package com.example.coffeehouse.ui.cafebrowser

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.coffeehouse.domain.model.CoffeeHouse
import com.example.coffeehouse.ui.base.components.EthernetConnectionAlertDialog
import com.example.coffeehouse.ui.base.components.SessionTimeoutAlertDialog
import com.example.coffeehouse.ui.cafebrowser.model.BrowserState
import com.example.coffeehouse.ui.navigation.CafeMenu
import com.example.coffeehouse.ui.navigation.Login
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.location.Location
import com.yandex.mapkit.location.LocationListener
import com.yandex.mapkit.location.LocationStatus

@Composable
fun CafeBrowserScreen(
    viewModel: CafeBrowserViewModel = hiltViewModel<CafeBrowserViewModel>(),
    navController: NavController,
) {
    val cafeList by viewModel.cafeList.collectAsState()
    val isTokenValid by viewModel.isTokenValid.collectAsState()
    val isConnectSuccess by viewModel.isConnectSuccess.collectAsState()
    val browserState by viewModel.browserState.collectAsState()

    if (!isTokenValid) {
        SessionTimeoutAlertDialog({
            navController.popBackStack(route = Login, false)
        })
    }

    if(!isConnectSuccess) {
        EthernetConnectionAlertDialog({
            viewModel.updateConnectStatus()
            viewModel.loadCafeList()
        })
    }


    val currentPoint by viewModel.currentPoint.collectAsState()

    val locationPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { isGranted ->
            if (isGranted) {
                val locationManager = MapKitFactory.getInstance().createLocationManager()
                locationManager.requestSingleUpdate(object : LocationListener {
                    override fun onLocationStatusUpdated(status: LocationStatus) {
                    }

                    override fun onLocationUpdated(location: Location) {
                        viewModel.updateCurrentPoint(
                            point = CoffeeHouse.Point(
                                latitude = location.position.latitude,
                                longitude = location.position.longitude
                            )
                        )
                    }
                })
            }
        }
    )


    CafeBrowserScreen(
        cafeList = cafeList,
        browserState = browserState,
        currentPoint = currentPoint,
        updateBrowserState = viewModel::updateBrowserState,
        onNavigateNext = { navController.navigate(CafeMenu(it)) },
        onNavigateBack = {
            navController.popBackStack()
        }
    )

    LaunchedEffect(Unit) {
        locationPermissionLauncher.launch(android.Manifest.permission.ACCESS_FINE_LOCATION)
    }
}

@Composable
fun CafeBrowserScreen(
    cafeList: List<CoffeeHouse>,
    browserState: BrowserState,
    currentPoint: CoffeeHouse.Point?,
    updateBrowserState: () -> Unit,
    onNavigateNext: (Long) -> Unit,
    onNavigateBack: () -> Unit
) {
    when (browserState) {
        BrowserState.LIST -> CafeListScreen(
            cafeList = cafeList,
            currentPoint = currentPoint,
            updateBrowserState = updateBrowserState,
            onNavigateNext = onNavigateNext,
            onNavigateBack = onNavigateBack
        )

        BrowserState.MAP -> CafeMapScreen(
            cafeList = cafeList,
            currentPoint = currentPoint,
            updateBrowserState = updateBrowserState,
            onNavigateNext = onNavigateNext
        )
    }
}
