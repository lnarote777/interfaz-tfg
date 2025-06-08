package com.example.interfaz_tfg.screen

import android.annotation.SuppressLint
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.interfaz_tfg.R
import com.example.interfaz_tfg.api.model.cycle.CyclePhase
import com.example.interfaz_tfg.compose.Footer
import com.example.interfaz_tfg.compose.calendario.Month
import com.example.interfaz_tfg.compose.home.HomeScreenContent
import com.example.interfaz_tfg.compose.home.SetupSideEffects
import com.example.interfaz_tfg.compose.home.collectViewModelStates
import com.example.interfaz_tfg.compose.home.homeDataClasses.HomeScreenViewModels
import com.example.interfaz_tfg.compose.home.rememberHomeScreenState
import com.example.interfaz_tfg.compose.home.rememberViewModels
import com.example.interfaz_tfg.navigation.AppScreen
import com.example.interfaz_tfg.utils.calculateMenstruationData
import com.example.interfaz_tfg.utils.calculatePeriodState
import com.example.interfaz_tfg.utils.groupContinuousDates
import com.example.interfaz_tfg.utils.updateCalendarSharedViewModel
import com.example.interfaz_tfg.viewModel.CalendarSharedViewModel
import com.example.interfaz_tfg.viewModel.CycleViewModel
import com.example.interfaz_tfg.viewModel.DailyLogViewModel
import com.example.interfaz_tfg.viewModel.UserViewModel
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.temporal.ChronoUnit
import java.time.Month as JavaMonth


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