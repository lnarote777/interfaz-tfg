package com.example.interfaz_tfg

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.interfaz_tfg.navigation.AppNavigation
import com.example.interfaz_tfg.ui.theme.InterfaztfgTheme

class MainActivity : ComponentActivity() {
    @SuppressLint("NewApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            InterfaztfgTheme {
                AppNavigation()
            }
        }
    }
}
