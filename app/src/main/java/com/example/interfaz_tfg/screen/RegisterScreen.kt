package com.example.interfaz_tfg.screen

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.interfaz_tfg.R
import com.example.interfaz_tfg.compose.CalendarField
import com.example.interfaz_tfg.compose.TextFielPassword
import com.example.interfaz_tfg.compose.Textfield
import com.example.interfaz_tfg.compose.calendar.CustomDatePickerDialog
import com.example.interfaz_tfg.navigation.AppScreen
import com.example.interfaz_tfg.viewModel.RegistrerViewModel
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun RegisterScreen(navController: NavController, viewModel: RegistrerViewModel = viewModel()){
    var email by rememberSaveable { mutableStateOf("") }
    var name by rememberSaveable { mutableStateOf("") }
    var username by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    var passVisible by rememberSaveable { mutableStateOf(false) }
    var passwordRepeat by rememberSaveable { mutableStateOf("") }
    var passVisibleRepeat by rememberSaveable { mutableStateOf(false) }
    var birthdate by rememberSaveable { mutableStateOf("") } // para mostrar en el campo
    var birthdateRaw by rememberSaveable { mutableStateOf("") }
    var showDatePicker by rememberSaveable { mutableStateOf(false) }
    val uiState by viewModel.uiState.collectAsState()
    var errorMessage by remember { mutableStateOf("") }
    var error by rememberSaveable { mutableStateOf(false) }

    val color = MaterialTheme.colorScheme

    LaunchedEffect(uiState) {
        if (uiState.isNotEmpty()) {
            errorMessage = uiState
            error = true
            username = ""
            password = ""
            passwordRepeat = ""
            name = ""
            email = ""
            birthdate = ""

        }
    }


    if (showDatePicker) {
        CustomDatePickerDialog(
            onDismiss = { showDatePicker = false },
            onDateSelected = {
                birthdate = it.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
                birthdateRaw = it.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
            }
        )
    }

    Box(){
        if(isSystemInDarkTheme()){
            Image(painter = painterResource(R.drawable.fondo_load_dark), //modo oscuro
                contentDescription = "Fondo",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
        }else{
            Image(painter = painterResource(R.drawable.fondo_registro),
                contentDescription = "Fondo",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
        }


        Column(horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Bottom,
            modifier = Modifier.fillMaxSize()
        ) {
            Box(modifier = Modifier
                .fillMaxWidth(0.9F)
                .padding(bottom = 100.dp)
                .clip(RoundedCornerShape(51.dp))
                .background(colorResource(R.color.tarjetaDatos))
                .height(700.dp)

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
                        )
                        Spacer(Modifier.height(10.dp))
                        if (error){
                            Text(
                                errorMessage,
                                color = Color.Red,
                                fontSize = 20.sp
                            )
                        }
                        Spacer(Modifier.height(10.dp))
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
                            selectedDate = birthdate,
                            onclick = {showDatePicker = true},
                            valueChange = {birthdate = it}
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
                if (username.isBlank() || password.isBlank() || name.isBlank() || birthdate.isBlank() || email.isBlank()) {
                    errorMessage = "Por favor, completa todos los campos"
                    error = true
                } else if (password != passwordRepeat){
                    errorMessage = "Las contraseñas no coinciden"
                    error = true
                } else {

                    viewModel.registerUser(
                        name = name,
                        pass = password,
                        passRepeat = passwordRepeat,
                        username = username,
                        email = email,
                        birthdate = birthdateRaw,
                        navController = navController
                    )
                    errorMessage = ""
                }
            },
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .align(Alignment.BottomCenter)
                .padding(bottom = 30.dp)
                .height(50.dp),
            shape = RoundedCornerShape(10.dp),
            colors = ButtonDefaults.buttonColors(containerColor = color.tertiary)
        ) { Text("Continuar", fontSize = 20.sp) }


    }
}