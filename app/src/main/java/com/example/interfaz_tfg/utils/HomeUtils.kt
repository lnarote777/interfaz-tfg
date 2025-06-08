package com.example.interfaz_tfg.utils

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.navigation.NavController
import com.example.interfaz_tfg.api.model.cycle.CyclePhase
import com.example.interfaz_tfg.api.model.cycle.DailyLog
import com.example.interfaz_tfg.api.model.cycle.MenstrualCycle
import com.example.interfaz_tfg.api.model.user.UserDTO
import com.example.interfaz_tfg.compose.home.homeDataClasses.HomeScreenViewModels
import com.example.interfaz_tfg.compose.home.homeDataClasses.MenstruationData
import com.example.interfaz_tfg.compose.home.homeDataClasses.PeriodState
import com.example.interfaz_tfg.navigation.AppScreen
import com.example.interfaz_tfg.viewModel.CalendarSharedViewModel
import com.example.interfaz_tfg.viewModel.CycleViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.temporal.ChronoUnit

fun handleCycleRecalculation(
    email: String?,
    logs: List<DailyLog>,
    currentDate: LocalDate,
    lastRecalculationDate: LocalDate?,
    cycleViewModel: CycleViewModel,
    onRecalculationComplete: () -> Unit
) {
    email ?: return

    val todayLog = logs.find { it.date == currentDate.toString() }
    val shouldRecalculate = (todayLog == null || !todayLog.hasMenstruation) &&
            lastRecalculationDate != currentDate

    if (shouldRecalculate) {
        cycleViewModel.recalculateCycle(email, currentDate)
        cycleViewModel.loadCycles(email)
        onRecalculationComplete()
    }
}

@SuppressLint("NewApi")
fun calculateMenstruationData(cycles: List<MenstrualCycle>): MenstruationData {
    val confirmedPhases = cycles.filter { !it.isPredicted }.flatMap { it.phases }
    val predictedPhases = cycles.filter { it.isPredicted }.flatMap { it.phases }

    val confirmedDates = confirmedPhases
        .filter { it.phase == CyclePhase.MENSTRUATION }
        .mapNotNull { runCatching { LocalDate.parse(it.date) }.getOrNull() }

    val predictedDates = predictedPhases
        .filter { it.phase == CyclePhase.MENSTRUATION }
        .mapNotNull { runCatching { LocalDate.parse(it.date) }.getOrNull() }

    val allDates = (confirmedDates + predictedDates).distinct().sorted()
    val ranges = groupContinuousDates(allDates)

    return MenstruationData(confirmedDates, predictedDates, allDates, ranges)
}

@SuppressLint("NewApi")
fun calculatePeriodState(
    menstruationData: MenstruationData,
    selectedDate: LocalDate,
    isBleeding: Boolean
): PeriodState {

    val currentDate = LocalDate.now()
    val menstruationBlockToday = menstruationData.ranges.find {
        currentDate in it.first..it.second
    }
    val isTodayInMenstruation = menstruationBlockToday != null
    val menstruationStartDate = if (isTodayInMenstruation && isBleeding) {
        menstruationBlockToday?.first
    } else null

    val dayInPeriod = menstruationStartDate?.let {
        ChronoUnit.DAYS.between(it, selectedDate).toInt() + 1
    }

    val nextMenstruationRange = menstruationData.ranges.firstOrNull {
        it.first.isAfter(selectedDate)
    }
    val nextMenstruationDate = nextMenstruationRange?.first
    val daysUntilNextPeriod = nextMenstruationDate?.let {
        maxOf(0, ChronoUnit.DAYS.between(selectedDate, it).toInt())
    } ?: -1

    val periodText = when {
        dayInPeriod != null -> "Día $dayInPeriod"
        daysUntilNextPeriod > 0 -> daysUntilNextPeriod.toString()
        else -> ""
    }

    return PeriodState(dayInPeriod, daysUntilNextPeriod, isTodayInMenstruation, periodText)
}

fun updateCalendarSharedViewModel(
    calendarSharedViewModel: CalendarSharedViewModel,
    cycles: List<MenstrualCycle>
) {
    val confirmedPhases = cycles.filter { !it.isPredicted }.flatMap { it.phases }
    val predictedPhases = cycles.filter { it.isPredicted }.flatMap { it.phases }

    calendarSharedViewModel.confirmedPhases = confirmedPhases
    calendarSharedViewModel.predictedPhases = predictedPhases
}

fun handlePeriodRegistration(
    user: UserDTO?,
    token: String?,
    isBleeding: Boolean,
    logs: List<DailyLog>,
    currentDate: LocalDate,
    viewModels: HomeScreenViewModels,
    navController: NavController,
    scope: CoroutineScope
) {
    val email = user?.email ?: return

    if (!isBleeding) {
        viewModels.dailyLogViewModel.setIsBleeding(true)
        scope.launch {
            with(viewModels.cycleViewModel) {
                updateOrCreateCycleFromLogs(email, logs)
                recalculateCycle(email, currentDate)
                loadCycles(email)
            }
        }
    }

    navController.navigate("${AppScreen.DailyScreen.route}/$email/$token/$isBleeding")
}

@RequiresApi(Build.VERSION_CODES.O)
fun groupContinuousDates(dates: List<LocalDate>): List<Pair<LocalDate, LocalDate>> {
    if (dates.isEmpty()) return emptyList()
    val sortedDates = dates.sorted()
    val result = mutableListOf<Pair<LocalDate, LocalDate>>()

    var start = sortedDates[0]
    var end = start

    for (i in 1 until sortedDates.size) {
        val current = sortedDates[i]
        if (ChronoUnit.DAYS.between(end, current) <= 1) {
            end = current
        } else {
            result.add(start to end)
            start = current
            end = current
        }
    }
    result.add(start to end)
    return result
}