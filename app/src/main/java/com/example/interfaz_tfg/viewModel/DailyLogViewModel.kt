package com.example.interfaz_tfg.viewModel

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.interfaz_tfg.api.API
import com.example.interfaz_tfg.api.model.cycle.DailyLogDTO
import com.example.interfaz_tfg.api.model.cycle.LogState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate

class DailyLogViewModel : ViewModel(){
    private val _logState = MutableStateFlow(LogState())
    val logState: StateFlow<LogState> = _logState

    @RequiresApi(Build.VERSION_CODES.O)
    private val _selectedDate = MutableStateFlow(LocalDate.now())
    @RequiresApi(Build.VERSION_CODES.O)
    val selectedDate: StateFlow<LocalDate> = _selectedDate

    @RequiresApi(Build.VERSION_CODES.O)
    fun setSelectedDate(date: LocalDate) {
        _selectedDate.value = date
    }

    fun setNotes(notes: String) {
        _logState.value = _logState.value.copy(notes = notes)
    }

    fun setWaterIntake(water: Int?) {
        _logState.value = _logState.value.copy(waterIntake = water)
    }

    fun setWeight(weight: Float?) {
        _logState.value = _logState.value.copy(weight = weight)
    }

    fun updateCategory(category: String, selectedEmojis: List<String>) {
        _logState.value = when (category) {
            "Estado de ánimo" -> _logState.value.copy(mood = selectedEmojis)
            "Síntomas" -> _logState.value.copy(symptoms = selectedEmojis)
            "Flujo menstrual" -> _logState.value.copy(menstrualFlow = selectedEmojis.toString())
            "Flujo vaginal" -> _logState.value.copy(vaginalDischarge = selectedEmojis)
            "Actividad sexual" -> _logState.value.copy(sexualActivity = selectedEmojis)
            "Actividad física" -> _logState.value.copy(physicalActivity = selectedEmojis)
            "Anticonceptivo Oral" -> _logState.value.copy(pillsTaken = selectedEmojis)
            else -> _logState.value
        }
    }
    @RequiresApi(Build.VERSION_CODES.O)
    fun createLog(token: String, email: String, logDTO: DailyLogDTO, onSuccess: () -> Unit, onError: (String) -> Unit) {
        viewModelScope.launch {
            try {
                val response = API.retrofitService.createLog("Bearer $token", email, logDTO)
                if (response.isSuccessful) {
                    onSuccess()
                } else {
                    onError("Error: ${response.code()}")
                }
            } catch (e: Exception) {
                onError("Error de red: ${e.localizedMessage}")
                Log.d("ERROR", "createLog: ${e.localizedMessage}")
            }
        }
    }
}
