package com.example.interfaz_tfg.compose.login

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.interfaz_tfg.R
import com.example.interfaz_tfg.compose.login.loginDataClasses.LoginFormData

@Composable
fun LoginActionSection(
    onLogin: () -> Unit
) {
    Button(
        onClick = onLogin,
        modifier = Modifier
            .fillMaxWidth(0.9f)
            .height(50.dp),
        shape = RoundedCornerShape(10.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = colorResource(R.color.botones1)
        )
    ) {
        Text("Iniciar Sesión", fontSize = 20.sp)
    }
    
    Spacer(Modifier.height(20.dp))
}