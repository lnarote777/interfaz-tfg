package com.example.interfaz_mesames.screen

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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.interfaz_mesames.R
import com.example.interfaz_mesames.compose.Header
import com.example.interfaz_mesames.compose.premium.CaracteristicasBox


@Composable
fun PremiumScreen(navController: NavController){
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier.fillMaxSize()
            .padding(top = 30.dp, bottom = 18.dp)
            .verticalScroll(scrollState)
    ) {
        Header(navController, "VIP")

        Column(modifier = Modifier.fillMaxSize(),
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
                    Text("1 Año",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.SemiBold)
                    Spacer(Modifier.weight(1f))
                    Row (
                        verticalAlignment = Alignment.Bottom
                    ){
                        Text("€",
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.offset(y = (-25).dp),
                            fontFamily = FontFamily(Font(R.font.inter))
                        )
                        Text("2.99",
                            fontSize = 48.sp,
                            fontWeight = FontWeight.ExtraBold,
                            fontFamily = FontFamily(Font(R.font.inter))
                        )
                        Text("/mes",
                            fontSize = 14.sp,
                            modifier = Modifier.padding(start = 4.dp),
                            fontFamily = FontFamily(Font(R.font.inter))
                        )
                    }
                    Spacer(Modifier.weight(1f))
                    Button(
                        modifier = Modifier.fillMaxWidth(0.85f),
                        shape = RoundedCornerShape(10.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = colorResource(R.color.botones1)),
                        onClick = {}
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
                        onClick = {}
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


























