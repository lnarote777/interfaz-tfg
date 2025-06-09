package com.example.interfaz_tfg.compose.login

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.interfaz_tfg.compose.login.loginDataClasses.LoginFormData
import com.example.interfaz_tfg.compose.login.loginDataClasses.LoginScreenState

@Composable
fun LoginScreenUI(
    navController: NavController,
    screenState: LoginScreenState,
    formData: LoginFormData,
    onStateChange: (LoginScreenState) -> Unit,
    onLogin: () -> Unit
) {
    Box(modifier = Modifier.fillMaxSize()) {
        LoginBackgroundImage()
        
        LoginFormContainer(
            navController = navController,
            screenState = screenState,
            formData = formData,
            onStateChange = onStateChange,
            onLogin = onLogin
        )
    }
}