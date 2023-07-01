package com.icedigital.exerclock.ui.screens.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

@Composable
fun InformeScreen(onBack: () -> Unit) {
    val firestore = FirebaseFirestore.getInstance()
    var contadorPecho by remember { mutableStateOf("") }
    var contadorPierna by remember { mutableStateOf("") }
    var contadorAbdomen by remember { mutableStateOf("") }
    var contadorBiceps by remember { mutableStateOf("") }
    var contadorGluteos by remember { mutableStateOf("") }

    val user = FirebaseAuth.getInstance().currentUser
    if (user != null) {
        val uid = user.uid // Aquí obtienes el ID del usuario actual

        LaunchedEffect(uid) { // Agregas el ID del usuario como key para el LaunchedEffect
            firestore.collection("users")
                .document(uid) // Aquí usas el ID del usuario actual
                .get()
                .addOnSuccessListener { document ->
                    if (document != null) {
                        contadorPecho = document["contadorPecho"]?.toString() ?: "0"
                        contadorPierna = document["contadorPierna"]?.toString() ?: "0"
                        contadorAbdomen = document["contadorAbdomen"]?.toString() ?: "0"
                        contadorBiceps = document["contadorBiceps"]?.toString() ?: "0"
                        contadorGluteos = document["contadorGluteos"]?.toString() ?: "0"
                    }
                }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Título de la pantalla
        Text(
            text = "Informe de Rutinas",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Tarjeta de la rutina de pechos
        Card(
            modifier = Modifier.fillMaxWidth(),
            elevation = 4.dp
        ) {
            Row(
                modifier = Modifier.padding(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // Nombre de la rutina
                Text(
                    text = "Rutina de Pechos",
                    fontSize = 20.sp
                )

                // Contador en un cuadro celeste
                Box(
                    modifier = Modifier
                        .size(50.dp)
                        .background(Color(0xFF87CEFA)), // Color celeste
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = contadorPecho, // Usando el valor del contador desde Firebase
                        fontSize = 18.sp,
                        color = Color.White
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Tarjeta de la rutina de piernas
        Card(
            modifier = Modifier.fillMaxWidth(),
            elevation = 4.dp
        ) {
            Row(
                modifier = Modifier.padding(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // Nombre de la rutina
                Text(
                    text = "Rutina de Piernas",
                    fontSize = 20.sp
                )

                // Contador en un cuadro verde
                Box(
                    modifier = Modifier
                        .size(50.dp)
                        .background(Color(0xFFE91E63)), // Color verde
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = contadorPierna, // Usando el valor del contador desde Firebase
                        fontSize = 18.sp,
                        color = Color.White
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Tarjeta de la rutina de abdominales
        Card(
            modifier = Modifier.fillMaxWidth(),
            elevation = 4.dp
        ) {
            Row(
                modifier = Modifier.padding(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // Nombre de la rutina
                Text(
                    text = "Rutina de Abdominales",
                    fontSize = 20.sp
                )

                // Contador en un cuadro rojo
                Box(
                    modifier = Modifier
                        .size(50.dp)
                        .background(Color(0xFFFF5722)), // Color rojo
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = contadorAbdomen, // Usando el valor del contador desde Firebase
                        fontSize = 18.sp,
                        color = Color.White
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Tarjeta de la rutina de bíceps
        Card(
            modifier = Modifier.fillMaxWidth(),
            elevation = 4.dp
        ) {
            Row(
                modifier = Modifier.padding(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // Nombre de la rutina
                Text(
                    text = "Rutina de Bíceps",
                    fontSize = 20.sp
                )

                // Contador en un cuadro amarillo
                Box(
                    modifier = Modifier
                        .size(50.dp)
                        .background(Color(0xFFFFC107)), // Color amarillo
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = contadorBiceps, // Usando el valor del contador desde Firebase
                        fontSize = 18.sp,
                        color = Color.White
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Tarjeta de la rutina de piernas
        Card(
            modifier = Modifier.fillMaxWidth(),
            elevation = 4.dp
        ) {
            Row(
                modifier = Modifier.padding(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // Nombre de la rutina
                Text(
                    text = "Rutina de Gluteos",
                    fontSize = 20.sp
                )

                // Contador en un cuadro verde
                Box(
                    modifier = Modifier
                        .size(50.dp)
                        .background(Color(0xFF310212)), // Color verde
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = contadorGluteos, // Usando el valor del contador desde Firebase
                        fontSize = 18.sp,
                        color = Color.White
                    )
                }
            }
        }
    }
}