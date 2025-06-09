package com.example.interfaz_tfg.compose.login

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.interfaz_tfg.compose.TextFielPassword
import com.example.interfaz_tfg.compose.Textfield
import com.example.interfaz_tfg.compose.login.loginDataClasses.LoginScreenState

@Composable
fun LoginInputSection(
    screenState: LoginScreenState,
    onStateChange: (LoginScreenState) -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth()
    ) {
        Textfield(
            value = screenState.user,
            placeholder = "Usuario"
        ) { newUser ->
            onStateChange(screenState.copy(user = newUser))
        }
        
        Spacer(Modifier.height(30.dp))
        
        TextFielPassword(
            password = screenState.password,
            passVisible = screenState.passVisible,
            placeholder = "Contraseña",
            valueChange = { newPassword ->
                onStateChange(screenState.copy(password = newPassword))
            },
            onclick = {
                onStateChange(screenState.copy(passVisible = !screenState.passVisible))
            }
        )
        
        Spacer(Modifier.height(30.dp))
    }
}