package com.example.interfaz_tfg.viewModel

import android.annotation.SuppressLint
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.interfaz_tfg.api.API
import com.example.interfaz_tfg.api.model.cycle.CyclePhaseDay
import com.example.interfaz_tfg.api.model.cycle.DailyLog
import com.example.interfaz_tfg.api.model.cycle.MenstrualCycle
import com.example.interfaz_tfg.api.model.cycle.MenstrualCycleDTO
import com.example.interfaz_tfg.api.model.cycle.MenstrualFlowLevel
import com.example.interfaz_tfg.navigation.AppScreen
import com.example.interfaz_tfg.utils.groupContinuousDates
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.temporal.ChronoUnit

class CycleViewModel : ViewModel(){
     private val _cycles = MutableStateFlow<List<MenstrualCycle>>(emptyList())
    val cycles: StateFlow<List<MenstrualCycle>> = _cycles

    // Ciclo seleccionado actualmente en la UI (visto para futuro)
    private val _selectedCycle = MutableStateFlow<MenstrualCycle?>(null)
    val selectedCycle: StateFlow<MenstrualCycle?> = _selectedCycle

    private val _phases = MutableStateFlow<List<CyclePhaseDay>>(emptyList())
    val phases: StateFlow<List<CyclePhaseDay>> = _phases

    private val _cycleState = MutableStateFlow<MenstrualCycle?>(null)
    val cycleState: StateFlow<MenstrualCycle?> = _cycleState

    private val _predictedCycles = MutableStateFlow<List<MenstrualCycle>>(emptyList())
    val predictedCycles: StateFlow<List<MenstrualCycle>> get() = _predictedCycles

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    //Llamadas a API
    // Carga los ciclos menstruales confirmados para un usuario dado
    fun loadCycles(userId: String) {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                val response = API.retrofitService.getCyclesUser( userId)
                if (response.isSuccessful){
                    _cycles.value = response.body()!!
                }else{
                    Log.e("ERROR", "loadcycles - error: ${response.code()}, ${response.errorBody()}")
                }
            }catch (e: Exception){
                Log.d("EXCEPCION", "loadCycles: ${e.message}")
            } finally {
                _isLoading.value = false
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getPrediction(email: String){
        val today = LocalDate.now()
        val hasUpcomingPrediction = _predictedCycles.value.any {
            LocalDate.parse(it.startDate).isAfter(today)
        }

        if (hasUpcomingPrediction) return // Ya tienes una predicción futura, no hagas nada

        _isLoading.value = true
        viewModelScope.launch {
            try {
                val response = API.retrofitService.getPrediction(email)
                if (response.isSuccessful) {
                    _predictedCycles.value = response.body()?.filterNotNull() ?: emptyList()

                } else {
                    Log.e("CycleViewModel", "Error obtener predicción: ${response.code()}")
                }
            } catch (e: Exception) {
                Log.e("CycleViewModel", "Exception obtener predicción", e)
            } finally {
                _isLoading.value = false
            }
        }
    }

    // Crea un nuevo ciclo menstrual y recarga la lista de ciclos
    @RequiresApi(Build.VERSION_CODES.O)
    fun createCycle(dto: MenstrualCycleDTO) {
        viewModelScope.launch {
            try {
                val response = API.retrofitService.createCycle( dto)
                if (response.isSuccessful){
                    val cycle = response.body()!!
                    loadCycles( cycle.userId)
                    if (_predictedCycles.value.none { LocalDate.parse(it.startDate) > LocalDate.now() }) {
                        getPrediction(cycle.userId)
                    }
                }else{
                    Log.d("ERROR", "createCycle: error ${response.code()}")
                }

            } catch (e: Exception) {
                Log.d("ERROR EXCEPCION", "createCycle: error ${e.message}")
            }
        }
    }

    // Recalcula el ciclo menstrual para un usuario en base a una fecha opcional (por ejemplo tras añadir logs)
    fun recalculateCycle(token: String, userId: String, date: LocalDate? = null) {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                val response = API.retrofitService.recalculateCycle("Bearer $token", userId, date?.toString())
                if (response.isSuccessful) {
                    val updatedCycle = response.body()
                    _cycleState.value = updatedCycle
                } else {
                    Log.e("API", "Error: ${response.code()}")
                }
            } catch (e: Exception) {
                Log.e("API", "Exception: ${e.message}")
            } finally {
                _isLoading.value = false
            }
        }
    }
    // Actualiza o crea un ciclo menstrual en base a una lista de logs diarios que incluyen sangrado
    @SuppressLint("NewApi")
    fun updateOrCreateCycleFromLogs(token: String, email: String, logs: List<DailyLog>) {
        viewModelScope.launch {
            try {
                val bleedingDates = logs
                    .filter { it.hasMenstruation }
                    .mapNotNull { LocalDate.parse(it.date) }
                    .sorted()

                // Agrupa fechas continuas de sangrado
                val grouped = groupContinuousDates(bleedingDates)
                val latestRange = grouped.lastOrNull() ?: return@launch // Si no hay sangrado, termina
                val start = latestRange.first
                val end = latestRange.second
                val duration = ChronoUnit.DAYS.between(start, end).toInt() + 1

                // Busca ciclo menstrual que se solape con el rango detectado
                val overlappingCycle = _cycles.value.find {
                    val cycleStart = LocalDate.parse(it.startDate)
                    val cycleEnd = LocalDate.parse(it.endDate)
                    (start in cycleStart..cycleEnd || end in cycleStart..cycleEnd)
                }

                if (overlappingCycle != null) {
                    val index = _cycles.value.indexOf(overlappingCycle)
                    val cycleStart = LocalDate.parse(overlappingCycle.startDate)
                    val nextCycleStart = if (index + 1 < _cycles.value.size) {
                        LocalDate.parse(_cycles.value[index + 1].startDate)
                    } else {
                        cycleStart.plusDays(28) // Por defecto 28 días si no hay siguiente ciclo
                    }
                    val newCycleLength = ChronoUnit.DAYS.between(cycleStart, nextCycleStart).toInt()

                    if (duration != overlappingCycle.bleedingDuration || newCycleLength != overlappingCycle.cycleLength) {
                        val updatedCycle = overlappingCycle.copy(
                            startDate = start.toString(),
                            endDate = end.toString(),
                            bleedingDuration = duration,
                            cycleLength = newCycleLength
                        )
                        val response = API.retrofitService.updateCycle("Bearer $token", updatedCycle)
                        if (response.isSuccessful) {
                            loadCycles(email)// Recarga la lista tras actualizar
                        }
                    }
                } else {
                    // Si no hay ciclo solapado, crea uno nuevo con datos por defecto
                    val dto = MenstrualCycleDTO(
                        userId = email,
                        startDate = start.toString(),
                        cycleLength = 28,
                        bleedingDuration = duration,
                        averageFlow = MenstrualFlowLevel.MODERATE
                    )
                    createCycle(dto)
                }
            } catch (e: Exception) {
                Log.e("CycleUpdate", "Error al procesar logs para ciclo: ${e.message}")
            }
        }
    }


    fun updateCycle(token: String, cycle: MenstrualCycle, navController: NavController, username: String, email: String){
        try {
            viewModelScope.launch {
                val response = API.retrofitService.updateCycle("Bearer $token",cycle)
                if (response.isSuccessful){
                    navController.navigate(AppScreen.UserScreen.route + "/$username/$email")
                }else{
                    Log.e("ERROR - A update", "${response.code()} - ${response.errorBody()}")
                }
            }
        }catch (e:Exception){
            Log.e("ERROR - UPDATE", "error: ${e.localizedMessage}")
        }
    }
}
