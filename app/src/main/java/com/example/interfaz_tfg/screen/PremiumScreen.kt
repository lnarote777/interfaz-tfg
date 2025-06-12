package com.example.interfaz_tfg.screen

import androidx.compose.material3.Divider
import android.graphics.drawable.Icon
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.ScrollableState
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.interfaz_tfg.R
import com.example.interfaz_tfg.api.model.user.SubscriptionType
import com.example.interfaz_tfg.compose.Header
import com.example.interfaz_tfg.compose.premium.CaracteristicasBox
import com.example.interfaz_tfg.utils.UserPreferences
import com.example.interfaz_tfg.viewModel.LoginViewModel
import com.example.interfaz_tfg.viewModel.PagoViewModel
import com.example.interfaz_tfg.viewModel.getRolFromToken
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch


@Composable
fun PremiumScreen(navController: NavController, email: String){
    val scrollState = rememberScrollState()
    val color = MaterialTheme.colorScheme
    val pagoViewModel : PagoViewModel = viewModel()
    val uriHandler = LocalUriHandler.current
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    val loginViewModel: LoginViewModel = viewModel()
    val lifecycleOwner = LocalLifecycleOwner.current
    var paymentInitiated by remember { mutableStateOf(false) }

    var token : String? = ""

    LaunchedEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_RESUME && paymentInitiated) {
                // La app ha vuelto al primer plano después de iniciar un pago
                // Esperamos un poco para asegurar que el pago se haya procesado
                scope.launch {
                    delay(1000)

                    // Rehacer el login para actualizar el token del usuario
                    val username = UserPreferences.getUsername(context).firstOrNull()
                    val password = UserPreferences.getPassword(context).firstOrNull()

                    if (username != null && password != null) {
                        loginViewModel.login(context, username, password, navController)
                    }

                    paymentInitiated = false
                }
            }
        }
        token = UserPreferences.getToken(context).firstOrNull()
        lifecycleOwner.lifecycle.addObserver(observer)
    }

    LaunchedEffect(pagoViewModel.checkoutUrl) {
        pagoViewModel.checkoutUrl.collect { url ->
            // Abrir URL de Stripe Checkout en navegador
            if (url != null) {
                paymentInitiated = true
                uriHandler.openUri(url)
            }
        }
    }

    Scaffold {innerpadding ->
        Column(
            modifier = Modifier.fillMaxSize()
                .padding(innerpadding)

        ) {
            Header(navController, "VIP", onClick = {})

            Column(modifier = Modifier.verticalScroll(scrollState),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    verticalAlignment = Alignment.Top
                ) {
                    Text("VIP",
                        fontSize = 40.sp,
                        fontWeight = FontWeight.Black
                    )
                    Icon(
                        painter = painterResource(R.drawable.corona),
                        contentDescription = "Vip",
                        tint = colorResource(R.color.ocre)
                    )
                }

                Text(text = "Pásate a premium y descubre todas las opciones que te ofrecemos.",
                    fontSize = 24.sp,
                    modifier = Modifier
                        .padding(vertical = 20.dp, horizontal = 40.dp)
                        .fillMaxWidth(),
                    textAlign = TextAlign.Center
                )

                CaracteristicasBox()

                Spacer(Modifier.height(20.dp))

                Box(contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .fillMaxWidth(0.8f)
                        .border(width = 3.dp, color = colorResource(R.color.botones2), shape = RoundedCornerShape(30.dp))
                        .clip(RoundedCornerShape(30.dp))
                        .background(Color.White)
                        .height(221.dp)
                ) {

                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        Spacer(Modifier.weight(1f))
                        Text("De por vida",
                            fontSize = 24.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = Color.Black
                        )
                        Spacer(Modifier.weight(1f))
                        Row (
                            verticalAlignment = Alignment.Bottom
                        ){
                            Text("€",
                                fontSize = 24.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.offset(y = (-25).dp),
                                fontFamily = FontFamily(Font(R.font.inter)),
                                color = Color.Black
                            )
                            Text("3.95",
                                fontSize = 48.sp,
                                fontWeight = FontWeight.ExtraBold,
                                fontFamily = FontFamily(Font(R.font.inter)),
                                color = Color.Black
                            )
                        }
                        Spacer(Modifier.weight(1f))
                        Button(
                            modifier = Modifier.fillMaxWidth(0.85f),
                            shape = RoundedCornerShape(10.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = colorResource(R.color.botones1)),
                            onClick = {
                                scope.launch {
                                    token?.let { pagoViewModel.iniciarPago(it, email, SubscriptionType.ONE_TIME) } // Para 1 año
                                }
                            }
                        ) {
                            Text("Continuar",
                                fontFamily = FontFamily(Font(R.font.inter)))
                        }
                        Spacer(Modifier.weight(1f))
                    }

                }

                Spacer(Modifier.height(20.dp))

                Box(
                    modifier = Modifier
                        .fillMaxWidth(0.8f)
                        .border(width = 3.dp, color = colorResource(R.color.botones2), shape = RoundedCornerShape(30.dp))
                        .clip(RoundedCornerShape(30.dp))
                        .background(colorResource(R.color.botones1))
                        .height(221.dp)
                ) {

                    Column (
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.fillMaxSize()
                    ){
                        Spacer(Modifier.weight(1f))
                        Text("1 Mes",
                            fontSize = 24.sp,
                            color = Color.White,
                            fontWeight = FontWeight.SemiBold)
                        Spacer(Modifier.weight(1f))
                        Row (
                            verticalAlignment = Alignment.Bottom
                        ){
                            Text("€",
                                fontSize = 24.sp,
                                color = Color.White,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.offset(y = (-25).dp),
                                fontFamily = FontFamily(Font(R.font.inter))
                            )
                            Text("0.95",
                                fontSize = 48.sp,
                                color = Color.White,
                                fontWeight = FontWeight.ExtraBold,
                                fontFamily = FontFamily(Font(R.font.inter))
                            )
                            Text("/mes",
                                fontSize = 14.sp,
                                color = Color.White,
                                modifier = Modifier.padding(start = 4.dp),
                                fontFamily = FontFamily(Font(R.font.inter))
                            )
                        }
                        Spacer(Modifier.weight(1f))
                        Button(
                            modifier = Modifier.fillMaxWidth(0.85f),
                            shape = RoundedCornerShape(10.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = colorResource(R.color.botones2)),
                            onClick = {
                                scope.launch {
                                    token?.let { pagoViewModel.iniciarPago(it, email, SubscriptionType.MONTHLY) }
                                }
                            }
                        ) {
                            Text("Continuar",
                                fontFamily = FontFamily(Font(R.font.inter)))
                        }
                        Spacer(Modifier.weight(1f))
                    }

                }

            }
        }
    }

}


























