package com.example.coffeehouse.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.coffeehouse.ui.auth.AuthScreen
import com.example.coffeehouse.ui.cafebrowser.CafeBrowserScreen
import com.example.coffeehouse.ui.menu.MenuScreen

@Composable
fun AppNavigation(
    navController: NavHostController = rememberNavController(),
) {
    NavHost(
        navController = navController,
        startDestination = Login
    ) {
        composable<Login> {
            AuthScreen(
                navController = navController,
            )
        }

        composable<CafeBrowser> {
            CafeBrowserScreen(
                navController = navController,
            )
        }
        composable<CafeMenu> {
            MenuScreen(navController = navController)
        }
    }
}