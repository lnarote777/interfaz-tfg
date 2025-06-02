package com.example.interfaz_tfg.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.Placeholder
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.interfaz_tfg.api.model.cycle.LogState
import com.example.interfaz_tfg.viewModel.DailyLogViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TextCard(
    title: String,
    placeholder: String,
    color: Color,
    value: String,
    onValueChange: (String) -> Unit
){

    val colorScheme = MaterialTheme.colorScheme
    Column(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .border(4.dp, color, RoundedCornerShape(25.dp))
            .background(colorScheme.surface, RoundedCornerShape(25.dp))
            .padding(16.dp)
    ) {
        Text(text = title, style = MaterialTheme.typography.titleMedium,)
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            value = value,
            onValueChange = { onValueChange(it)
            },
            placeholder = { Text(placeholder) },
            modifier = Modifier
                .fillMaxWidth(),
            colors = TextFieldDefaults.textFieldColors(
                containerColor = Color.Transparent,
                focusedIndicatorColor = Color.Black,
                unfocusedIndicatorColor = Color.Black,
                disabledIndicatorColor = Color.Black,
                cursorColor = Color.Black,
                focusedTextColor = Color.Black,
                unfocusedLabelColor = Color.Black,
                unfocusedTextColor = Color.Black
            ),
            singleLine = true,
            textStyle = MaterialTheme.typography.bodyLarge
        )
        Spacer(Modifier.height(16.dp))
    }

}