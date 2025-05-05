package com.example.interfaz_mesames.compose

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalendarField(selectedDate: String, valueChange: (String)->Unit, onclick: () -> Unit){

    TextField(
        value = selectedDate,
        onValueChange =  valueChange ,
        placeholder = { Text("dd/mm/yyyy") },
        trailingIcon = {
            IconButton(onClick = { onclick() }) {
                Icon(Icons.Default.DateRange, contentDescription = "Seleccionar fecha")
            }
        },
        modifier = Modifier
            .padding(horizontal = 20.dp)
            .fillMaxWidth()
            .height(60.dp)
            .clickable { onclick() },
        textStyle = TextStyle(fontSize = 16.sp),
        colors = TextFieldDefaults.textFieldColors(
            containerColor = Color.Transparent, // sin fondo
            focusedIndicatorColor = Color(0xFF6A1B9A), // línea morada intensa
            unfocusedIndicatorColor = Color(0xFF9C27B0), // línea morada suave
            disabledIndicatorColor = Color.Transparent,
            cursorColor = Color(0xFF6A1B9A)
        ),
    )

}