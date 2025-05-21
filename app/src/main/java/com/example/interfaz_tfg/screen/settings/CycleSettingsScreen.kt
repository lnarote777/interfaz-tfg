package com.example.interfaz_tfg.screen.settings

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.interfaz_tfg.compose.Header
import com.example.interfaz_tfg.compose.SelectableBoxWithDropdown

@Composable
fun CycleSettingsScreen(
    navController: NavController,
    periodDays: String,
    cycleDays: String
){

    val color = MaterialTheme.colorScheme
    val numCycle = mutableListOf<String>()
    val numMenstruation = mutableListOf<String>()

    for(i in 21 until 101){
        numCycle.add(i.toString())
    }

    for(i in 1 until 13){
        numMenstruation.add(i.toString())
    }

    Scaffold(
        bottomBar = {
            Button(
                onClick = {

                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 18.dp)
            ){
                Text("Guardar cambios")
            }
        }
    ) { innerpadding ->

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(innerpadding)
                .background(color.background)
        ) {
            Header(navController, "Ajustes ciclo")

            SelectableBoxWithDropdown(
                listOf("Seguimiento de ciclo", "Quedar embarazada", "Evitar embarazo"),
                "General"
            ) { seleccionada ->
                Log.d("Dropdown", "Seleccionaste: $seleccionada")
            }
            SelectableBoxWithDropdown(
                numCycle,
                cycleDays
            ) { seleccionada ->
                Log.d("Dropdown", "Seleccionaste: $seleccionada")
            }
            SelectableBoxWithDropdown(
                numMenstruation,
                periodDays
            ) { seleccionada ->
                Log.d("Dropdown", "Seleccionaste: $seleccionada")
            }
        }
    }
}