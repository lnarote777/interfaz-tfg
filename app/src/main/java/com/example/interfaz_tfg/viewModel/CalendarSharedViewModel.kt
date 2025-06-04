package com.example.interfaz_tfg.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.interfaz_tfg.api.model.cycle.CyclePhase
import com.example.interfaz_tfg.api.model.cycle.CyclePhaseDay

class CalendarSharedViewModel : ViewModel() {
    var confirmedPhases by mutableStateOf<List<CyclePhaseDay>>(emptyList())
    var predictedPhases by mutableStateOf<List<CyclePhaseDay>>(emptyList())
}