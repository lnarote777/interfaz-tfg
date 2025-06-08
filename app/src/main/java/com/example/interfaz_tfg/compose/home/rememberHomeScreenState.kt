package com.example.interfaz_tfg.compose.home

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.example.interfaz_tfg.compose.home.homeDataClasses.HomeScreenUIState
import java.time.LocalDate

@SuppressLint("NewApi")
@Composable
fun rememberHomeScreenState(): MutableState<HomeScreenUIState> {
    val currentDate = LocalDate.now()
    return remember {
        mutableStateOf(
            HomeScreenUIState(
                selectedDate = currentDate,
                currentMonth = currentDate.month,
                currentYear = currentDate.year,
                lastRecalculationDate = null,
                phasesUpdateTrigger = 0
            )
        )
    }
}