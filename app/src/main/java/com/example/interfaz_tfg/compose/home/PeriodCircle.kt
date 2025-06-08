package com.example.interfaz_tfg.compose.home

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.interfaz_tfg.R
import com.example.interfaz_tfg.api.model.cycle.DailyLog
import com.example.interfaz_tfg.api.model.user.UserDTO
import com.example.interfaz_tfg.compose.home.homeDataClasses.HomeScreenViewModels
import com.example.interfaz_tfg.compose.home.homeDataClasses.PeriodState
import com.example.interfaz_tfg.utils.handlePeriodRegistration
import kotlinx.coroutines.CoroutineScope
import java.time.LocalDate

@SuppressLint("NewApi")
@Composable
fun PeriodCircle(
    navController: NavController,
    token: String?,
    user: UserDTO?,
    logs: List<DailyLog>,
    isBleeding: Boolean,
    periodState: PeriodState,
    viewModels: HomeScreenViewModels,
    scope: CoroutineScope
) {
    val currentDate = LocalDate.now()
    
    Box(
        contentAlignment = Alignment.Center,
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
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                if (periodState.periodText == periodState.daysUntilNextPeriod.toString()) {
                    "Periodo en: "
                } else {
                    "Periodo:"
                }
            )
            Text(
                periodState.periodText,
                fontSize = 60.sp,
                fontWeight = FontWeight.ExtraBold
            )
            Button(
                modifier = Modifier
                    .padding(top = 10.dp)
                    .height(35.dp),
                onClick = {
                    handlePeriodRegistration(
                        user = user,
                        token = token,
                        isBleeding = isBleeding,
                        logs = logs,
                        currentDate = currentDate,
                        viewModels = viewModels,
                        navController = navController,
                        scope = scope
                    )
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorResource(R.color.botones2)
                )
            ) {
                Text("Registrar periodo")
            }
        }
    }
}