package com.example.interfaz_tfg.compose.login

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.interfaz_tfg.compose.login.loginDataClasses.LoginFormData
import com.example.interfaz_tfg.compose.login.loginDataClasses.LoginScreenState

@Composable
fun LoginFormContent(
    screenState: LoginScreenState,
    formData: LoginFormData,
    onStateChange: (LoginScreenState) -> Unit,
    onLogin: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth()
    ) {
        LoginHeaderSection()
        
        LoginInputSection(
            screenState = screenState,
            onStateChange = onStateChange
        )
        
        LoginErrorSection(screenState = screenState)
        
        LoginActionSection(
            onLogin = onLogin
        )
        
        LoginForgotPasswordSection()
    }
}