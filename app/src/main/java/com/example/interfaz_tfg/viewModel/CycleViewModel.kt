package com.example.interfaz_tfg.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.interfaz_tfg.api.ApiService
import com.example.interfaz_tfg.api.model.cycle.MenstrualCycle
import com.example.interfaz_tfg.api.model.cycle.MenstrualCycleDTO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate

//class CycleViewModel : ViewModel(){
//    private val _cycles = MutableStateFlow<List<MenstrualCycle>>(emptyList())
//    val cycles: StateFlow<List<MenstrualCycle>> get() = _cycles
//
//    private val _predictionDate = MutableStateFlow<LocalDate?>(null)
//    val predictionDate: StateFlow<LocalDate?> get() = _predictionDate
//
//    private val _error = MutableStateFlow<String?>(null)
//    val error: StateFlow<String?> get() = _error
//
//    fun loadCycles(userId: String) {
//        viewModelScope.launch {
//            try {
//                _cycles.value = ApiService.getCyclesByUser(userId)
//            } catch (e: Exception) {
//                _error.value = "Error loading cycles: ${e.message}"
//            }
//        }
//    }
//
//    fun createCycle(dto: MenstrualCycleDTO) {
//        viewModelScope.launch {
//            try {
//                ApiService.createCycle(dto)
//                loadCycles(dto.userId)
//            } catch (e: Exception) {
//                _error.value = "Error creating cycle: ${e.message}"
//            }
//        }
//    }
//
//    fun updateCycle(id: String, dto: MenstrualCycleDTO) {
//        viewModelScope.launch {
//            try {
//                ApiService.updateCycle(id, dto)
//                loadCycles(dto.userId)
//            } catch (e: Exception) {
//                _error.value = "Error updating cycle: ${e.message}"
//            }
//        }
//    }
//
//    fun predictNextCycle(userId: String) {
//        viewModelScope.launch {
//            try {
//                _predictionDate.value = ApiService.predictNextCycle(userId)
//            } catch (e: Exception) {
//                _error.value = "Error predicting cycle: ${e.message}"
//            }
//        }
//    }
//}
