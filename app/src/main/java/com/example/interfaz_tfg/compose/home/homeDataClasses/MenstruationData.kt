package com.example.interfaz_tfg.compose.home.homeDataClasses

import java.time.LocalDate

data class MenstruationData(
    val confirmedDates: List<LocalDate>,
    val predictedDates: List<LocalDate>,
    val allDates: List<LocalDate>,
    val ranges: List<Pair<LocalDate, LocalDate>>
)