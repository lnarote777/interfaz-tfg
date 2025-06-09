package com.example.interfaz_tfg.screen

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.interfaz_tfg.compose.calendar.CalendarHeader
import com.example.interfaz_tfg.api.model.cycle.DailyLog
import com.example.interfaz_tfg.compose.calendar.CalendarMonthsList
import com.example.interfaz_tfg.compose.calendar.Month
import com.example.interfaz_tfg.utils.generateCalendarMonths
import com.example.interfaz_tfg.utils.scrollToCurrentMonth
import com.example.interfaz_tfg.viewModel.CalendarSharedViewModel
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.Month

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CalendarScreen(
    navController: NavController,
    logs: List<DailyLog>,
    calendarSharedViewModel: CalendarSharedViewModel
){
    val currentDate = LocalDate.now()

    var selectedDate by remember { mutableStateOf(LocalDate.now()) }
    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()

    val months = remember { generateCalendarMonths(currentDate) }

    val confirmedPhases = calendarSharedViewModel.confirmedPhases
    val predictedPhases = calendarSharedViewModel.predictedPhases

    LaunchedEffect(Unit) {
        scrollToCurrentMonth(months, currentDate, listState)
    }


    Scaffold { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .background(color = MaterialTheme.colorScheme.background)
        ) {
            CalendarHeader(
                navController = navController,
                title = "Calendario",
                onTodayClick = {
                    selectedDate = currentDate
                    coroutineScope.launch {
                        scrollToCurrentMonth(months, currentDate, listState)
                    }
                }
            )

            CalendarMonthsList(
                months = months,
                listState = listState,
                selectedDate = selectedDate,
                currentDate = currentDate,
                logs = logs,
                confirmedPhases = confirmedPhases,
                predictedPhases = predictedPhases,
                onDateSelected = { date -> selectedDate = date },
                modifier = Modifier.weight(1f)
            )
        }
    }
}