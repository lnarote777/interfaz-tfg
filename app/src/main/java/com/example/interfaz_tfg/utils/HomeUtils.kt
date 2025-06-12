package com.example.interfaz_tfg.utils

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDate
import java.time.temporal.ChronoUnit

/**
 * Agrupa una lista de fechas [`dates`] en rangos continuos de fechas consecutivas.
 *
 * Las fechas consecutivas o con diferencia de un día se agrupan en un par que indica
 * la fecha de inicio y la fecha de fin del rango continuo.
 *
 * Por ejemplo, dadas las fechas:
 *  - 2023-06-01, 2023-06-02, 2023-06-04
 *
 * Esta función devolverá:
 *  - [(2023-06-01, 2023-06-02), (2023-06-04, 2023-06-04)]
 *
 * @param dates Lista de fechas a agrupar. Puede estar desordenada.
 * @return Lista de pares de fechas [`start`, `end`] representando rangos continuos.
 *         Cada par indica el inicio y fin de un rango consecutivo.
 */
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