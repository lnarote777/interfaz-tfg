package com.example.interfaz_tfg.compose.profile

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.example.interfaz_tfg.utils.UserPreferences
import com.example.interfaz_tfg.viewModel.CycleViewModel
import com.example.interfaz_tfg.viewModel.UserViewModel
import kotlinx.coroutines.flow.firstOrNull

@Composable
fun UserScreenEffects(
    context: Context,
    email: String?,
    username: String?,
    userViewModel: UserViewModel,
    cycleViewModel: CycleViewModel,
    onTokenLoaded: (String?) -> Unit
) {
    LaunchedEffect(Unit) {
        val token = UserPreferences.getToken(context).firstOrNull()
        onTokenLoaded(token)
        userViewModel.initFromPreferences(context)
    }

    LaunchedEffect(email) {
        email?.let { userEmail ->
            cycleViewModel.loadCycles(userEmail)
            username?.let { userName ->
                userViewModel.getUserByUsername(userName)
            }
        }
    }
}