package com.example.interfaz_tfg.screen.settings

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.interfaz_tfg.api.model.user.Goal
import com.example.interfaz_tfg.api.model.user.UserUpdateDTO
import com.example.interfaz_tfg.compose.Header
import com.example.interfaz_tfg.compose.SelectableBoxWithDropdown
import com.example.interfaz_tfg.viewModel.CycleViewModel
import com.example.interfaz_tfg.viewModel.UserViewModel

@Composable
fun CycleSettingsScreen(
    navController: NavController,
    bleedingDuration: Int,
    cycleLength: Int,
    username: String,
    email: String
) {
    val cycleViewModel: CycleViewModel = viewModel()
    val userViewModel: UserViewModel = viewModel()

    val color = MaterialTheme.colorScheme
    val cycleState = cycleViewModel.cycles.collectAsState()
    val cycle = cycleState.value.find { !it.isPredicted }
    val selectedCycleLength = remember { mutableIntStateOf(cycleLength) }
    val selectedBleedingDuration = remember { mutableIntStateOf( bleedingDuration) }

    val userState = userViewModel.user.collectAsState()
    val user = userState.value
    val selectedGoal = remember { mutableStateOf(user?.goal ?: Goal.TRACK_PERIOD) }

    LaunchedEffect(email) {
        cycleViewModel.loadCycles(email)
        userViewModel.getUserByUsername(username)
    }

    val numCycle = (21..100).map { it.toString() }
    val numMenstruation = (1..12).map { it.toString() }

    Scaffold(
        bottomBar = {
            if (cycle != null) {
                Button(
                    onClick = {
                        val updatedCycle = cycle.copy(
                            cycleLength = selectedCycleLength.intValue,
                            bleedingDuration = selectedBleedingDuration.intValue
                        )
                        val userUpdate = UserUpdateDTO(
                            email = email,
                            username = username,
                            password = "",
                            goal = user?.goal ?: Goal.TRACK_PERIOD
                        )
                    cycleViewModel.updateCycle(updatedCycle, navController, username, email)
                        cycleViewModel.getPrediction(email)
                        userViewModel.updateUser(userUpdate, navController )

                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 18.dp)
                ) {
                    Text("Guardar cambios")
                }
            }
        }
    ) { innerPadding ->

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .background(color.background)
        ) {
            Header(navController, "Ajustes ciclo")

            if (cycle == null || user == null) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 40.dp), // para evitar que se pegue demasiado al Header
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(color = color.primary)
                }
            } else {

                val goalStr = when (user.goal) {
                    Goal.GET_PREGNANT -> "Quedar embarazada"
                    Goal.TRACK_PERIOD -> "Seguimiento de ciclo"
                    Goal.AVOID_PREGNANCY -> "Evitar embarazo"
                }

                SelectableBoxWithDropdown(
                    "Objetivo: ",
                    listOf("Seguimiento de ciclo", "Quedar embarazada", "Evitar embarazo"),
                    goalStr
                ) { seleccionada ->
                    selectedGoal.value = when (seleccionada) {
                        "Quedar embarazada" -> Goal.GET_PREGNANT
                        "Evitar embarazo" -> Goal.AVOID_PREGNANCY
                        else -> Goal.TRACK_PERIOD
                    }
                }

                SelectableBoxWithDropdown(
                    "Duración del ciclo: ",
                    numCycle,
                    selectedCycleLength.intValue.toString()
                ) { seleccionada ->
                    selectedCycleLength.intValue = seleccionada.toInt()
                }

                SelectableBoxWithDropdown(
                    "Duración del periodo: ",
                    numMenstruation,
                    selectedBleedingDuration.intValue.toString()
                ) { seleccionada ->
                    selectedBleedingDuration.intValue = seleccionada.toInt()
                }
            }
        }
    }
}
