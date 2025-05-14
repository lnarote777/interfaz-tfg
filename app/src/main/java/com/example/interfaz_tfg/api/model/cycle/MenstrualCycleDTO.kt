package com.example.interfaz_tfg.api.model.cycle

import java.time.LocalDate

data class MenstrualCycleDTO(
    val userId: String,
    val startDate: String,
    val cycleLength: Int,
    val bleedingDuration: Int,
    val averageFlow: MenstrualFlowLevel,
    val isPredicted: Boolean = false
)
