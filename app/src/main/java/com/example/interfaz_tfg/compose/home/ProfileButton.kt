package com.example.interfaz_tfg.compose.home

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.interfaz_tfg.R
import com.example.interfaz_tfg.api.model.user.UserDTO
import com.example.interfaz_tfg.navigation.AppScreen

@Composable
fun ProfileButton(
    navController: NavController,
    user: UserDTO?,
    color: ColorScheme
) {
    IconButton(
        onClick = {
            val encodedUsername = Uri.encode(user?.username ?: "")
            val encodedEmail = Uri.encode(user?.email ?: "")
            navController.navigate(
                route = AppScreen.UserScreen.route + "/$encodedUsername/$encodedEmail"
            )
        }
    ) {
        Image(
            painter = painterResource(R.drawable.user_icon),
            contentDescription = "Profile",
            modifier = Modifier.size(35.dp),
            colorFilter = ColorFilter.tint(color.onBackground)
        )
    }
}
