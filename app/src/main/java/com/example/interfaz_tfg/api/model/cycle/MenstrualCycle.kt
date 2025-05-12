package com.example.interfaz_tfg.api.model.cycle

import java.time.LocalDate
import java.time.LocalDate.now


data class MenstrualCycle(
    val id: String? = null,
    val userId: String,
    val startDate: LocalDate,
    val endDate: LocalDate,
    val cycleLength: Int,
    val bleedingDuration: Int,
    val averageFlow: MenstrualFlowLevel,
    val symptoms: List<String> = listOf(),
    val moodChanges: List<String> = listOf(),
    val registeredAt: LocalDate = now(),
    val notes: String? = null,
    val isPredicted: Boolean = false,
    val phases: List<CyclePhaseDay> = listOf()
)

enum class MenstrualFlowLevel {
    LIGHT, MODERATE, HEAVY, CLOTS
}

enum class CyclePhase {
    MENSTRUATION, FOLLICULAR, OVULATION, LUTEAL
}
