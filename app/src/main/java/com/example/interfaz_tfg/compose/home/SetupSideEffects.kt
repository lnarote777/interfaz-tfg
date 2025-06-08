package com.example.interfaz_tfg.compose.home

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import com.example.interfaz_tfg.compose.home.homeDataClasses.CollectedStates
import com.example.interfaz_tfg.compose.home.homeDataClasses.HomeScreenUIState
import com.example.interfaz_tfg.compose.home.homeDataClasses.HomeScreenViewModels
import com.example.interfaz_tfg.utils.handleCycleRecalculation
import java.time.LocalDate


@SuppressLint("NewApi")
@Composable
fun SetupSideEffects(
    username: String?,
    token: String?,
    viewModels: HomeScreenViewModels,
    collectedStates: CollectedStates,
    uiState: MutableState<HomeScreenUIState>
) {
    val currentDate = LocalDate.now()
    
    // Initial user loading
    LaunchedEffect(Unit) {
        username?.let { viewModels.userViewModel.getUserByUsername(it) }
    }
    
    // Load user data when user changes
    LaunchedEffect(collectedStates.user?.email) {
        collectedStates.user?.email?.let { email ->
            with(viewModels) {
                cycleViewModel.getPrediction(email)
                cycleViewModel.loadCycles(email)
                dailyLogViewModel.loadLogs(email)
                userViewModel.getUserByUsername(collectedStates.user.username)
            }
        }
    }
    
    // Handle date selection changes
    LaunchedEffect(uiState.value.selectedDate) {
        collectedStates.user?.email?.let { email ->
            token?.let { 
                viewModels.dailyLogViewModel.setSelectedDate(uiState.value.selectedDate, email, it)
            }
        }
        viewModels.dailyLogViewModel.updateBleedingStatusForToday(
            collectedStates.logs, 
            uiState.value.selectedDate
        )
    }
    
    // Update bleeding status when logs change
    LaunchedEffect(collectedStates.logs) {
        viewModels.dailyLogViewModel.updateBleedingStatusForToday(collectedStates.logs, currentDate)
    }
    
    // Handle cycle recalculation
    LaunchedEffect(collectedStates.user?.email, collectedStates.logs, currentDate) {
        handleCycleRecalculation(
            email = collectedStates.user?.email,
            logs = collectedStates.logs,
            currentDate = currentDate,
            lastRecalculationDate = uiState.value.lastRecalculationDate,
            cycleViewModel = viewModels.cycleViewModel,
            onRecalculationComplete = { 
                uiState.value = uiState.value.copy(lastRecalculationDate = currentDate)
            }
        )
    }
}