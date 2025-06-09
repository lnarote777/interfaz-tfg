package com.example.interfaz_tfg.compose.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import com.example.interfaz_tfg.R

@Composable
fun LoginBackgroundImage() {
    val backgroundResource = if (isSystemInDarkTheme()) {
        R.drawable.fondo_load_dark
    } else {
        R.drawable.fondo_login
    }
    
    Image(
        painter = painterResource(backgroundResource),
        contentDescription = "Fondo",
        contentScale = ContentScale.Crop,
        modifier = Modifier.fillMaxSize()
    )
}