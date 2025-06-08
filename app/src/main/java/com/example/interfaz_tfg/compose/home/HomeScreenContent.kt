package com.example.interfaz_tfg.compose.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.interfaz_tfg.compose.home.homeDataClasses.CollectedStates
import com.example.interfaz_tfg.compose.home.homeDataClasses.HomeScreenUIState
import com.example.interfaz_tfg.compose.home.homeDataClasses.HomeScreenViewModels
import com.example.interfaz_tfg.compose.home.homeDataClasses.MenstruationData
import com.example.interfaz_tfg.compose.home.homeDataClasses.PeriodState

@Composable
fun HomeScreenContent(
    navController: NavController,
    userRol: String?,
    token: String?,
    uiState: MutableState<HomeScreenUIState>,
    collectedStates: CollectedStates,
    viewModels: HomeScreenViewModels,
    periodState: PeriodState,
    menstruationData: MenstruationData
) {
    val scope = rememberCoroutineScope()
    val color = MaterialTheme.colorScheme
    
    Scaffold { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            BackgroundImage()
            
            Column(modifier = Modifier.fillMaxSize()) {
                collectedStates.user?.let {
                    TopNavigationBar(
                        navController = navController,
                        userRol = userRol,
                        user = it,
                        color = color
                    )
                }
                
                MainContent(
                    navController = navController,
                    token = token,
                    uiState = uiState,
                    collectedStates = collectedStates,
                    viewModels = viewModels,
                    periodState = periodState,
                    scope = scope
                )
            }
            
            FooterSection(
                navController = navController,
                user = collectedStates.user,
                token = token,
                cycles = collectedStates.cycles,
                isBleeding = collectedStates.isBleeding,
                logs = collectedStates.logs,
                modifier = Modifier.align(Alignment.BottomCenter)
            )
        }
    }
}
