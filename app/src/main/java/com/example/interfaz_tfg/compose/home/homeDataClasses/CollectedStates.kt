package com.example.interfaz_tfg.compose.home.homeDataClasses

import com.example.interfaz_tfg.api.model.cycle.DailyLog
import com.example.interfaz_tfg.api.model.cycle.MenstrualCycle
import com.example.interfaz_tfg.api.model.user.UserDTO

data class CollectedStates(
    val user: UserDTO?,
    val logs: List<DailyLog>,
    val isBleeding: Boolean,
    val cycles: List<MenstrualCycle>
)
