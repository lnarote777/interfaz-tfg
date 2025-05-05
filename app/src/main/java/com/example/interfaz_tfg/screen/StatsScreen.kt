package com.example.interfaz_mesames.screen

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.example.interfaz_mesames.compose.Header

@Composable
fun StatsScreen(navController: NavController){
    Header(navController, "Estadísticas")
}