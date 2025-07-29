package com.example.coffeehouse.ui.cafebrowser

import android.content.pm.PackageManager
import android.graphics.Canvas
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.core.graphics.createBitmap
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.example.coffeehouse.R
import com.example.coffeehouse.domain.model.CoffeeHouse
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.map.CameraPosition
import com.yandex.mapkit.map.MapObjectTapListener
import com.yandex.mapkit.map.TextStyle
import com.yandex.mapkit.mapview.MapView
import com.yandex.runtime.image.ImageProvider

fun getMapObjectTapListener(onNavigateNext: () -> Unit) = MapObjectTapListener { mapObject, point ->
    onNavigateNext()
    true
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CafeMapScreen(
    cafeList: List<CoffeeHouse>,
    currentPoint: CoffeeHouse.Point?,
    updateBrowserState: () -> Unit,
    onNavigateNext: (Long) -> Unit,
) {
    BackHandler(enabled = true) {
        updateBrowserState()
    }

    val drawable = ContextCompat.getDrawable(LocalContext.current, R.drawable.ic_coffee)
    val bitmap = createBitmap(drawable!!.intrinsicWidth, drawable.intrinsicHeight)
    val canvas = Canvas(bitmap)
    drawable.setBounds(0, 0, canvas.width, canvas.height)
    drawable.draw(canvas)

    val hasPermission = ContextCompat.checkSelfPermission(
        LocalContext.current,
        android.Manifest.permission.ACCESS_FINE_LOCATION
    ) == PackageManager.PERMISSION_GRANTED

    val startPoint =
        if (hasPermission && currentPoint?.latitude != null && currentPoint.longitude != null) {
            Point(currentPoint.latitude, currentPoint.longitude)
        } else {
            val firstCorrectPoint =
                cafeList.find { it.point?.latitude != null && it.point.longitude != null }?.point
            if (firstCorrectPoint == null) {
                Point()
            } else {
                Point(firstCorrectPoint.latitude!!, firstCorrectPoint.longitude!!)
            }
        }

    Scaffold(
        topBar = {
            Column() {
                CenterAlignedTopAppBar(
                    title = {
                        Text(
                            text = stringResource(R.string.map_title),
                            color = colorResource(id = R.color.brown_dark),
                            fontWeight = FontWeight.Bold,
                        )
                    },
                    navigationIcon = {
                        IconButton(
                            onClick = { updateBrowserState() },
                        ) {
                            Icon(
                                painter = painterResource(R.drawable.ic_arrow_back),
                                contentDescription = "",
                                tint = colorResource(id = R.color.brown_dark),
                            )
                        }
                    },
                )
                HorizontalDivider(
                    thickness = 1.dp,
                    color = colorResource(id = R.color.gray)
                )
                HorizontalDivider(
                    thickness = 1.dp,
                    color = colorResource(id = R.color.gray),
                    modifier = Modifier.padding(top = 1.dp)
                )
            }
        },
        content = { padding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
            ) {
                AndroidView(
                    factory = { ctx ->
                        MapView(ctx).apply {
                            mapWindow.map.move(
                                CameraPosition(startPoint, 17f, 0f, 0f)
                            )
                            cafeList.forEach { cafe ->
                                if (cafe.point?.latitude != null && cafe.point.longitude != null) {
                                    mapWindow.map.mapObjects.addPlacemark().apply {
                                        geometry = Point(cafe.point.latitude, cafe.point.longitude)
                                        setIcon(ImageProvider.fromBitmap(bitmap))
                                        setText(
                                            cafe.name,
                                            TextStyle().apply {
                                                size = 10f
                                                placement = TextStyle.Placement.BOTTOM
                                                offset = 5f
                                            },
                                        )
                                        addTapListener(getMapObjectTapListener { onNavigateNext(cafe.id) })
                                    }
                                }
                            }

                        }
                    },
                    update = {
                        it.mapWindow.map.move(
                            CameraPosition(startPoint, 17f, 0f, 0f)
                        )
                    }
                )
            }
        }
    )

    DisposableEffect(LocalLifecycleOwner.current) {
        MapKitFactory.getInstance().onStart()
        onDispose {
            MapKitFactory.getInstance().onStop()
        }
    }
}