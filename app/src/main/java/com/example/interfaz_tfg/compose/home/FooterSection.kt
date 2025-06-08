package com.example.interfaz_tfg.compose.home

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.interfaz_tfg.api.model.cycle.DailyLog
import com.example.interfaz_tfg.api.model.cycle.MenstrualCycle
import com.example.interfaz_tfg.api.model.user.UserDTO
import com.example.interfaz_tfg.compose.Footer

@Composable
fun FooterSection(
    navController: NavController,
    user: UserDTO?,
    token: String?,
    cycles: List<MenstrualCycle>,
    isBleeding: Boolean,
    logs: List<DailyLog>,
    modifier: Modifier = Modifier
) {
    user?.email?.let { email ->
        token?.let { validToken ->
            val confirmedPhases = cycles.filter { !it.isPredicted }.flatMap { it.phases }
            val predictedPhases = cycles.filter { it.isPredicted }.flatMap { it.phases }
            
            Footer(
                navController = navController,
                modifier = modifier,
                confirmedPhases,
                predictedPhases,
                email,
                validToken,
                isBleeding = isBleeding,
                logs
            )
        }
    }
}