package com.example.interfaz_mesames.api.model

data class UserRegisterDTO (
    val email: String,
    val username: String,
    val password: String,
    val passwordRepeat: String,
    val name: String,
    val birthDate: String,
    //val goal: Goal = Goal.TRACK_PERIOD
)
