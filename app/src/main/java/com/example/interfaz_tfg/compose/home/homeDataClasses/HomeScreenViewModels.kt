package com.example.interfaz_tfg.compose.home.homeDataClasses

import com.example.interfaz_tfg.viewModel.CycleViewModel
import com.example.interfaz_tfg.viewModel.DailyLogViewModel
import com.example.interfaz_tfg.viewModel.UserViewModel

data class HomeScreenViewModels(
    val userViewModel: UserViewModel,
    val cycleViewModel: CycleViewModel,
    val dailyLogViewModel: DailyLogViewModel
)