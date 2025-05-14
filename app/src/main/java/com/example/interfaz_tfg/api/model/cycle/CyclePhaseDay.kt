package com.example.interfaz_tfg.api.model.cycle


data class CyclePhaseDay(
    val date: String,
    val phase: CyclePhase,
    var isPredicted: Boolean
)