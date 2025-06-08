package com.example.interfaz_tfg.compose.home

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.interfaz_tfg.api.model.user.UserDTO

@Composable
fun TopNavigationBar(
    navController: NavController,
    userRol: String?,
    user: UserDTO,
    color: ColorScheme
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
    ) {
        if (userRol != "PREMIUM") {
            PremiumButton(navController, user)
        }
        
        Spacer(Modifier.weight(1f))
        
        ProfileButton(navController, user, color)
    }
}