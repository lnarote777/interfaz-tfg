package com.example.interfaz_tfg.utils

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDate
import java.time.temporal.ChronoUnit

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