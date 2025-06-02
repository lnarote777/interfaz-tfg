package com.example.interfaz_tfg.compose

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.interfaz_tfg.navigation.AppScreen

@Composable
fun ProfileImage(
    uri: Uri?,
    avatarRes: Int?,
    navController: NavController,
    username: String,
    email: String)
{
    val modifier = Modifier
        .size(100.dp)
        .clip(CircleShape)
        .clickable {
            navController.navigate("${AppScreen.UserSettingsScreen.route}/$username/$email")
        }

    when {
        uri != null -> {
            Image(
                painter = rememberAsyncImagePainter(uri),
                contentDescription = "Imagen de perfil",
                modifier = modifier
            )
        }
        avatarRes != null -> {
            Image(
                painter = painterResource(id = avatarRes),
                contentDescription = "Avatar",
                modifier = modifier
            )
        }
        else -> {
            Icon(
                imageVector = Icons.Default.AccountCircle,
                contentDescription = "Avatar por defecto",
                modifier = modifier,
                tint = Color.Gray
            )
        }
    }
}