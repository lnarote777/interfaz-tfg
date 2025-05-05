package com.example.interfaz_mesames.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.interfaz_mesames.R
import com.example.interfaz_mesames.compose.Card
import com.example.interfaz_mesames.compose.Header

@Composable
fun DailyScreen(navController: NavController){
    //provisional la lista asi
    val sintomas = listOf<Pair<String, List<String>>>(
        Pair("Nombres", listOf("Juan", "Lucas", "Matias")),
        Pair("Nombres", listOf("Juan", "Lucas", "Matias")),
        Pair("Nombres", listOf("Juan", "Lucas", "Matias")),
        Pair("Nombres", listOf("Juan", "Lucas", "Matias")),
        Pair("Nombres", listOf("Juan", "Lucas", "Matias")),
        Pair("Nombres", listOf("Juan", "Lucas", "Matias")),
        Pair("Nombres", listOf("Juan", "Lucas", "Matias")),
        Pair("Nombres", listOf("Juan", "Lucas", "Matias")),
        Pair("Nombres", listOf("Juan", "Lucas", "Matias")),
        Pair("Nombres", listOf("Juan", "Lucas", "Matias")),
        Pair("Nombres", listOf("Juan", "Lucas", "Matias")),
        Pair("Nombres", listOf("Juan", "Lucas", "Matias")),
        Pair("Nombres", listOf("Juan", "Lucas", "Matias")),
        Pair("Nombres", listOf("Juan", "Lucas", "Matias")),
        Pair("Nombres", listOf("Juan", "Lucas", "Matias")),
        Pair("Nombres", listOf("Juan", "Lucas", "Matias")),
        Pair("Nombres", listOf("Juan", "Lucas", "Matias")),
    ) // para el view model

    val (selectedSymptoms, setSelectedSymptoms) = remember { mutableStateOf(listOf<String>()) }

    Box {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize().padding(top = 35.dp, bottom = 18.dp)
                .background(color = colorResource(R.color.fondo))
        ) {
            Header(navController, "Registro diario")
            Spacer(Modifier.height(20.dp))
            LazyColumn {
                items(sintomas.count()){index ->
                    val pair = sintomas[index]
                    Card(title = pair.first,
                        emojis = pair.second,
                        selectedEmojis = selectedSymptoms,
                        onSelectionChange = setSelectedSymptoms,
                        color = Color.Red
                    )
                    Spacer(Modifier.height(20.dp))
                }
            }
        }
    }


}