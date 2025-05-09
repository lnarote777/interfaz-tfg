package com.example.interfaz_tfg.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import com.example.interfaz_tfg.R

private val DarkColorScheme = darkColorScheme(
    primary = Color(0xFF840051), // Titulo
    secondary = Color(0xFF34052D), // Eslogan
    tertiary = Color(0xFF543585), // Botones1
    background = Color(0xFF0A101D), // Fondo oscuro
    surface = Color(0xFF1A284A), // Superficie más oscura para las tarjetas
    onPrimary = Color.White, // Texto sobre el título
    onSecondary = Color.White, // Texto sobre eslogan
    onTertiary = Color.White, // Texto sobre botones
    onBackground = Color.White, // Texto sobre el fondo
    onSurface = Color.White, // Texto sobre la superficie
    error = Color(0xFFB00020), // Rojo de error
)

private val LightColorScheme = lightColorScheme(
    primary = Color(0xFF840051), // Titulo
    secondary = Color(0xFF34052D), // Eslogan
    tertiary = Color(0xFF543585), // Botones1
    background = Color(0xFFF6F6F6), // Fondo
    surface = Color(0xFFFFFFFF), // Superficie blanca
    onPrimary = Color.White, // Texto sobre el título
    onSecondary = Color.White, // Texto sobre eslogan
    onTertiary = Color.White, // Texto sobre botones
    onBackground = Color.Black, // Texto sobre el fondo
    onSurface = Color.Black, // Texto sobre la superficie
    error = Color(0xFFB00020), // Rojo de error
)

@Composable
fun InterfaztfgTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }
    val background = if (darkTheme) painterResource(R.drawable.fondo_load_dark) else painterResource(R.drawable.fondo_load)

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}