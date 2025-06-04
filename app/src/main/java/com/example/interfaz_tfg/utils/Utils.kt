package com.example.interfaz_tfg.utils

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDate


@RequiresApi(Build.VERSION_CODES.O)
fun groupContinuousDates(dates: List<LocalDate>): List<Pair<LocalDate, LocalDate>> {
    if (dates.isEmpty()) return emptyList()
    val sortedDates = dates.sorted()
    val result = mutableListOf<Pair<LocalDate, LocalDate>>()

    var start = sortedDates[0]
    var end = start

    for (date in sortedDates.drop(1)) {
        if (date == end.plusDays(1)) {
            end = date
        } else {
            result.add(start to end)
            start = date
            end = date
        }
    }
    result.add(start to end)
    return result
}
