package com.example.interfaz_tfg.api.model.cycle

data class LogState(
    val mood: List<String> = emptyList(),
    val symptoms: List<String> = emptyList(),
    val vaginalDischarge: List<String> = emptyList(),
    val sexualActivity: List<String> = emptyList(),
    val physicalActivity: List<String> = emptyList(),
    val pillsTaken: List<String> = emptyList(),
    val waterIntake: Int? = null, // Podría ser el número de litros, o cantidad en mililitros
    val weight: Float? = null, // El peso en kg, puede ser null si no se ha registrado
    val notes: String? = null, // Las notas adicionales
    val hasMenstruation: Boolean = false, // Para saber si tiene menstruación en el día
    val menstrualFlow: String? = null // Flujo menstrual, puede ser "ligero", "medio", "abundante"
)
