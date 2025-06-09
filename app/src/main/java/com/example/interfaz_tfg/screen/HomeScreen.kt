package com.example.interfaz_tfg.screen

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.example.interfaz_tfg.compose.home.HomeScreenContent
import com.example.interfaz_tfg.compose.home.SetupSideEffects
import com.example.interfaz_tfg.compose.home.collectViewModelStates
import com.example.interfaz_tfg.compose.home.rememberHomeScreenState
import com.example.interfaz_tfg.compose.home.rememberViewModels
import com.example.interfaz_tfg.utils.calculateMenstruationData
import com.example.interfaz_tfg.utils.calculatePeriodState
import com.example.interfaz_tfg.utils.updateCalendarSharedViewModel
import com.example.interfaz_tfg.viewModel.CalendarSharedViewModel


@SuppressLint("NewApi")
@Composable
fun HomeScreen(
    navController: NavController,
    username: String? ,
    userRol: String?,
    token: String?,
    calendarSharedViewModel: CalendarSharedViewModel
){
    val viewModels = rememberViewModels()

    val uiState = rememberHomeScreenState()

    val collectedStates = collectViewModelStates(viewModels)

    SetupSideEffects(
        username = username,
        token = token,
        viewModels = viewModels,
        collectedStates = collectedStates,
        uiState = uiState
    )

    val menstruationData = calculateMenstruationData(collectedStates.cycles)

    // Calculate period state
    val periodState = calculatePeriodState(
        menstruationData = menstruationData,
        selectedDate = uiState.value.selectedDate,
        isBleeding = collectedStates.isBleeding
    )

    // Update shared view model
    updateCalendarSharedViewModel(calendarSharedViewModel, collectedStates.cycles)

    // UI Rendering
    HomeScreenContent(
        navController = navController,
        userRol = userRol,
        token = token,
        uiState = uiState,
        collectedStates = collectedStates,
        viewModels = viewModels,
        periodState = periodState,
        menstruationData = menstruationData
    )
}