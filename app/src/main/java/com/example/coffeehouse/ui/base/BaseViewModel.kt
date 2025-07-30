package com.example.coffeehouse.ui.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.coffeehouse.domain.UserTokenIsNotAvailableException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.io.IOException

abstract class BaseViewModel : ViewModel() {
    private val _isTokenValid: MutableStateFlow<Boolean> = MutableStateFlow(true)
    val isTokenValid: StateFlow<Boolean> = _isTokenValid

    private val _isConnectSuccess: MutableStateFlow<Boolean> = MutableStateFlow(true)
    val isConnectSuccess: StateFlow<Boolean> = _isConnectSuccess

    fun updateConnectStatus() {
        _isConnectSuccess.value = !_isConnectSuccess.value
    }


    protected fun getRequestInfo(getFunction: suspend () -> Unit) {
        viewModelScope.launch {
            try {
                getFunction()
            } catch (e: Exception) {
                when (e) {
                    is UserTokenIsNotAvailableException -> _isTokenValid.value = false
                    is IOException -> _isConnectSuccess.value = false
                }

            }
        }
    }
}