package com.example.interfaz_tfg.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.interfaz_tfg.compose.Header

@Composable
fun StatsScreen(navController: NavController){
    Scaffold {innerpadding ->
        Header(navController, "Estadísticas")
        Column(modifier = Modifier.padding(innerpadding)) {  }
    }

}