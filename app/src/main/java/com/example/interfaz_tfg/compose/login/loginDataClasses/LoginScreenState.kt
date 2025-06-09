package com.example.interfaz_tfg.compose.login.loginDataClasses

data class LoginScreenState(
    val user: String = "",
    val password: String = "",
    val passVisible: Boolean = false,
    val errorMessage: String = "",
    val showError: Boolean = false
)