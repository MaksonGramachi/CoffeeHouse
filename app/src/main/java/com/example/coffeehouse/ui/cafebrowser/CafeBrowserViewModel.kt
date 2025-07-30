package com.example.coffeehouse.ui.cafebrowser

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.coffeehouse.domain.UserTokenIsNotAvailableException
import com.example.coffeehouse.domain.model.CoffeeHouse
import com.example.coffeehouse.domain.usecase.GetCoffeeHouseListUseCase
import com.example.coffeehouse.ui.cafebrowser.model.BrowserState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CafeBrowserViewModel @Inject constructor(
    private val getCoffeeHouseListUseCase: GetCoffeeHouseListUseCase
) : ViewModel(){
    private val _cafeList: MutableStateFlow<List<CoffeeHouse>> = MutableStateFlow(listOf())
    val cafeList: StateFlow<List<CoffeeHouse>> = _cafeList

    private val _isTokenValid: MutableStateFlow<Boolean> = MutableStateFlow(true)
    val isTokenValid: StateFlow<Boolean> = _isTokenValid

    private val _browserState: MutableStateFlow<BrowserState> = MutableStateFlow(BrowserState.LIST)
    val browserState: StateFlow<BrowserState> = _browserState

    private val _currentPoint: MutableStateFlow<CoffeeHouse.Point?> = MutableStateFlow(null)
    val currentPoint: StateFlow<CoffeeHouse.Point?> = _currentPoint

    init {
        getCafeList()

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

    private fun getCafeList() {
        viewModelScope.launch {
            try {
                _cafeList.value = getCoffeeHouseListUseCase()
            }
            catch(e: Exception) {
                when(e){
                    is UserTokenIsNotAvailableException -> _isTokenValid.value = false
                }
            }
        }
    }
}
