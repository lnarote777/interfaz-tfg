package com.example.interfaz_tfg

import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

class PaymentResultActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Obtener datos del deep link
        val data: Uri? = intent?.data

        // Estado para el mensaje a mostrar
        val message = when (data?.host) {
            "payment-success" -> {
                val sessionId = data.getQueryParameter("session_id")
                "Pago exitoso!\nSession ID: $sessionId"
            }
            "payment-cancel" -> {
                "Pago cancelado."
            }
            else -> {
                "Resultado desconocido."
            }
        }

        setContent {
            MaterialTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        Text(
                            text = message,
                            style = MaterialTheme.typography.headlineMedium,
                            modifier = Modifier.padding(16.dp)
                        )
                    }
                }
            }
        }
    }
}
