package com.example.interfaz_tfg.compose.profile.profileDataClasses

import com.example.interfaz_tfg.api.model.user.Goal

data class UserInfo(
    val username: String?,
    val email: String?,
    val goal: Goal?,
    val periodDuration: String,
    val cycleDuration: String
)