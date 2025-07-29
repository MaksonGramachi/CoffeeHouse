package com.example.coffeehouse.ui.menu

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.coffeehouse.ui.menu.model.MenuState
import com.example.coffeehouse.ui.menu.model.ProductInfo
import com.example.coffeehouse.ui.navigation.CafeBrowser

@Composable
fun MenuScreen(
    viewModel: MenuViewModel = hiltViewModel<MenuViewModel>(),
    navController: NavController,
) {
    val currentMenu by viewModel.currentMenu.collectAsState()
    val menuState by viewModel.menuState.collectAsState()

    MenuScreen(
        currentMenu = currentMenu,
        menuState = menuState,
        plusProductCount = { viewModel.plusProductCount(it) },
        minusProductCount = { viewModel.minusProductCount(it) },
        updateMenuState = viewModel::updateMenuState,
        onNavigateNext = {},
        onNavigateBack = {
            navController.navigate(CafeBrowser) {
                popUpTo(CafeBrowser) { inclusive = true }
            }
        }
    )
}

@Composable
fun MenuScreen(
    currentMenu: List<ProductInfo>,
    menuState: MenuState,
    plusProductCount: (Long) -> Unit,
    minusProductCount: (Long) -> Unit,
    updateMenuState: () -> Unit,
    onNavigateNext: () -> Unit,
    onNavigateBack: () -> Unit,

    ) {
    when (menuState) {
        MenuState.LIST -> ProductListScreen(
            currentMenu = currentMenu,
            plusProductCount = plusProductCount,
            minusProductCount = minusProductCount,
            updateMenuState = updateMenuState,
            onNavigateBack = onNavigateBack
        )

        MenuState.ORDER -> OrderScreen(
            currentMenu = currentMenu,
            plusProductCount = plusProductCount,
            minusProductCount = minusProductCount,
            updateMenuState = updateMenuState,
            onNavigateNext = onNavigateNext
        )
    }
}