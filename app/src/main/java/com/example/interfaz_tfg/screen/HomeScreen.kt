package com.example.interfaz_tfg.screen

import java.time.temporal.ChronoUnit
import android.annotation.SuppressLint
import android.net.Uri
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.interfaz_tfg.R
import com.example.interfaz_tfg.compose.Footer
import com.example.interfaz_tfg.compose.calendario.Month
import com.example.interfaz_tfg.navigation.AppScreen
import com.example.interfaz_tfg.viewModel.CycleViewModel
import com.example.interfaz_tfg.viewModel.DailyLogViewModel
import com.example.interfaz_tfg.viewModel.UserViewModel
import java.time.LocalDate
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.rememberCoroutineScope
import com.example.interfaz_tfg.api.model.cycle.CyclePhase
import kotlinx.coroutines.launch


@SuppressLint("NewApi")
@Composable
fun HomeScreen(
    navController: NavController,
    username: String?,
    userRol: String?,
    token: String?
){
    val viewModel: UserViewModel = viewModel()
    val scrollState = rememberScrollState()
    val color = MaterialTheme.colorScheme
    val user by viewModel.user.collectAsState()
    val dailyLogViewModel: DailyLogViewModel = viewModel()
    val logs by dailyLogViewModel.logs.collectAsState()
    var isBleeding by rememberSaveable { mutableStateOf(false) }
    val cycleViewModel : CycleViewModel = viewModel()
    val cycles by cycleViewModel.cycles.collectAsState()
    val currentDate = LocalDate.now()
    var selectedDate by remember { mutableStateOf(currentDate) }
    val today = LocalDate.now()
    var lastRecalculationDate by rememberSaveable { mutableStateOf<LocalDate?>(null) }
    val scope = rememberCoroutineScope()
    var phasesUpdateTrigger by remember { mutableIntStateOf(0) }


    LaunchedEffect(Unit) {
        scrollState.scrollTo(0)
        username?.let { viewModel.getUserByUsername(it) }
    }

    LaunchedEffect(user?.email) {
        user?.email?.let {
            cycleViewModel.loadCycles(it)
            viewModel.getUserByUsername(user!!.username)
            dailyLogViewModel.loadLogs(it)
        }

    }

    LaunchedEffect(logs) {
        isBleeding = logs.find { it.date == today.toString() }?.hasMenstruation ?: false
    }

    LaunchedEffect(user?.email, logs) {
        val email = user?.email ?: return@LaunchedEffect

        val todayLog = logs.find { it.date == today.toString() }
        val hasLoggedToday = todayLog != null
        val hasMenstruationToday = todayLog?.hasMenstruation ?: false
        isBleeding = hasMenstruationToday

        val shouldRecalculate = (!hasLoggedToday || !hasMenstruationToday) &&
                lastRecalculationDate != today

        if (shouldRecalculate) {
            cycleViewModel.recalculateCycle(email, today)
            cycleViewModel.loadCycles(email)
            lastRecalculationDate = today
        }
    }

    LaunchedEffect(scrollState.value, user, token) {
        if (
            scrollState.value == scrollState.maxValue &&
            user?.email != null &&
            !token.isNullOrBlank()
        ) {
            val email = Uri.encode(user!!.email)
            navController.navigate("${AppScreen.DailyScreen.route}/$email/$token/$isBleeding")
        }
    }
    val phases by remember(cycles, currentDate, phasesUpdateTrigger) {
        derivedStateOf {
            cycles.maxByOrNull { it.startDate }?.phases ?: emptyList()
        }
    }
    val daysUntilNextPeriod by remember(phases, currentDate) {
        derivedStateOf {
            phases
                .mapNotNull {
                    if (it.phase == CyclePhase.MENSTRUATION) {
                        runCatching { LocalDate.parse(it.date) }.getOrNull()
                    } else null
                }
                .firstOrNull { it.isAfter(currentDate) }
                ?.let { ChronoUnit.DAYS.between(currentDate, it).toInt() }
                ?: -1 // -1 si no se encuentra una futura fase de menstruación
        }
    }

    val periodIn = daysUntilNextPeriod
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


                    IconButton(
                        onClick = {
                            val encodedUsername = Uri.encode(user?.username ?: "")
                            val encodedEmail = Uri.encode(user?.email ?: "")
                            navController.navigate(route = AppScreen.UserScreen.route + "/$encodedUsername/$encodedEmail")
                        }
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
                            Text(
                                "$periodIn",
                                fontSize = 60.sp,
                                fontWeight = FontWeight.ExtraBold)
                            Button(
                                modifier = Modifier.padding(top = 10.dp)
                                    .height(35.dp),
                                onClick = {
                                    phasesUpdateTrigger++
                                    isBleeding = true
                                    val email = user?.email
                                    if (email != null) {
                                        scope.launch {
                                            cycleViewModel.recalculateCycle(email, today)
                                            cycleViewModel.loadCycles(email)
                                            lastRecalculationDate = today
                                            navController.navigate("${AppScreen.DailyScreen.route}/$email/$token/$isBleeding")
                                        }
                                    }
                                },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = colorResource(R.color.botones2)
                                )
                            ) {
                                Text("Registrar periodo")
                            }
                        }
                    }
                    Spacer(Modifier.height(60.dp))
                    Month(
                        year = currentDate.year,
                        month = currentDate.month,
                        selectedDate = selectedDate,
                        currentDate = currentDate,
                        phases = phases
                    ) { date -> selectedDate = date }
                    Spacer(Modifier.height(100.dp))
                }
            }

            Footer(navController = navController,
                modifier = Modifier
                    .align(alignment = Alignment.BottomCenter)
                    .padding(vertical = 18.dp),
                phases = phases
            )
        }

    }


}