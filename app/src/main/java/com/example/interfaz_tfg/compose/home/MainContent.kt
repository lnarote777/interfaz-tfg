package com.example.interfaz_tfg.compose.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.interfaz_tfg.compose.home.homeDataClasses.CollectedStates
import com.example.interfaz_tfg.compose.home.homeDataClasses.HomeScreenUIState
import com.example.interfaz_tfg.compose.home.homeDataClasses.HomeScreenViewModels
import com.example.interfaz_tfg.compose.home.homeDataClasses.PeriodState
import kotlinx.coroutines.CoroutineScope
import org.intellij.lang.annotations.JdkConstants

@Composable
fun MainContent(
    navController: NavController,
    token: String?,
    uiState: MutableState<HomeScreenUIState>,
    collectedStates: CollectedStates,
    viewModels: HomeScreenViewModels,
    periodState: PeriodState,
    scope: CoroutineScope
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        PeriodCircle(
            navController = navController,
            token = token,
            user = collectedStates.user,
            logs = collectedStates.logs,
            isBleeding = collectedStates.isBleeding,
            periodState = periodState,
            viewModels = viewModels,
            scope = scope
        )
        
        Spacer(Modifier.height(60.dp))

        CalendarMonth(
            uiState = uiState,
            collectedStates = collectedStates
        )
        
        Spacer(Modifier.height(100.dp))
    }
}
