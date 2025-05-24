package com.example.interfaz_tfg.api.model.user

import java.util.Date

data class UserEntity(
    val id: String, //email
    val name: String,
    var username: String,
    var password: String,
    var roles: String, //Premium or user
    val birthDate: String,
    val registrationDate: Date,
    var weight: Double,
    var height: Double,
    var goal: Goal
)

enum class Goal {
    GET_PREGNANT, TRACK_PERIOD, AVOID_PREGNANCY
}