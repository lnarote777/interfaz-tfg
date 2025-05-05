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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.interfaz_mesames.R
import com.example.interfaz_mesames.compose.TextFielPassword
import com.example.interfaz_mesames.compose.Textfield
import com.example.interfaz_mesames.navigation.AppScreen


@Composable
fun LoginScreen(navController: NavController){
    var user by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    var passVisible by rememberSaveable { mutableStateOf(false) }
    //val uiState by viewModel.uiState.collectAsState()
    var errorMessage by remember { mutableStateOf("") }
    var error by rememberSaveable { mutableStateOf(false) }

    Box(){
        Image(painter = painterResource(R.drawable.fondo_login),
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
                .clip(RoundedCornerShape(topStart = 51.dp, topEnd = 51.dp))
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
                        Text("Inicia Sesión",
                            textAlign = TextAlign.Center,
                            fontSize = 40.sp,
                            modifier =  Modifier
                                .fillMaxWidth()
                                .padding(30.dp)
                            // .clickable { navController.navigate(route = AppScreen.RegisterScreen.route) }
                        )

                        Textfield(user, "Usuario") { user = it }
                        Spacer(Modifier.height(30.dp))

                        TextFielPassword(password, passVisible, "Contraseña", { password = it }) {
                            passVisible = !passVisible
                        }
                        Spacer(Modifier.height(30.dp))
                        if (error){
                            Text(
                                errorMessage,
                                color = Color.Red,
                                fontSize = 20.sp
                            )
                        }

                        Spacer(Modifier.height(60.dp))

                        Button(
                            onClick = {
                                if (user.isBlank() || password.isBlank()) {
                                    errorMessage = "Por favor, completa ambos campos"
                                    error = true
                                } else {
                                    // Si todo está bien, intentamos hacer login
                                    // viewModel.login(user, password, navController)
                                    errorMessage = ""
                                }
                            },
                            modifier = Modifier.fillMaxWidth(0.9f)
                                .height(50.dp),
                            shape = RoundedCornerShape(10.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = colorResource(R.color.botones1))
                        ) { Text("Iniciar Sesión", fontSize = 20.sp) }

                        Spacer(Modifier.height(20.dp))

                        Text("¿Olvidaste la contraseña?",
                            textAlign = TextAlign.Center,
                            modifier =  Modifier
                                .fillMaxWidth()
                                //.clickable { navController.navigate(route = AppScreen.RegistroScreen.route) }
                        )
                    }

                    Text("¿Aun no estás registrada? Regístrate.",
                        textAlign = TextAlign.Center,
                        modifier =  Modifier
                            .fillMaxWidth()
                            .padding(bottom = 30.dp)
                            .clickable { navController.navigate(route = AppScreen.RegistroScreen.route) }
                    )

                }

            }
        }
    }
}