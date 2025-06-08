package com.example.interfaz_tfg.compose.home.homeDataClasses

data class PeriodState(
    val dayInPeriod: Int? = null,
    val daysUntilNextPeriod: Int = -1,
    val isTodayInMenstruation: Boolean = false,
    val periodText: String = ""
)