package com.example.interfaz_tfg.screen

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.example.interfaz_tfg.compose.Header

@Composable
fun StatsScreen(navController: NavController){
    Header(navController, "Estadísticas")
}