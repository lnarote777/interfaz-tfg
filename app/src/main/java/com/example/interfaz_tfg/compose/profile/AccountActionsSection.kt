package com.example.interfaz_tfg.compose.profile

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.interfaz_tfg.compose.configuraciones.SettingCard


@Composable
fun AccountActionsSection(
    onLogout: () -> Unit,
    onDeleteAccount: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth()
    ) {
        SettingCard(height = 90.dp) {
            Text(
                text = "Cerrar sesión",
                fontSize = 20.sp,
                color = Color.Black,
                modifier = Modifier.clickable { onLogout() }
            )
            
            Spacer(Modifier.height(10.dp))
            
            Text(
                text = "Eliminar cuenta",
                fontSize = 20.sp,
                color = Color.Red,
                modifier = Modifier.clickable { onDeleteAccount() }
            )
        }
    }
}