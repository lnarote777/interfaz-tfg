package com.example.interfaz_tfg.compose.home

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.interfaz_tfg.R
import com.example.interfaz_tfg.api.model.user.UserDTO
import com.example.interfaz_tfg.navigation.AppScreen

@Composable
fun PremiumButton(
    navController: NavController,
    user: UserDTO
) {
    val encodedEmail = Uri.encode(user.email ?: "")
    IconButton(
        onClick = { 
            navController.navigate(route = AppScreen.PremiumScreen.route + "/$encodedEmail")
        },
        modifier = Modifier.size(50.dp)
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Image(
                painter = painterResource(R.drawable.corona_home),
                contentDescription = "VIP",
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}
