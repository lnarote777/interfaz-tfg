package com.example.interfaz_tfg.api.model.cycle

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDate
import java.time.LocalDate.now

@RequiresApi(Build.VERSION_CODES.O)
data class MenstrualCycle(
    val id: String? = null,
    val userId: String,
    val startDate: String,
    val endDate: String,
    val cycleLength: Int,
    val bleedingDuration: Int,
    val averageFlow: MenstrualFlowLevel,
    val logs: List<DailyLog> = listOf(),
    val isPredicted: Boolean = false,
    val phases: List<CyclePhaseDay> = listOf()
)

enum class MenstrualFlowLevel {
    LIGHT, MODERATE, HEAVY, CLOTS
}

enum class CyclePhase {
    MENSTRUATION, FOLLICULAR, OVULATION, LUTEAL
}
