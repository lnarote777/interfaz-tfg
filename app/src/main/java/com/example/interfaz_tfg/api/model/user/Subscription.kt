package com.example.interfaz_tfg.api.model.user

data class Subscription(
    val email: String,
    val type: String // "mensual" o "unico"
)
