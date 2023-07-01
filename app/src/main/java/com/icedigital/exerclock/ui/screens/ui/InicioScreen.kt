package com.icedigital.exerclock.ui.screens.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.icedigital.exerclock.R

@Composable
fun InicioScreen(onBack: () -> Unit) {
    val currentScreen = remember { mutableStateOf("Inicio") }

    val onBack = { currentScreen.value = "Inicio" }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Image(
            painter = painterResource(id = R.drawable.fondooficial),
            contentDescription = "Background",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxSize()
        ) {
            when (currentScreen.value) {
                "Inicio" -> {
                    ButtonCard(
                        name = "Rutina Pechos",
                        onClick = { currentScreen.value = "RutinaPechos" }
                    )
                    ButtonCard(
                        name = "Rutina Bíceps",
                        onClick = { currentScreen.value = "RutinaBiceps" }
                    )
                    ButtonCard(
                        name = "Rutina Piernas",
                        onClick = { currentScreen.value = "RutinaPiernas" }
                    )
                    ButtonCard(
                        name = "Rutina Abdomen",
                        onClick = { currentScreen.value = "RutinaAbdomen" }
                    )
                    ButtonCard(
                        name = "Rutina Glúteos",
                        onClick = { currentScreen.value = "RutinaGluteos" }
                    )
                }

                "RutinaPechos" -> RutinaPechos(onBack)
                "RutinaBiceps" -> RutinaBiceps(onBack)
                "RutinaPiernas" -> RutinaPiernas(onBack)
                "RutinaAbdomen" -> RutinaAbdomen(onBack)
                "RutinaGluteos" -> RutinaGluteos(onBack)
            }
        }
    }
}


    @OptIn(ExperimentalMaterialApi::class)
    @Composable
    fun ButtonCard(name: String, onClick: () -> Unit) {
        Card(
            onClick = onClick,
            elevation = 10.dp,
            modifier = Modifier.padding(10.dp)
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
            ) {
                Text(
                    text = "El único límite eres tú mismo.",
                    fontSize = 14.sp,
                    color = Color.Gray
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = name,
                    fontSize = 18.sp,
                    color = Color.Black,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
