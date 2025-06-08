package com.example.interfaz_tfg.compose.profile

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.interfaz_tfg.compose.profile.profileDataClasses.UserInfo
import com.example.interfaz_tfg.compose.profile.profileDataClasses.UserScreenState

@Composable
fun DeleteAccountDialog(
    userInfo: UserInfo,
    screenState: UserScreenState,
    onStateChange: (UserScreenState) -> Unit,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("¿Estás seguro?") },
        text = {
            Column {
                Text("Para eliminar tu cuenta, introduce tu correo electrónico:")
                Spacer(Modifier.height(8.dp))
                
                OutlinedTextField(
                    value = screenState.confirmationEmail,
                    onValueChange = { email ->
                        onStateChange(
                            screenState.copy(
                                confirmationEmail = email,
                                emailError = false
                            )
                        )
                    },
                    label = { Text("Correo electrónico") },
                    isError = screenState.emailError,
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
                
                if (screenState.emailError) {
                    Text(
                        text = "El correo no coincide.",
                        color = Color.Red,
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }
        },
        confirmButton = {
            Button(onClick = onConfirm) {
                Text("Eliminar")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancelar")
            }
        }
    )
}