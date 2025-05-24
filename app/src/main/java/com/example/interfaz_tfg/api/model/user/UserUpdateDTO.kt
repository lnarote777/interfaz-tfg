package com.example.interfaz_tfg.api.model.user

data class UserUpdateDTO (
    val email: String,
    var username: String,
    var password: String,
    var goal: Goal = Goal.TRACK_PERIOD
)
