package com.example.interfaz_tfg.compose.home.homeDataClasses

import java.time.LocalDate
import java.time.Month

data class HomeScreenUIState(
    val selectedDate: LocalDate,
    val currentMonth: Month,
    val currentYear: Int,
    val lastRecalculationDate: LocalDate?,
    val phasesUpdateTrigger: Int
)