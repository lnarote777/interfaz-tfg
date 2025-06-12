package com.example.interfaz_tfg.screen

import com.example.interfaz_tfg.compose.StatCard
import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.interfaz_tfg.api.model.cycle.MonthlyStats
import com.example.interfaz_tfg.compose.Header
import com.example.interfaz_tfg.viewModel.DailyLogViewModel
import java.util.Calendar


@SuppressLint("NewApi")
@Composable
fun StatsScreen(navController: NavController){
    val context = LocalContext.current
    val calendar = Calendar.getInstance()
    val currentMonth = calendar.get(Calendar.MONTH) + 1 // 1-based
    val currentYear = calendar.get(Calendar.YEAR)
    val viewModel: DailyLogViewModel = viewModel()

    var stats by remember { mutableStateOf<MonthlyStats?>(null) }

    LaunchedEffect(Unit) {
        stats = viewModel.getMonthlyStats(currentMonth, currentYear)
    }

    Scaffold { padding ->
        Column(horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize().padding(padding)
        ) {
            Header(navController, "Estadísticas", onClick = {})
            stats?.let {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    StatCard(title = "Estado de ánimo más común", content = it.moodCounts.maxByOrNull { entry -> entry.value }?.key ?: "N/A")
                    StatCard(title = "Síntoma más común", content = it.symptomCounts.maxByOrNull { entry -> entry.value }?.key ?: "Ninguno")
                    StatCard(title = "Días con ejercicio", content = "${it.exerciseDays} días")
                    StatCard(title = "Media de agua consumida", content = "${"%.1f".format(it.averageWater)} L/día")
                    StatCard(title = "Registros de peso", content = "${it.weightRecords} días")
                }
            } ?: Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

    }
}
