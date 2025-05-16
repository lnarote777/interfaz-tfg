package com.example.interfaz_tfg.screen.settings

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.interfaz_tfg.compose.Header
import com.example.interfaz_tfg.compose.SelectableBoxWithDropdown
import com.example.interfaz_tfg.navigation.AppScreen

@Composable
fun CycleSettingsScreen(navController: NavController){
    val color = MaterialTheme.colorScheme
    Scaffold { innerpadding ->

        Column (
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(innerpadding)
                .background(color.background)
        ) {
            Header(navController, "Ajustes ciclo")

            SelectableBoxWithDropdown(
                listOf("Seguimiento de ciclo", "Quedar embarazada", "Evitar embarazo"),
                "General"
            ) {
                    seleccionada ->
                Log.d("Dropdown", "Seleccionaste: $seleccionada")
            }
        }
    }
}