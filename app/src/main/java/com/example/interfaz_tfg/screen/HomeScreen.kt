package com.example.interfaz_tfg.screen

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
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
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
import com.example.interfaz_tfg.api.model.cycle.CyclePhase
import com.example.interfaz_tfg.compose.Footer
import com.example.interfaz_tfg.compose.calendario.Month
import com.example.interfaz_tfg.navigation.AppScreen
import com.example.interfaz_tfg.utils.groupContinuousDates
import com.example.interfaz_tfg.viewModel.CalendarSharedViewModel
import com.example.interfaz_tfg.viewModel.CycleViewModel
import com.example.interfaz_tfg.viewModel.DailyLogViewModel
import com.example.interfaz_tfg.viewModel.UserViewModel
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.temporal.ChronoUnit
import java.time.Month as JavaMonth


@SuppressLint("NewApi")
@Composable
fun HomeScreen(
    navController: NavController,
    username: String? ,
    userRol: String?,
    token: String?,
    calendarSharedViewModel: CalendarSharedViewModel
){
    // ViewModels
    val userViewModel: UserViewModel = viewModel()
    val cycleViewModel: CycleViewModel = viewModel()
    val dailyLogViewModel: DailyLogViewModel = viewModel()

    // Estados
    val scrollState = rememberScrollState()
    val user by userViewModel.user.collectAsState()
    val logs by dailyLogViewModel.logs.collectAsState()
    val isBleeding by dailyLogViewModel.isBleeding.collectAsState()
    val cycles by cycleViewModel.cycles.collectAsState(initial = emptyList())

    val currentDate = LocalDate.now()
    var selectedDate by remember { mutableStateOf(currentDate) }
    var lastRecalculationDate by rememberSaveable { mutableStateOf<LocalDate?>(null) }
    var phasesUpdateTrigger by remember { mutableIntStateOf(0) }
    val scope = rememberCoroutineScope()
    val color = MaterialTheme.colorScheme
    var currentMonth by remember { mutableStateOf(currentDate.month) }
    var currentYear by remember { mutableStateOf(currentDate.year) }

    // Lógica reactiva
    LaunchedEffect(Unit) {
        //scrollState.scrollTo(0)
        username?.let { userViewModel.getUserByUsername(it) }
    }

    LaunchedEffect(user?.email) {
        user?.email?.let {
            cycleViewModel.getPrediction(it)
            cycleViewModel.loadCycles(it)
            dailyLogViewModel.loadLogs(it)
            userViewModel.getUserByUsername(user!!.username)
        }
    }

    LaunchedEffect(selectedDate) {
        user?.email?.let {
            if (token != null) {
                dailyLogViewModel.setSelectedDate(selectedDate, it, token)
            }
        }
        dailyLogViewModel.updateBleedingStatusForToday(logs, selectedDate)
    }

    LaunchedEffect(logs) {
        dailyLogViewModel.updateBleedingStatusForToday(logs, currentDate)
    }

    LaunchedEffect(user?.email, logs, currentDate) {
        val email = user?.email ?: return@LaunchedEffect
        val todayLog = logs.find { it.date == currentDate.toString() }
        val shouldRecalculate = (todayLog == null || !todayLog.hasMenstruation) &&
                lastRecalculationDate != currentDate

        if (shouldRecalculate) {
            cycleViewModel.recalculateCycle(email, currentDate)
            cycleViewModel.loadCycles(email)
            lastRecalculationDate = currentDate
        }
    }

    // Fases y fechas
    val confirmedPhases = cycles
        .filter { !it.isPredicted }
        .flatMap { it.phases }

    val predictedPhases = cycles
        .filter { it.isPredicted }
        .flatMap { it.phases }

    val confirmedMenstruationDates = confirmedPhases
        .filter { it.phase == CyclePhase.MENSTRUATION }
        .mapNotNull { runCatching { LocalDate.parse(it.date) }.getOrNull() }

    val predictedMenstruationDates = predictedPhases
        .filter { it.phase == CyclePhase.MENSTRUATION }
        .mapNotNull { runCatching { LocalDate.parse(it.date) }.getOrNull() }

    val allMenstruationDates = (confirmedMenstruationDates + predictedMenstruationDates)
        .distinct()
        .sorted()

    val menstruationRanges = groupContinuousDates(allMenstruationDates)
    val menstruationBlockToday = menstruationRanges.find { currentDate in it.first..it.second }
    val isTodayInMenstruation = menstruationBlockToday != null
    val menstruationStartDate = if (isTodayInMenstruation && isBleeding) menstruationBlockToday?.first else null
    val dayInPeriod = menstruationStartDate?.let { ChronoUnit.DAYS.between(it, selectedDate).toInt() + 1 }
    val nextMenstruationRange = menstruationRanges.firstOrNull { it.first.isAfter(selectedDate) }
    val nextMenstruationDate = nextMenstruationRange?.first
    val daysUntilNextPeriod = nextMenstruationDate?.let {
        maxOf(0, ChronoUnit.DAYS.between(selectedDate, it).toInt())
    } ?: -1

    val periodText = when {
        dayInPeriod != null -> "Día $dayInPeriod"
        daysUntilNextPeriod > 0 -> daysUntilNextPeriod.toString()
        else -> ""
    }

    calendarSharedViewModel.confirmedPhases = confirmedPhases
    calendarSharedViewModel.predictedPhases = predictedPhases

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
                //.verticalScroll(scrollState)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp)
                ) {
                    if (userRol != "PREMIUM"){
                        val encodedEmail = Uri.encode(user?.email ?: "")
                        IconButton(
                        onClick = { navController.navigate(route = AppScreen.PremiumScreen.route + "/$encodedEmail") },
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
                            Text(if (periodText == daysUntilNextPeriod.toString()) "Periodo en: " else "Periodo:")
                            Text(
                                periodText,
                                fontSize = 60.sp,
                                fontWeight = FontWeight.ExtraBold
                            )
                            Button(
                                modifier = Modifier.padding(top = 10.dp)
                                    .height(35.dp),
                                onClick = {
                                    val email = user?.email
                                    if (email != null) {
                                        if (!isBleeding){
                                            phasesUpdateTrigger++
                                            dailyLogViewModel.setIsBleeding(true)
                                            scope.launch {
                                                cycleViewModel.updateOrCreateCycleFromLogs(email, logs)
                                                cycleViewModel.recalculateCycle(email, currentDate)
                                                cycleViewModel.loadCycles(email)
                                                lastRecalculationDate = currentDate
                                            }
                                        }


                                        navController.navigate("${AppScreen.DailyScreen.route}/$email/$token/$isBleeding")
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
                        year = currentYear,
                        month = currentMonth,
                        selectedDate = selectedDate,
                        currentDate = currentDate,
                        confirmedPhases = confirmedPhases,
                        predictedPhases = predictedPhases,
                        logs = logs,
                        showNavigationArrows = true,
                        onDateSelected = { date -> selectedDate = date },
                        onPreviousMonth = {
                            val prev = currentMonth.minus(1)
                            if (prev == JavaMonth.DECEMBER) currentYear -= 1
                            currentMonth = prev
                        },
                        onNextMonth = {
                            val next = currentMonth.plus(1)
                            if (next == JavaMonth.JANUARY) currentYear += 1
                            currentMonth = next
                        }
                    )
                    Spacer(Modifier.height(100.dp))
                }
            }

            user?.email?.let {
                if (token != null) {
                    Footer(navController = navController,
                        modifier = Modifier
                            .align(alignment = Alignment.BottomCenter),
                        confirmedPhases,
                        predictedPhases,
                        it,
                        token,
                        isBleeding = isBleeding,
                        logs
                    )
                }
            }
        }

    }


}

