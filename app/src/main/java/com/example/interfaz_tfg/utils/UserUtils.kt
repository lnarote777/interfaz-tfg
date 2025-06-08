package com.example.interfaz_tfg.utils

import androidx.navigation.NavController
import com.example.interfaz_tfg.api.model.user.Goal
import com.example.interfaz_tfg.navigation.AppScreen
import com.example.interfaz_tfg.viewModel.UserViewModel
import kotlinx.coroutines.launch

fun Goal?.toDisplayString(): String {
    return when (this) {
        Goal.GET_PREGNANT -> "Quedar embarazada"
        Goal.TRACK_PERIOD -> "Seguimiento general"
        Goal.AVOID_PREGNANCY -> "Evitar embarazo"
        else -> "Sin especificar"
    }
}

fun performLogout(
    context: android.content.Context,
    navController: NavController,
    coroutineScope: kotlinx.coroutines.CoroutineScope
) {
    coroutineScope.launch {
        UserPreferences.clearUser(context)
        navController.navigate(AppScreen.CoverScreen.route) {
            popUpTo(0) { inclusive = true }
        }
    }
}

fun performDeleteAccount(
    context: android.content.Context,
    email: String?,
    confirmationEmail: String,
    userViewModel: UserViewModel,
    navController: NavController,
    coroutineScope: kotlinx.coroutines.CoroutineScope,
    onError: () -> Unit
) {
    email?.let { userEmail ->
        if (confirmationEmail == userEmail) {
            userViewModel.deleteUser(
                context = context,
                email = userEmail,
                onSuccess = {
                    coroutineScope.launch {
                        UserPreferences.clearUser(context)
                        navController.navigate(AppScreen.CoverScreen.route) {
                            popUpTo(AppScreen.UserScreen.route) { inclusive = true }
                        }
                    }
                },
                onError = { onError() }
            )
        } else {
            onError()
        }
    }
}
