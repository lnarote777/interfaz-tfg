package com.example.interfaz_tfg.compose.calendar

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.interfaz_tfg.api.model.cycle.CyclePhaseDay
import com.example.interfaz_tfg.api.model.cycle.DailyLog
import java.time.LocalDate

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CalendarMonthsList(
    months: List<YearMonth>,
    listState: LazyListState,
    selectedDate: LocalDate,
    currentDate: LocalDate,
    logs: List<DailyLog>,
    confirmedPhases: List<CyclePhaseDay>,
    predictedPhases: List<CyclePhaseDay>,
    onDateSelected: (LocalDate) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier ,
        verticalArrangement = Arrangement.spacedBy(24.dp),
        state = listState
    ) {
        items(
            items = months,
        ) { monthYear ->
            Month(
                year = monthYear.year,
                month = monthYear.month,
                selectedDate = selectedDate,
                currentDate = currentDate,
                logs = logs,
                showNavigationArrows = false,
                confirmedPhases = confirmedPhases,
                predictedPhases = predictedPhases,
                onDateSelected = onDateSelected
            )
        }
    }
}