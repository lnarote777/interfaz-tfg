package com.example.interfaz_mesames.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.interfaz_mesames.R
import com.example.interfaz_mesames.compose.CalendarField
import com.example.interfaz_mesames.compose.TextFielPassword
import com.example.interfaz_mesames.compose.Textfield
import com.example.interfaz_mesames.compose.calendario.CustomDatePickerDialog
import com.example.interfaz_mesames.navigation.AppScreen
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(navController: NavController){
    var email by rememberSaveable { mutableStateOf("") }
    var name by rememberSaveable { mutableStateOf("") }
    var username by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    var passVisible by rememberSaveable { mutableStateOf(false) }
    var passwordRepeat by rememberSaveable { mutableStateOf("") }
    var passVisibleRepeat by rememberSaveable { mutableStateOf(false) }
    var selectedDate by rememberSaveable { mutableStateOf("") }
    var showDatePicker by rememberSaveable { mutableStateOf(false) }
    //val uiState by viewModel.uiState.collectAsState()
    var errorMessage by remember { mutableStateOf("") }

    if (showDatePicker) {
        CustomDatePickerDialog(
            onDismiss = { showDatePicker = false },
            onDateSelected = {
                selectedDate = it.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
            }
        )
    }

    Box(){
        Image(painter = painterResource(R.drawable.fondo_registro),
            contentDescription = "Fondo",
            contentScale = ContentScale.Crop,
            modifier = Modifier.padding(top = 35.dp)
                .fillMaxSize()
        )

        Column(horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Bottom,
            modifier = Modifier.fillMaxSize()
        ) {
            Box(modifier = Modifier
                .fillMaxWidth(0.9F)
                .padding(bottom = 100.dp)
                .clip(RoundedCornerShape(51.dp))
                .background(colorResource(R.color.tarjetaDatos))
                .height(600.dp)

            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxSize()
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text("Resgístrate",
                            textAlign = TextAlign.Center,
                            fontFamily = FontFamily(Font(R.font.inter, FontWeight.ExtraBold)),
                            fontSize = 28.sp,
                            modifier =  Modifier
                                .fillMaxWidth()
                                .padding(20.dp)
                            // .clickable { navController.navigate(route = AppScreen.RegisterScreen.route) }
                        )

                        Textfield(name, "Nombre") { name = it }
                        Spacer(Modifier.height(10.dp))

                        Textfield(username, "Nombre de usuario") { username = it }
                        Spacer(Modifier.height(10.dp))

                        Textfield(email, "Email") { email = it }
                        Spacer(Modifier.height(10.dp))

                        TextFielPassword(password, passVisible, "Contraseña", { password = it }) {
                            passVisible = !passVisible
                        }
                        Spacer(Modifier.height(10.dp))

                        TextFielPassword(passwordRepeat, passVisibleRepeat, "Repetir contraseña", { passwordRepeat = it }) {
                            passVisibleRepeat = !passVisibleRepeat
                        }
                        Spacer(Modifier.height(10.dp))

                        CalendarField(
                            selectedDate = selectedDate,
                            onclick = {showDatePicker = true},
                            valueChange = {selectedDate = it}
                        )


                    }

                    Text("¿Ya tienes una cuenta? Inicia sesión",
                        textAlign = TextAlign.Center,
                        modifier =  Modifier
                            .fillMaxWidth()
                            .padding(bottom = 20.dp)
                            .clickable { navController.navigate(route = AppScreen.LoginScreen.route) }
                    )

                }

            }

        }

        Button(
            onClick = {
                if (username.isBlank() || password.isBlank()) {
                    errorMessage = "Por favor, completa ambos campos"
                } else {
                    // Si todo está bien, intentamos hacer login
                    // viewModel.login(user, password, navController)
                    errorMessage = ""
                }
            },
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .align(Alignment.BottomCenter)
                .padding(bottom = 30.dp)
                .height(50.dp),
            shape = RoundedCornerShape(10.dp),
            colors = ButtonDefaults.buttonColors(containerColor = colorResource(R.color.botones1))
        ) { Text("Continuar", fontSize = 20.sp) }


    }
}