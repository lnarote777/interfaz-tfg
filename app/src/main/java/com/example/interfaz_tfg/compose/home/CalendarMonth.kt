package com.example.interfaz_tfg.compose.home

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import com.example.interfaz_tfg.compose.calendario.Month
import com.example.interfaz_tfg.compose.home.homeDataClasses.CollectedStates
import com.example.interfaz_tfg.compose.home.homeDataClasses.HomeScreenUIState
import java.time.LocalDate
import java.time.Month as JavaMonth

@SuppressLint("NewApi")
@Composable
fun CalendarMonth(
    uiState: MutableState<HomeScreenUIState>,
    collectedStates: CollectedStates
) {
    val confirmedPhases = collectedStates.cycles
        .filter { !it.isPredicted }
        .flatMap { it.phases }
    val predictedPhases = collectedStates.cycles
        .filter { it.isPredicted }
        .flatMap { it.phases }
    
    Month(
        year = uiState.value.currentYear,
        month = uiState.value.currentMonth,
        selectedDate = uiState.value.selectedDate,
        currentDate = LocalDate.now(),
        confirmedPhases = confirmedPhases,
        predictedPhases = predictedPhases,
        logs = collectedStates.logs,
        showNavigationArrows = true,
        onDateSelected = { date -> 
            uiState.value = uiState.value.copy(selectedDate = date)
        },
        onPreviousMonth = {
            val prev = uiState.value.currentMonth.minus(1)
            val newYear = if (prev == JavaMonth.DECEMBER) {
                uiState.value.currentYear - 1
            } else {
                uiState.value.currentYear
            }
            uiState.value = uiState.value.copy(currentMonth = prev, currentYear = newYear)
        },
        onNextMonth = {
            val next = uiState.value.currentMonth.plus(1)
            val newYear = if (next == JavaMonth.JANUARY) {
                uiState.value.currentYear + 1
            } else {
                uiState.value.currentYear
            }
            uiState.value = uiState.value.copy(currentMonth = next, currentYear = newYear)
        }
    )
}
