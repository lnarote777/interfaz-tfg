package com.example.interfaz_tfg.api.model.cycle

import java.time.LocalDate

data class DailyLog(
    val id: String? = null,                       // MongoDB ID
    val userId: String,                           // Reference to the user
    val date: String,                          // Log date
    val hasMenstruation: Boolean = false,
    val menstrualFlow: MenstrualFlowLevel? = null,
    val sexualActivity: List<String> = listOf(),  // e.g., "Intercourse", "Desire", "Masturbation"
    val mood: List<String> = listOf(),            // e.g., "Happy", "Irritable", "Sad"
    val symptoms: List<String> = listOf(),        // e.g., "Cramps", "Headache"
    val vaginalDischarge: List<String> = listOf(),// e.g., "Clear", "Sticky", "Creamy"
    val physicalActivity: List<String> = listOf(),// e.g., "Yoga", "Running"
    val pillsTaken: List<String> = listOf(),      // e.g., "Painkiller", "Contraceptive"
    val waterIntake: Int? = null,              // Liters of water (e.g., 1.5)
    val weight: Double? = null,                   // Weight in kg
    val notes: String? = null
)
