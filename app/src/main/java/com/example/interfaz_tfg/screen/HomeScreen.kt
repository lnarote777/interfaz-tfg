package com.example.interfaz_tfg.screen

import android.net.Uri
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.interfaz_tfg.R
import com.example.interfaz_tfg.compose.Footer
import com.example.interfaz_tfg.compose.calendario.Month
import com.example.interfaz_tfg.navigation.AppScreen
import com.example.interfaz_tfg.viewModel.UserViewModel
import java.time.LocalDate


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun HomeScreen(
    navController: NavController,
    username: String?,
    userRol: String?,
    token: String?,
    periodDays: Int?,
    viewModel: UserViewModel = viewModel()
){
    val diasPeriodo = periodDays //cambiar llamada a la api
    val scrollState = rememberScrollState()
    val color = MaterialTheme.colorScheme
    val user = viewModel.user.collectAsState()

    LaunchedEffect(Unit) {
        scrollState.scrollTo(0)
        if (username != null) {
            viewModel.getUserByUsername(username)
        }
    }
    LaunchedEffect(scrollState.value) {
        if (scrollState.value == scrollState.maxValue) {
            navController.navigate(route = AppScreen.DailyScreen.route)
        }
    }

    Scaffold {innerpadding ->
        Box(modifier = Modifier.padding(innerpadding)) {
            if(isSystemInDarkTheme()){
                Box(
                    Modifier.fillMaxSize()
                        .background(color.background)
                )
            }else{
                Image(
                    painter = painterResource(R.drawable.tema_claro),
                    contentDescription = "Fondo",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .padding(top = 15.dp)
                        .fillMaxSize()
                )
            }

            Column(modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 80.dp)
                .verticalScroll(scrollState)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 25.dp, vertical = 30.dp)
                ) {
                    if (userRol != "PREMIUM"){
                        IconButton(
                            onClick = { navController.navigate(route = AppScreen.PremiumScreen.route) },
                            modifier = Modifier.size(50.dp)
                        ) {
                            Box(modifier = Modifier.fillMaxSize()) {
                                Image(
                                    painter = painterResource(R.drawable.corona_home),
                                    contentDescription = "VIP",
                                    modifier = Modifier.fillMaxSize()
                                )
                            }
                        }
                    }

                    Spacer(Modifier.weight(1f))

                    val encodedUsername = Uri.encode(user.value?.username)
                    val encodedEmail = Uri.encode(user.value?.email)
                    IconButton(
                        onClick = {navController.navigate(route = AppScreen.UserScreen.route + "/$encodedUsername/$encodedEmail")}
                    ) {
                        Image(painter = painterResource(R.drawable.user_icon),
                            contentDescription = "Profile",
                            modifier = Modifier.size(35.dp),
                            colorFilter = ColorFilter.tint(color.onBackground)
                        )
                    }
                }


                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxSize()
                ) {
                    Box(contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .size(230.dp)
                            .clip(CircleShape)
                            .background(colorResource(R.color.rosa_55))
                            .border(
                                width = 10.dp,
                                color = colorResource(R.color.bordeMorado),
                                shape = CircleShape
                            )
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center) {
                            Text("Periodo en:")
                            Text("$diasPeriodo",
                                fontSize = 60.sp,
                                fontWeight = FontWeight.ExtraBold) // cambiar segun lo de la api
                            Button(
                                modifier = Modifier.padding(top = 10.dp)
                                    .height(35.dp),
                                onClick = {},
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = colorResource(R.color.botones2)
                                )
                            ) {
                                Text("Registrar periodo")
                            }
                        }
                    }
                    Spacer(Modifier.height(60.dp))
                    val currentDate = LocalDate.now()
                    var selectedDate by remember { mutableStateOf(LocalDate.now()) }
                    Month(
                        year = currentDate.year,
                        month = currentDate.month,
                        selectedDate = selectedDate,
                        currentDate = currentDate
                    ) { date -> selectedDate = date }
                    Spacer(Modifier.height(100.dp))
                }
            }

            Footer(navController = navController,
                modifier = Modifier
                    .align(alignment = Alignment.BottomCenter)
                    .padding(vertical = 18.dp) )
        }

    }


}