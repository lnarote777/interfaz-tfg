package com.example.interfaz_tfg.api.model.cycle

data class OptionItem(
    val id: String? = null,
    val category: OptionCategory,
    val value: String,              // "Dolor de cabeza"
    val icon: String,               // Emoji o URL: "🤕" o "/icons/headache.png"
    val isDefault: Boolean = true,
    val userId: String? = null
)

enum class OptionCategory {
    SYMPTOM,
    MOOD,
    SEXUAL_ACTIVITY,
    VAGINAL_DISCHARGE,
    PHYSICAL_ACTIVITY,
    PILL
}