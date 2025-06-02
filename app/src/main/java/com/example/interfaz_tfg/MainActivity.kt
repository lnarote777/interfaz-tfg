package com.example.interfaz_tfg

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.interfaz_tfg.navigation.AppNavigation
import com.example.interfaz_tfg.navigation.AppScreen
import com.example.interfaz_tfg.ui.theme.InterfaztfgTheme
import com.example.interfaz_tfg.viewModel.LoginViewModel
import com.example.interfaz_tfg.viewModel.getRolFromToken
import com.example.interfaz_tfg.viewModel.getUsernameFromToken
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

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

