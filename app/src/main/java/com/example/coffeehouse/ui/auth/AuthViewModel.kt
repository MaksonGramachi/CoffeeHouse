package com.example.coffeehouse.ui.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.coffeehouse.domain.NullBodyException
import com.example.coffeehouse.domain.UserAlreadyExistsException
import com.example.coffeehouse.domain.UserAuthInformationIsEmpty
import com.example.coffeehouse.domain.UserNotExistsException
import com.example.coffeehouse.domain.UserPasswordsIsNotEqualException
import com.example.coffeehouse.domain.usecase.FetchAccessTokenFromLoginUseCase
import com.example.coffeehouse.domain.usecase.FetchAccessTokenFromRegisterUseCase
import com.example.coffeehouse.ui.auth.model.AuthState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val fetchAccessTokenFromLoginUseCase: FetchAccessTokenFromLoginUseCase,
    private val fetchAccessTokenFromRegisterUseCase: FetchAccessTokenFromRegisterUseCase
): ViewModel() {
    private val _email: MutableStateFlow<String> = MutableStateFlow("")
    val email: StateFlow<String> = _email

    private val _password: MutableStateFlow<String> = MutableStateFlow("")
    val password: StateFlow<String> = _password

    private val _repeatedPassword: MutableStateFlow<String> = MutableStateFlow("")
    val repeatPassword: StateFlow<String> = _repeatedPassword

    private val _exceptionMessage: MutableStateFlow<String> = MutableStateFlow("")
    val exceptionMessage: StateFlow<String> = _exceptionMessage

    private val _authState: MutableStateFlow<AuthState> = MutableStateFlow(AuthState.LOGIN)
    val authState: StateFlow<AuthState> = _authState

    fun updateEmail(email: String) {
        _email.value = email
    }

    fun updatePassword(password: String) {
        _password.value = password
    }

    fun updateRepeatedPassword(password: String) {
        _repeatedPassword.value = password
    }

    fun updateAuthState() {
        _authState.value = when (_authState.value) {
            AuthState.LOGIN -> AuthState.REGISTER
            AuthState.REGISTER -> AuthState.LOGIN
        }
        _exceptionMessage.value = ""
    }

    fun authUser(onNavigateNext: () -> Unit) {
        viewModelScope.launch {
            try {
                when (_authState.value) {
                    AuthState.LOGIN -> {
                        loginUser()
                        onNavigateNext()
                    }
                    AuthState.REGISTER -> {
                        registerUser()
                        updateAuthState()
                    }
                }
            }
            catch(e: Exception) {
                processAuthException(e)
            }
        }
    }

    private suspend fun loginUser() {
        if (_email.value.isEmpty() || _password.value.isEmpty()) {
           throw UserAuthInformationIsEmpty("Login or password is empty")
        }

        fetchAccessTokenFromLoginUseCase(_email.value, _password.value)
    }

    private suspend fun registerUser() {
        if (_email.value.isEmpty() || _password.value.isEmpty() || _repeatedPassword.value.isEmpty()) {
            throw UserAuthInformationIsEmpty("Login or password is empty")
        }

        if (_password.value != _repeatedPassword.value) {
            throw UserPasswordsIsNotEqualException("Password and repeatedPassword is not equal")
        }

        fetchAccessTokenFromRegisterUseCase(_email.value, _password.value)
    }

    private suspend fun processAuthException(e: Exception) {
        _exceptionMessage.emit(when(e) {
            is UserNotExistsException -> "Пользователь с таким логином не найден"
            is UserAlreadyExistsException -> "Пользователь с таким логином уже зарегестрирован"
            is NullBodyException -> "Ошибка с сервером, попробуете позже"
            is UserAuthInformationIsEmpty -> "Заполните указанные поля"
            is UserPasswordsIsNotEqualException -> "Пароли не совпадают"
            else -> e.message.orEmpty()
        })
    }
}