package com.example.interfaz_tfg.utils

import android.content.Context
import androidx.navigation.NavController
import com.example.interfaz_tfg.compose.login.loginDataClasses.LoginFormData
import com.example.interfaz_tfg.viewModel.LoginViewModel

fun performLogin(
    context: Context,
    formData: LoginFormData,
    viewModel: LoginViewModel,
    navController: NavController,
    onValidationError: (String) -> Unit
) {
    when {
        !formData.isValid -> {
            onValidationError("Por favor, completa ambos campos")
        }
        formData.user.isBlank() -> {
            onValidationError("El campo usuario es requerido")
        }
        formData.password.isBlank() -> {
            onValidationError("El campo contraseña es requerido")
        }
        else -> {
            viewModel.login(
                context = context,
                username = formData.user,
                password = formData.password,
                navController = navController
            )
        }
    }
}