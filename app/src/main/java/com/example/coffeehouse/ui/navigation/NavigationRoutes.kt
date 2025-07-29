package com.example.coffeehouse.ui.navigation

import kotlinx.serialization.Serializable

@Serializable
object Login

@Serializable
object CafeBrowser

@Serializable
data class CafeMenu(val cafeId: Long)
