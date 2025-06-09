package com.example.interfaz_tfg.compose.login

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect

@Composable
fun LoginScreenEffects(
    uiState: String,
    onErrorReceived: (String) -> Unit
) {
    LaunchedEffect(uiState) {
        if (uiState.isNotEmpty()) {
            onErrorReceived(uiState)
        }
    }
}