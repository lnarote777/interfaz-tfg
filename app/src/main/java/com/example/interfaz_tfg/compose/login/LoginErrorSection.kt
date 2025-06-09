package com.example.interfaz_tfg.compose.login

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.interfaz_tfg.compose.login.loginDataClasses.LoginScreenState

@Composable
fun LoginErrorSection(screenState: LoginScreenState) {
    if (screenState.showError) {
        Text(
            text = screenState.errorMessage,
            color = Color.Red,
            fontSize = 20.sp,
            textAlign = TextAlign.Center
        )
    }
    
    Spacer(Modifier.height(60.dp))
}