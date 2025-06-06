package com.example.interfaz_tfg.viewModel

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.interfaz_tfg.api.API
import com.example.interfaz_tfg.api.model.cycle.DailyLog
import com.example.interfaz_tfg.api.model.cycle.DailyLogDTO
import com.example.interfaz_tfg.api.model.cycle.LogState
import com.example.interfaz_tfg.api.model.cycle.MonthlyStats
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate

class DailyLogViewModel : ViewModel(){
    private val _logState = MutableStateFlow(LogState())
    val logState: StateFlow<LogState> = _logState

    @RequiresApi(Build.VERSION_CODES.O)
    private val _selectedDate = MutableStateFlow(LocalDate.now())
    @RequiresApi(Build.VERSION_CODES.O)
    val selectedDate: StateFlow<LocalDate> = _selectedDate

    private val _logs = MutableStateFlow<List<DailyLog>>(emptyList())
    val logs: StateFlow<List<DailyLog>> = _logs

    private val _isBleeding = MutableStateFlow(false)
    val isBleeding: StateFlow<Boolean> = _isBleeding

    private val _isEditing = MutableStateFlow(false)
    val isEditing: StateFlow<Boolean> = _isEditing

    fun setIsBleeding(value: Boolean) {
        _isBleeding.value = value
    }


    @RequiresApi(Build.VERSION_CODES.O)
    fun setSelectedDate(date: LocalDate, email: String, token: String) {
        _selectedDate.value = date
        loadLogForDate(email, date)
    }

    fun setNotes(notes: String) {
        _logState.value = _logState.value.copy(notes = notes)
    }

    fun setWeight(weight: Double?) {
        _logState.value = _logState.value.copy(weight = weight)
    }

    fun updateBleedingStatusForToday(logs: List<DailyLog>, today: LocalDate) {
        val todayLog = logs.find { it.date == today.toString() }
        _isBleeding.value = todayLog?.hasMenstruation == true
    }

    fun updateCategory(category: String, selectedEmojis: MutableList<String?>) {
        _logState.value = when (category) {
            "Estado de ánimo" -> _logState.value.copy(mood = selectedEmojis)
            "Síntomas" -> _logState.value.copy(symptoms = selectedEmojis)
            "Flujo Menstrual" -> _logState.value.copy(menstrualFlow = selectedEmojis.firstOrNull() ?: "")
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
                    onError("Error: ${response.code()} - message: ${response.message()}")
                    Log.d("ERROR", "Error: ${response.code()} - message: ${response.message()} - ${response.errorBody()}")
                }
            } catch (e: Exception) {
                onError("Error de red: ${e.localizedMessage}")
                Log.d("ERROR", "createLog: ${e.localizedMessage}")
            }
        }
    }

    fun loadLogs(email: String) {
        viewModelScope.launch {
            try {
                val cleanedEmail = email.trim('"')
                val result = API.retrofitService.getLogsByUser(cleanedEmail)
                if (result.isSuccessful) {
                    _logs.value = result.body() ?: emptyList()

                    if (_logs.value.isEmpty()){
                        Log.e("DAILYLOG - A", "Error al cargar logs: ${result.code()}")
                    }
                }else{
                    Log.e("DAILYLOG - B", "Error al cargar logs: ${result.code()}")

                }
            } catch (e: Exception) {
                Log.e("API", "Error al cargar logs: ${e.message}")
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun loadLogForDate(email: String, date: LocalDate) {
        viewModelScope.launch {
            try {
                val cleanedEmail = email.trim('"')
                val result = API.retrofitService.getLogByDate(cleanedEmail, date.toString())
                if (result.isSuccessful) {
                    result.body()?.let { log ->
                        _logState.value = LogState(
                            mood = log.mood,
                            symptoms = log.symptoms,
                            menstrualFlow = log.menstrualFlow?.name?.lowercase() ?: "",
                            sexualActivity = log.sexualActivity,
                            vaginalDischarge = log.vaginalDischarge,
                            physicalActivity = log.physicalActivity,
                            pillsTaken = log.pillsTaken,
                            waterIntake = log.waterIntake,
                            weight = log.weight,
                            notes = log.notes
                        )
                        _isEditing.value = true
                    }?: run {
                        _logState.value = LogState()
                        _isEditing.value = false
                    }
                } else {
                    _logState.value = LogState()
                    _isEditing.value = false
                }

            } catch (e: Exception) {
                Log.e("API", "Error al cargar log del día: ${e.message}")
            }
        }
    }

    fun updateLog(email: String, date: LocalDate, dto: DailyLogDTO, onSuccess: () -> Unit, onError: (String) -> Unit) {
        viewModelScope.launch {
            try {
                val cleanedEmail = email.trim('"')
                val result = API.retrofitService.updateLog(cleanedEmail, date.toString(), dto)
                if (result.isSuccessful) {
                    onSuccess()
                } else {
                    onError("Error actualizando log: ${result.code()}")
                    Log.e("DAILY-LOG UPDATE", "Error actualizando log: ${result.code()} - body: ${result.body()}")

                }
            } catch (e: Exception) {
                onError("Error de red: ${e.localizedMessage}")
                Log.e("DAILY-LOG UPDATE", "Error de red: ${e.localizedMessage}")

            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getMonthlyStats(month: Int, year: Int): MonthlyStats {
        val filteredLogs = logs.value.filter { log ->
            val logDate = LocalDate.parse(log.date)
            logDate.monthValue == month && logDate.year == year
        }

        val moodCounts = mutableMapOf<String, Int>()
        val symptomCounts = mutableMapOf<String, Int>()
        var exerciseDays = 0
        var totalWater = 0f
        var waterEntries = 0
        var weightRecords = 0

        filteredLogs.forEach { log ->
            log.mood.forEach { mood ->
                mood?.let {
                    moodCounts[it] = moodCounts.getOrDefault(it, 0) + 1
                }
            }
            log.symptoms.forEach { symptom ->
                symptom?.let {
                    symptomCounts[it] = symptomCounts.getOrDefault(it, 0) + 1
                }
            }
            if (log.physicalActivity.isNotEmpty()) {
                exerciseDays++
            }
            log.waterIntake?.let {
                totalWater += it
                waterEntries++
            }
            if (log.weight != null) {
                weightRecords++
            }
        }

        val averageWater = if (waterEntries > 0) totalWater / waterEntries else 0f

        return MonthlyStats(
            moodCounts = moodCounts,
            symptomCounts = symptomCounts,
            exerciseDays = exerciseDays,
            averageWater = averageWater,
            weightRecords = weightRecords
        )
    }
}
