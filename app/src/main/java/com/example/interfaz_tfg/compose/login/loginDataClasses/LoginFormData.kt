package com.example.interfaz_tfg.compose.login.loginDataClasses

data class LoginFormData(
    val user: String,
    val password: String,
    val isValid: Boolean
) {
    companion object {
        fun from(state: LoginScreenState): LoginFormData {
            return LoginFormData(
                user = state.user.trim(),
                password = state.password,
                isValid = state.user.isNotBlank() && state.password.isNotBlank()
            )
        }
    }
}