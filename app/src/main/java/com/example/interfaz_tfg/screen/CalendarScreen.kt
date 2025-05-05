package com.example.interfaz_mesames.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.interfaz_mesames.R
import com.example.interfaz_mesames.compose.calendario.CalendarHeader
import com.example.interfaz_mesames.compose.calendario.Month
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.Month

@Composable
fun CalendarScreen(navController: NavController){
    val currentDate = LocalDate.now()
    val currentYear = currentDate.year
    val minYear = 2010
    val maxYear = currentYear + 2
    val months = remember {
        generateMonthsBetweenYears(minYear, maxYear).reversed()
    }
    var selectedDate by remember { mutableStateOf(LocalDate.now()) }

    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        val currentMonthIndex = months.indexOfFirst {
            it.year == currentDate.year && it.month == currentDate.month
        }
        if (currentMonthIndex != -1) {
            listState.scrollToItem(currentMonthIndex)
        }
    }

    Column(
        modifier = Modifier.fillMaxSize()
            .padding(top = 30.dp, bottom = 18.dp)
            .background(color = colorResource(R.color.fondo))
    ) {
        CalendarHeader(navController, "Calendario",
            onTodayClick = {
                selectedDate = currentDate
                val currentMonthIndex = months.indexOfFirst {
                    it.year == currentDate.year && it.month == currentDate.month
                }
                if (currentMonthIndex != -1) {
                    coroutineScope.launch {
                        listState.animateScrollToItem(currentMonthIndex)
                    }
                }
            }
        )

        LazyColumn(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(24.dp),
            reverseLayout = true, // ← Esto invierte la dirección del scroll
            state = listState
        ) {
            items(months) { monthYear ->
                Month(
                    year = monthYear.year,
                    month = monthYear.month,
                    selectedDate = selectedDate,
                    currentDate = currentDate,
                    onDateSelected = { date ->
                        selectedDate = date
                    }
                )
            }
        }

    }
}

data class YearMonth(val year: Int, val month: Month)

fun generateMonthsBetweenYears(startYear: Int, endYear: Int): List<YearMonth> {
    val months = mutableListOf<YearMonth>()
    for (year in startYear..endYear) {
        for (month in Month.entries) {
            months.add(YearMonth(year, month))
        }
    }
    return months
}