package com.example.interfaz_tfg.api.model.cycle

import java.time.LocalDate

data class CyclePhaseDay(
    val date: LocalDate,
    val phase: CyclePhase
)