package com.example.interfaz_mesames.compose.premium

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.interfaz_mesames.R

@Composable
fun CaracteristicasBox() {
    val features = listOf(
        Triple("Control ciclo", true, true),
        Triple("Recordatorios", true, true),
        Triple("Modo oscuro", true, true),
        Triple("Registro diario", true, true),
        Triple("Copia de seguridad", false, true),
        Triple("Cambiar tema", false, true),
        Triple("Exportar datos", false, true),
        Triple("Resumen mensual", false, true),
        Triple("Informes", false, true),
        Triple("Análisis de las fases del ciclo", false, true)
    )

    Column(
        modifier = Modifier
            .fillMaxWidth(0.9f)
            .clip(RoundedCornerShape(16.dp))
            .background(colorResource(R.color.VipBox))
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Características",
                modifier = Modifier.weight(2f),
                fontWeight = FontWeight.ExtraBold,
                fontSize = 16.sp
            )
            Text(
                text = "Gratis",
                modifier = Modifier.weight(1f),
                fontWeight = FontWeight.ExtraBold,
                fontSize = 16.sp,
                textAlign = TextAlign.Center
            )
            Text(
                text = "Premium",
                modifier = Modifier.weight(1f),
                fontWeight = FontWeight.ExtraBold,
                fontSize = 16.sp,
                textAlign = TextAlign.Center,
                color = colorResource(R.color.botones1)
            )
        }

        Divider(
            color = Color.Black,
            thickness = 1.dp,
            modifier = Modifier.padding(vertical = 8.dp)
        )

        features.forEach { (feature, free, premium) ->
            CaracteristicasRow(feature = feature, free = free, premium = premium)

            if (feature != features.last().first) {
                Divider(
                    color = Color.LightGray.copy(alpha = 0.3f),
                    thickness = 1.dp,
                    modifier = Modifier.padding(vertical = 4.dp)
                )
            }
        }
    }
}


@Composable
fun CaracteristicasRow(feature: String, free: Boolean, premium: Boolean) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = feature,
            modifier = Modifier.weight(2f),
            fontSize = 14.sp,
            fontWeight = FontWeight.SemiBold
        )

        Box(
            modifier = Modifier.weight(1f),
            contentAlignment = Alignment.Center
        ) {
            if (free) {
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = "Disponible",
                    tint = colorResource(R.color.botones1)
                )
            } else {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "No disponible",
                    tint = Color.Red
                )
            }
        }

        Box(
            modifier = Modifier.weight(1f),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.Check,
                contentDescription = "Disponible",
                tint = colorResource(R.color.botones1)
            )
        }
    }
}
