package com.example.interfaz_mesames.compose.configuraciones

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun SettingItem(
    title: String,
    value: String? = null,
    isClickable: Boolean = false,
    icon: ImageVector? = null,
    route: String =  "",
    navController: NavController? = null
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(enabled = isClickable) { navController?.navigate(route = route) }
            .padding(vertical = 10.dp, horizontal = 24.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        icon?.let {
            Icon(
                imageVector = it,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(end = 16.dp)
            )
        }

        Text(
            text = title,
            style = MaterialTheme.typography.bodyLarge,
            color = if (isClickable) MaterialTheme.colorScheme.primary else Color.Black,
            modifier = Modifier.weight(1f)
        )

        if (value != null) {
            Text(text = value, color = Color.Gray)
        } else if (isClickable) {
            Icon(Icons.Default.ArrowForward, contentDescription = "Go to", tint = MaterialTheme.colorScheme.primary)
        }
    }
}