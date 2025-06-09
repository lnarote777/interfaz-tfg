package com.example.interfaz_tfg.utils

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.interfaz_tfg.compose.calendar.YearMonth
import java.time.LocalDate
import java.time.Month


@RequiresApi(Build.VERSION_CODES.O)
fun generateCalendarMonths(currentDate: LocalDate): List<YearMonth> {
    val currentYear = currentDate.year
    val minYear = 2010
    val maxYear = currentYear + 2

    return generateMonthsBetweenYears(minYear, maxYear)
}

fun generateMonthsBetweenYears(startYear: Int, endYear: Int): List<YearMonth> {
    val months = mutableListOf<YearMonth>()
    for (year in startYear..endYear) {
        for (month in Month.entries) {
            months.add(YearMonth(year, month))
        }
    }
    return months
}

@RequiresApi(Build.VERSION_CODES.O)
suspend fun scrollToCurrentMonth(
    months: List<YearMonth>,
    currentDate: LocalDate,
    listState: androidx.compose.foundation.lazy.LazyListState
) {
    val currentMonthIndex = months.indexOfFirst {
        it.year == currentDate.year && it.month == currentDate.month
    }

    if (currentMonthIndex != -1) {
        listState.animateScrollToItem(currentMonthIndex)
    }
}