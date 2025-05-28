package com.example.interfaz_tfg.api.model.cycle

data class LogState(
    val mood: MutableList<String?> = mutableListOf(),
    val symptoms: MutableList<String?> = mutableListOf(),
    val vaginalDischarge: MutableList<String?> = mutableListOf(),
    val sexualActivity: MutableList<String?> = mutableListOf(),
    val physicalActivity: MutableList<String?> = mutableListOf(),
    val pillsTaken: MutableList<String?> = mutableListOf(),
    val waterIntake: Int? = null, // Podría ser el número de litros, o cantidad en mililitros
    val weight: Double? = null, // El peso en kg, puede ser null si no se ha registrado
    val notes: String? = null, // Las notas adicionales
    val hasMenstruation: Boolean = false, // Para saber si tiene menstruación en el día
    val menstrualFlow: String? = null // Flujo menstrual, puede ser "ligero", "medio", "abundante"
)
