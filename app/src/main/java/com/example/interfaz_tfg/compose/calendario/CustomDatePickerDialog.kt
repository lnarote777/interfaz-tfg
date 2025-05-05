package com.example.interfaz_mesames.compose.calendario

import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import com.example.interfaz_mesames.R
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class)
@Composable
fun CustomDatePickerDialog(
    onDismiss: () -> Unit,
    onDateSelected: (LocalDate) -> Unit
) {
        val datePickerState = rememberDatePickerState()
        DatePickerDialog(
            onDismissRequest = onDismiss,
            confirmButton = {
                TextButton(
                    onClick = {
                        datePickerState.selectedDateMillis?.let { millis ->
                            val selectedDate = Instant.ofEpochMilli(millis)
                                .atZone(ZoneId.systemDefault())
                                .toLocalDate()
                            onDateSelected(selectedDate)
                        }
                        onDismiss()
                    }
                ) {
                    Text("Aceptar", color = colorResource(R.color.botones1))
                }
            },
            dismissButton = {
                TextButton(onClick = onDismiss) {
                    Text("Cancelar", color = Color.Gray)
                }
            }
        ) {
            DatePicker(state = datePickerState,
                colors = DatePickerDefaults.colors(
                    containerColor = Color(0xFFEFB9E7), // Fondo rosa claro
                    titleContentColor = colorResource(R.color.botones1), // Morado oscuro
                    headlineContentColor = colorResource(R.color.botones1),
                    weekdayContentColor = Color(0xFF8E24AA), // Letras de días
                    subheadContentColor = Color.Gray,
                    selectedDayContainerColor = Color(0xFFBA68C8), // Día seleccionado
                    selectedDayContentColor = Color.White,
                    todayContentColor = Color(0xFF6A1B9A),
                    disabledDayContentColor = Color.LightGray
                ))
        }

}