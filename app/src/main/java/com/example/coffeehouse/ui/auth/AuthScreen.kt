package com.example.coffeehouse.ui.auth

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.coffeehouse.ui.auth.model.AuthState
import com.example.coffeehouse.ui.navigation.CafeBrowser

@Composable
fun AuthScreen(
    viewModel: AuthViewModel = hiltViewModel<AuthViewModel>(),
    navController: NavController
) {
    val email by viewModel.email.collectAsState()
    val password by viewModel.password.collectAsState()
    val repeatPassword by viewModel.repeatPassword.collectAsState()
    val exceptionMessage by viewModel.exceptionMessage.collectAsState()
    val authState by viewModel.authState.collectAsState()

    AuthScreen(
        email = email,
        password = password,
        repeatedPassword = repeatPassword,
        exceptionMessage = exceptionMessage,
        authState = authState,
        updateAuthState = viewModel::updateAuthState,
        updateEmail = viewModel::updateEmail,
        updatePassword = viewModel::updatePassword,
        updateRepeatedPassword = viewModel::updateRepeatedPassword,
        authUser = { viewModel.authUser({navController.navigate(CafeBrowser)}) },
    )
}

@Composable
fun AuthScreen(
    email: String,
    password: String,
    repeatedPassword: String,
    exceptionMessage: String,
    authState: AuthState,
    updateAuthState: () -> Unit,
    updateEmail: (String) -> Unit,
    updatePassword: (String) -> Unit,
    updateRepeatedPassword: (String) -> Unit,
    authUser: () -> Unit,
) {
    when(authState) {
        AuthState.LOGIN -> LoginScreen(
            email = email,
            password = password,
            exceptionMessage = exceptionMessage,
            updateAuthState = updateAuthState,
            updateEmail = updateEmail,
            updatePassword = updatePassword,
            authUser = authUser,
        )
        AuthState.REGISTER -> RegistrationScreen(
            email = email,
            password = password,
            repeatedPassword = repeatedPassword,
            exceptionMessage = exceptionMessage,
            updateAuthState = updateAuthState,
            updateEmail = updateEmail,
            updatePassword = updatePassword,
            updateRepeatedPassword = updateRepeatedPassword,
            authUser = authUser,
        )
    }
}