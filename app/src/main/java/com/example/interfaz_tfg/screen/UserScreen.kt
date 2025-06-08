package com.example.interfaz_tfg.screen


import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.interfaz_tfg.R
import com.example.interfaz_tfg.utils.UserPreferences
import com.example.interfaz_tfg.api.model.user.Goal
import com.example.interfaz_tfg.compose.Header
import com.example.interfaz_tfg.compose.ProfileImage
import com.example.interfaz_tfg.compose.configuraciones.SettingCard
import com.example.interfaz_tfg.compose.configuraciones.SettingItem
import com.example.interfaz_tfg.compose.profile.UserScreenEffects
import com.example.interfaz_tfg.compose.profile.UserScreenUI
import com.example.interfaz_tfg.compose.profile.profileDataClasses.UserInfo
import com.example.interfaz_tfg.navigation.AppScreen
import com.example.interfaz_tfg.viewModel.CycleViewModel
import com.example.interfaz_tfg.viewModel.UserViewModel
import com.example.interfaz_tfg.viewModel.getRolFromToken
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import com.example.interfaz_tfg.compose.profile.profileDataClasses.UserScreenState
import com.example.interfaz_tfg.utils.performDeleteAccount
import com.example.interfaz_tfg.utils.performLogout

@Composable
fun UserScreen(
    navController: NavController,
    username: String?,
    email: String?
) {
    val userViewModel: UserViewModel = viewModel()
    val cycleViewModel : CycleViewModel = viewModel()

    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    val user = userViewModel.user.collectAsState()
    val cycles = cycleViewModel.cycles.collectAsState()
    val selectedImageUri by userViewModel.selectedImageUri.collectAsState()

    var screenState by remember { mutableStateOf(UserScreenState()) }

    val userInfo = remember(user.value, cycles.value) {
        val cycle = cycles.value.find { !it.isPredicted }
        UserInfo(
            username = username,
            email = email,
            goal = user.value?.goal,
            periodDuration = cycle?.bleedingDuration?.toString() ?: "N/A",
            cycleDuration = cycle?.cycleLength?.toString() ?: "N/A"
        )
    }

    UserScreenEffects(
        context = context,
        email = email,
        username = username,
        userViewModel = userViewModel,
        cycleViewModel = cycleViewModel,
        onTokenLoaded = { token ->
            screenState = screenState.copy(token = token)
        }
    )

    UserScreenUI(
        navController = navController,
        userInfo = userInfo,
        screenState = screenState,
        selectedImageUri = selectedImageUri,
        userViewModel = userViewModel,
        onStateChange = { newState -> screenState = newState },
        onLogout = {
            performLogout(context, navController, coroutineScope)
        },
        onDeleteAccount = {
            performDeleteAccount(
                context = context,
                email = email,
                confirmationEmail = screenState.confirmationEmail,
                userViewModel = userViewModel,
                navController = navController,
                coroutineScope = coroutineScope,
                onError = {
                    screenState = screenState.copy(emailError = true)
                }
            )
        }
    )
}