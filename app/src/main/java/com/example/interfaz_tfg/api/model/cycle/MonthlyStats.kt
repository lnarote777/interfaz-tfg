package com.example.interfaz_tfg.api.model.cycle

data class MonthlyStats(
    val moodCounts: Map<String, Int>,
    val symptomCounts: Map<String, Int>,
    val exerciseDays: Int,
    val averageWater: Float,
    val weightRecords: Int
)
