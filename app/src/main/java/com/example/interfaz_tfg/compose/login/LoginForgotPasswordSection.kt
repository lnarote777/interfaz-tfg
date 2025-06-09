package com.example.interfaz_tfg.compose.login

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign

@Composable
fun LoginForgotPasswordSection() {
    Text(
        text = "¿Olvidaste la contraseña?",
        textAlign = TextAlign.Center,
        modifier = Modifier.fillMaxWidth()
        // TODO: Implementar navegación a pantalla de recuperación
        // .clickable { navController.navigate(route = AppScreen.ForgotPasswordScreen.route) }
    )
}