package com.example.interfaz_tfg.compose.login

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.interfaz_tfg.navigation.AppScreen

@Composable
fun LoginFooterSection(navController: NavController) {
    Text(
        text = "¿Aun no estás registrada? Regístrate.",
        textAlign = TextAlign.Center,
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 30.dp)
            .clickable {
                navController.navigate(route = AppScreen.RegisterScreen.route)
            }
    )
}