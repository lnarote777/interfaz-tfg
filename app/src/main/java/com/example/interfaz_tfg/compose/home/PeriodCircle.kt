package com.example.interfaz_tfg.compose.home

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.interfaz_tfg.R

@Composable
fun PeriodCircle(
    periodText: String,
    daysUntilNextPeriod: Int,
    isBleeding: Boolean,
    onRegisterPeriod: () -> Unit
) {
    val color = MaterialTheme.colorScheme
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .size(230.dp)
            .clip(CircleShape)
            .background(colorResource(R.color.rosa_55))
            .border(
                width = 10.dp,
                color = colorResource(R.color.bordeMorado),
                shape = CircleShape
            )
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center) {
            Text(if (periodText == daysUntilNextPeriod.toString()) "Periodo en: " else "Periodo:")
            Text(
                periodText,
                fontSize = 60.sp,
                fontWeight = FontWeight.ExtraBold
            )
            Button(
                modifier = Modifier.padding(top = 10.dp)
                    .height(35.dp),
                onClick = onRegisterPeriod,
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorResource(R.color.botones2)
                )
            ) {
                Text("Registrar periodo")
            }
        }
    }
}