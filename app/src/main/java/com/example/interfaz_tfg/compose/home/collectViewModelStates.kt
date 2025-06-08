package com.example.interfaz_tfg.compose.home

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.example.interfaz_tfg.compose.home.homeDataClasses.CollectedStates
import com.example.interfaz_tfg.compose.home.homeDataClasses.HomeScreenViewModels

@Composable
fun collectViewModelStates(viewModels: HomeScreenViewModels): CollectedStates {
    val user by viewModels.userViewModel.user.collectAsState()
    val logs by viewModels.dailyLogViewModel.logs.collectAsState()
    val isBleeding by viewModels.dailyLogViewModel.isBleeding.collectAsState()
    val cycles by viewModels.cycleViewModel.cycles.collectAsState(initial = emptyList())
    
    return CollectedStates(user, logs, isBleeding, cycles)
}