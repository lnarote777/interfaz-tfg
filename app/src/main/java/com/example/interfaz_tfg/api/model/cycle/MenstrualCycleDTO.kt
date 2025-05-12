package com.example.interfaz_tfg.api.model.cycle

import java.time.LocalDate

data class MenstrualCycleDTO(
    val userId: String,
    val startDate: LocalDate,
    val cycleLength: Int,
    val bleedingDuration: Int,
    val averageFlow: MenstrualFlowLevel,
    val symptoms: List<String> = listOf(),
    val moodChanges: List<String> = listOf(),
    val notes: String? = null,
    val isPredicted: Boolean = false
)
