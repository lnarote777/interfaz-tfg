package com.example.interfaz_tfg.compose.profile.profileDataClasses

data class UserScreenState(
    val showDeleteDialog: Boolean = false,
    val confirmationEmail: String = "",
    val emailError: Boolean = false,
    val token: String? = null
)