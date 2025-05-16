package com.example.interfaz_tfg.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.interfaz_tfg.api.API
import com.example.interfaz_tfg.api.ApiService
import com.example.interfaz_tfg.api.model.cycle.CyclePhaseDay
import com.example.interfaz_tfg.api.model.cycle.MenstrualCycle
import com.example.interfaz_tfg.api.model.cycle.MenstrualCycleDTO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate

class CycleViewModel : ViewModel(){
     private val _cycles = MutableStateFlow<List<MenstrualCycle>>(emptyList())
    val cycles: StateFlow<List<MenstrualCycle>> = _cycles

    private val _selectedCycle = MutableStateFlow<MenstrualCycle?>(null)
    val selectedCycle: StateFlow<MenstrualCycle?> = _selectedCycle

    private val _phases = MutableStateFlow<List<CyclePhaseDay>>(emptyList())
    val phases: StateFlow<List<CyclePhaseDay>> = _phases

    private val _cycleState = MutableStateFlow<MenstrualCycle?>(null)
    val cycleState: StateFlow<MenstrualCycle?> = _cycleState

    fun loadCycles(userId: String) {
        viewModelScope.launch {
            try {
                val response = API.retrofitService.getCyclesUser(userId)
                if (response.isSuccessful){
                    _cycles.value = response.body() ?: emptyList()
                }
            }catch (e: Exception){
                Log.d("EXCEPCION", "loadCycles: ${e.message}")
            }


        }
    }

    fun createCycle(dto: MenstrualCycleDTO) {
        viewModelScope.launch {
            try {
                val response = API.retrofitService.createCycle( dto)
                if (response.isSuccessful){
                    val cycle = response.body()!!
                    loadCycles(cycle.userId)
                }else{
                    Log.d("ERROR", "createCycle: error ${response.code()}")
                }

            } catch (e: Exception) {
                Log.d("ERROR EXCEPCION", "createCycle: error ${e.message}")
            }
        }
    }

    fun recalculateCycle(userId: String, date: LocalDate? = null) {
        viewModelScope.launch {
            try {
                val response = API.retrofitService.recalculateCycle(userId, date?.toString())
                if (response.isSuccessful) {
                    val updatedCycle = response.body()
                    _cycleState.value = updatedCycle
                } else {
                    Log.e("API", "Error: ${response.code()}")
                }
            } catch (e: Exception) {
                Log.e("API", "Exception: ${e.message}")
            }
        }
    }

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
}
