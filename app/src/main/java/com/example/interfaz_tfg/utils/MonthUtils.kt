package com.example.interfaz_tfg.utils

import com.example.interfaz_tfg.compose.calendar.YearMonth
import java.time.Month


fun generateMonthsBetweenYears(startYear: Int, endYear: Int): List<YearMonth> {
    val months = mutableListOf<YearMonth>()
    for (year in startYear..endYear) {
        for (month in Month.entries) {
            months.add(YearMonth(year, month))
        }
    }
    return months
}
