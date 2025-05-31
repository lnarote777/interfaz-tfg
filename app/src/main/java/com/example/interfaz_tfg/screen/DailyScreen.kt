package com.example.interfaz_tfg.screen

import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.interfaz_tfg.api.model.cycle.DailyLogDTO
import com.example.interfaz_tfg.api.model.cycle.MenstrualFlowLevel
import com.example.interfaz_tfg.compose.Card
import com.example.interfaz_tfg.compose.Header
import com.example.interfaz_tfg.compose.calendario.WeekCalendar
import com.example.interfaz_tfg.compose.menstrualflow
import com.example.interfaz_tfg.compose.moodEmojis
import com.example.interfaz_tfg.compose.physicalActivityEmojis
import com.example.interfaz_tfg.compose.pillEmojis
import com.example.interfaz_tfg.compose.sexEmojis
import com.example.interfaz_tfg.compose.symptomEmojis
import com.example.interfaz_tfg.compose.vaginalDischarge
import com.example.interfaz_tfg.compose.waterEmojis
import com.example.interfaz_tfg.viewModel.DailyLogViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DailyScreen(
    navController: NavController,
    email: String,
    token: String,
    isBleeding: String,
    viewModel: DailyLogViewModel = viewModel()
){

    val categorias = listOf(
        "Estado de ánimo" to moodEmojis,
        "Síntomas" to symptomEmojis,
        "Flujo Menstrual" to menstrualflow,
        "Flujo vaginal" to vaginalDischarge,
        "Actividad sexual" to sexEmojis,
        "Actividad física" to physicalActivityEmojis,
        "Agua" to waterEmojis,
        "Anticonceptivo Oral" to pillEmojis
    )

    val logState by viewModel.logState.collectAsState()
    val selectedItems = remember(logState) {
        derivedStateOf {
            mapOf(
                "Estado de ánimo" to logState.mood,
                "Síntomas" to logState.symptoms,
                "Flujo Menstrual" to if (logState.menstrualFlow?.isNotEmpty() == true) listOf(logState.menstrualFlow) else emptyList(),
                "Flujo vaginal" to logState.vaginalDischarge,
                "Actividad sexual" to logState.sexualActivity,
                "Actividad física" to logState.physicalActivity,
                "Anticonceptivo Oral" to logState.pillsTaken
            )
        }
    }.value
    val color = MaterialTheme.colorScheme
    val selectedDate by viewModel.selectedDate.collectAsState()

    LaunchedEffect (Unit) { viewModel.loadLogForDate(email, selectedDate )  }

    val context = LocalContext.current
    val isEditing by viewModel.isEditing.collectAsState()
    val hasChanges by remember(selectedItems, logState) {
        derivedStateOf {
            logState.mood.isNotEmpty() ||
            logState.symptoms.isNotEmpty() ||
            logState.sexualActivity.isNotEmpty() ||
            logState.vaginalDischarge.isNotEmpty() ||
            logState.physicalActivity.isNotEmpty() ||
            logState.pillsTaken.isNotEmpty() ||
            logState.notes?.isNotBlank() == true ||
            logState.waterIntake != null ||
            logState.weight != null
        }
    }

    val menstrualFlowLevel = when (logState.menstrualFlow) {
        "light" -> MenstrualFlowLevel.LIGHT
        "heavy" -> MenstrualFlowLevel.HEAVY
        "clots" -> MenstrualFlowLevel.CLOTS
        "moderate" -> MenstrualFlowLevel.MODERATE
        else -> null
    }



    Scaffold(
        bottomBar = {
            Button(
                onClick = {
                    val logDTO = DailyLogDTO(
                        date = selectedDate.toString(),
                        hasMenstruation = isBleeding.toBoolean(),
                        menstrualFlow = menstrualFlowLevel,
                        sexualActivity = logState.sexualActivity,
                        mood = logState.mood,
                        symptoms = logState.symptoms,
                        vaginalDischarge = logState.vaginalDischarge,
                        physicalActivity = logState.physicalActivity,
                        pillsTaken = logState.pillsTaken,
                        waterIntake = logState.waterIntake,
                        weight = null,
                        notes = logState.notes
                    )
                    if (isEditing) {
                        viewModel.updateLog(email, selectedDate, logDTO,
                            onSuccess = {
                                Toast.makeText(context, "Registro actualizado", Toast.LENGTH_SHORT).show()
                                navController.popBackStack()
                            },
                            onError = {
                                Toast.makeText(context, it, Toast.LENGTH_LONG).show()
                            }
                        )
                    } else {
                        viewModel.createLog(token, email, logDTO,
                            onSuccess = {
                                Toast.makeText(context, "Registro guardado", Toast.LENGTH_SHORT).show()
                                navController.popBackStack()
                            },
                            onError = {
                                Toast.makeText(context, it, Toast.LENGTH_LONG).show()
                            }
                        )
                    }
                },
                enabled = hasChanges,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 18.dp)

            ) {
                Text("Guardar")
            }
        }
    ) { innerpadding ->
        Box {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .padding(innerpadding)
                    .background(color = color.background)
            ) {
                Header(navController, "Registro diario")

                WeekCalendar(
                    selectedDate = selectedDate,
                    onDateSelected = { date -> viewModel.setSelectedDate(date, email, token) }
                )

                Spacer(Modifier.height(16.dp))
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    items(categorias) { (titulo, emojiList) ->
                        val seleccionados = selectedItems[titulo] ?: emptyList()
                        if (titulo == "Flujo Menstrual" && !isBleeding.toBoolean()) {
                            return@items
                        }
                        Card(
                            title = titulo,
                            emojis = emojiList,
                            selectedLabels = seleccionados,
                            onSelectionChange = { updated ->
                                viewModel.updateCategory(titulo, updated)
                            },
                            color = when (titulo) {
                                "Estado de ánimo" -> Color(0xFF90CAF9)
                                "Síntomas" -> Color(0xFFF48FB1)
                                "Flujo vaginal" -> Color(0xFFEF9A9A)
                                "Actividad sexual" -> Color(0xFFFFCC80)
                                "Actividad física" -> Color(0xFFA5D6A7)
                                "Agua" -> Color(0xFF80DEEA)
                                "Anticonceptivo Oral" -> Color(0xFFD7CCC8)
                                else -> Color.LightGray
                            }
                        )
                        Spacer(Modifier.height(16.dp))
                    }
                    item {
                        OutlinedTextField(
                            value = logState.notes ?: "",
                            onValueChange = { viewModel.setNotes(it) },
                            label = { Text("Notas") },
                            modifier = Modifier.fillMaxWidth(0.9f)
                        )
                        Spacer(Modifier.height(24.dp))
                    }

                }



                }
            }

        }
    }
