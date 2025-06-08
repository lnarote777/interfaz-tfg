package com.example.interfaz_tfg.compose.home

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.interfaz_tfg.compose.home.homeDataClasses.HomeScreenViewModels
import com.example.interfaz_tfg.viewModel.CycleViewModel
import com.example.interfaz_tfg.viewModel.DailyLogViewModel
import com.example.interfaz_tfg.viewModel.UserViewModel

@Composable
fun rememberViewModels(): HomeScreenViewModels {
    val userViewModel = viewModel<UserViewModel>()
    val cycleViewModel = viewModel<CycleViewModel>()
    val dailyLogViewModel = viewModel<DailyLogViewModel>()

    return remember {
        HomeScreenViewModels(
            userViewModel = userViewModel,
            cycleViewModel = cycleViewModel,
            dailyLogViewModel = dailyLogViewModel
        )
    }
}