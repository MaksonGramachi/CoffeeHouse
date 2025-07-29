package com.example.coffeehouse.ui.menu

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.example.coffeehouse.domain.NullBodyException
import com.example.coffeehouse.domain.usecase.GetCoffeeHouseMenuUseCase
import com.example.coffeehouse.ui.menu.model.MenuState
import com.example.coffeehouse.ui.menu.model.ProductInfo
import com.example.coffeehouse.ui.navigation.CafeMenu
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MenuViewModel @Inject constructor(
    private val getCoffeeHouseMenuUseCase: GetCoffeeHouseMenuUseCase,
    private val savedStateHandle: SavedStateHandle,
) : ViewModel() {
    private val _currentMenu: MutableStateFlow<List<ProductInfo>> = MutableStateFlow(listOf())
    val currentMenu: StateFlow<List<ProductInfo>> = _currentMenu

    private val _menuState: MutableStateFlow<MenuState> = MutableStateFlow(MenuState.LIST)
    val menuState: StateFlow<MenuState> = _menuState

    init {
        try {
            viewModelScope.launch {
                val args = savedStateHandle.toRoute<CafeMenu>()
                loadCafeMenu(args.cafeId)
            }
        }
        catch (e: NullBodyException) {
            Log.d("Tag", "empty menu")
        }
    }

    fun updateMenuState() {
        _menuState.value = when (_menuState.value) {
            MenuState.LIST -> MenuState.ORDER
            MenuState.ORDER -> MenuState.LIST
        }
    }

    fun plusProductCount(id: Long) {
        _currentMenu.value = _currentMenu.value.map {
            if (it.productCard.id == id) {
                ProductInfo(it.productCard, it.productCount + 1u)
            } else {
                it
            }
        }
    }

    fun minusProductCount(id: Long) {
        _currentMenu.value = _currentMenu.value.map {
            if (it.productCard.id == id && it.productCount != 0u) {
                ProductInfo(it.productCard, it.productCount - 1u)
            } else {
                it
            }
        }
    }

    private fun loadCafeMenu(id: Long) {
        viewModelScope.launch {
            _currentMenu.value = getCoffeeHouseMenuUseCase(id).map { ProductInfo(it, 0u) }
        }
    }

}