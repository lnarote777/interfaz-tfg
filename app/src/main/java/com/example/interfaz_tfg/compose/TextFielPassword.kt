package com.example.interfaz_mesames.compose

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.interfaz_mesames.R

/**
 * Composable que muestra un campo de texto para ingresar una contraseña, con la opción de alternar
 * la visibilidad de la contraseña (mostrar u ocultar).
 *
 * @param password El valor actual de la contraseña ingresada.
 * @param passVisible Booleano que indica si la contraseña es visible o está oculta.
 * @param label La etiqueta que se muestra encima del campo de texto, indicando su propósito.
 * @param valueChange Función que se ejecuta cuando el valor del campo de texto cambia.
 * Recibe el nuevo valor de la contraseña como parámetro, permitiendo que se actualice el estado.
 * @param onclick Función que se ejecuta cuando se hace clic en el ícono de visibilidad de la contraseña.
 * Esto permite alternar entre mostrar u ocultar la contraseña.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TextFielPassword(password: String, passVisible: Boolean, placeholder: String, valueChange: (String) -> Unit, onclick: () -> Unit){

    TextField(
        value = password,
        onValueChange = valueChange,
        modifier = Modifier
            .padding(horizontal = 20.dp)
            .fillMaxWidth()
            .height(60.dp),
        visualTransformation = if (passVisible) VisualTransformation.None else PasswordVisualTransformation(),
        colors = TextFieldDefaults.textFieldColors(
            containerColor = Color.Transparent, // sin fondo
            focusedIndicatorColor = Color(0xFF6A1B9A), // línea morada intensa
            unfocusedIndicatorColor = Color(0xFF9C27B0), // línea morada suave
            disabledIndicatorColor = Color.Transparent,
            cursorColor = Color(0xFF6A1B9A)
        ),
        singleLine = true,
        trailingIcon ={
            val image = if (passVisible) painterResource(R.drawable.visibilityoff) else painterResource(
                R.drawable.visibility)
            val description = if (passVisible) "Ocultar contraseña" else "Mostrar contraseña"
            IconButton(onClick = onclick) {
                Icon(
                    painter = image,
                    contentDescription = description,
                    tint = Color.Black,
                    modifier = Modifier.size(20.dp)
                )
            }
        },
        label = { Text(placeholder) }
    )
}