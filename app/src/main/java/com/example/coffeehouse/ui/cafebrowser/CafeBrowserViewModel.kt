package com.example.coffeehouse.ui.cafebrowser

import com.example.coffeehouse.domain.model.CoffeeHouse
import com.example.coffeehouse.domain.usecase.GetCoffeeHouseListUseCase
import com.example.coffeehouse.ui.base.BaseViewModel
import com.example.coffeehouse.ui.cafebrowser.model.BrowserState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class CafeBrowserViewModel @Inject constructor(
    private val getCoffeeHouseListUseCase: GetCoffeeHouseListUseCase
) : BaseViewModel() {
    private val _cafeList: MutableStateFlow<List<CoffeeHouse>> = MutableStateFlow(listOf())
    val cafeList: StateFlow<List<CoffeeHouse>> = _cafeList

    private val _browserState: MutableStateFlow<BrowserState> = MutableStateFlow(BrowserState.LIST)
    val browserState: StateFlow<BrowserState> = _browserState

    private val _currentPoint: MutableStateFlow<CoffeeHouse.Point?> = MutableStateFlow(null)
    val currentPoint: StateFlow<CoffeeHouse.Point?> = _currentPoint

    init {
        loadCafeList()
    }


    fun updateBrowserState() {
        _browserState.value = when(_browserState.value) {
            BrowserState.LIST -> BrowserState.MAP
            BrowserState.MAP -> BrowserState.LIST
        }
    }

    fun updateCurrentPoint(point: CoffeeHouse.Point?) {
        _currentPoint.value = point
    }

    fun loadCafeList() {
        getRequestInfo { getCafeList() }
    }

    private suspend fun getCafeList() {
        _cafeList.value = getCoffeeHouseListUseCase()
    }
}
