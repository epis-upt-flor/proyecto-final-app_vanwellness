package com.icedigital.exerclock.ui.screens.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Button
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import com.icedigital.exerclock.R
import com.icedigital.exerclock.utils.GifImage
import com.icedigital.exerclock.utils.TimerCircle

@Composable
fun RutinaPiernas(onBack: () -> Unit) {
    // Usamos un rememberSaveable para guardar el estado del tiempo y el estado del temporizador
    var time by rememberSaveable { mutableStateOf(30000L) } // 5 minutos * 60 segundos * 1000 milisegundos
    var isTimerRunning by rememberSaveable { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()

    // Configuración de Firestore
    val db = Firebase.firestore
    val userId = FirebaseAuth.getInstance().currentUser?.uid // Obtiene el ID de usuario dinámicamente
    val userRef = db.collection("users").document(userId ?: "")

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp), // Agrega relleno a toda la columna
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = "Rutina de Piernas",
                style = MaterialTheme.typography.h4 // Usa el estilo definido por el tema
            )
            GifImage(modifier = Modifier.size(180.dp), imageResource = R.drawable.piernas) // Cambia 'your_gif_name' por el nombre real del recurso GIF
            TimerCircle(time, isTimerRunning, 300000L) // Aquí cambia 'total_time_in_milliseconds' por el tiempo total real
            Button(onClick = {
                // Cambiamos el estado del temporizador directamente
                isTimerRunning = !isTimerRunning
                if (isTimerRunning) {
                    coroutineScope.launch {
                        while (isTimerRunning && time > 0) {
                            delay(1000L)
                            // Restamos un segundo al tiempo directamente
                            time -= 1000L
                        }
                        if (time <= 0) {
                            isTimerRunning = false
                            // Incrementar el contador en Firebase
                            userRef.update("contadorPierna", FieldValue.increment(1))
                        }
                    }
                }
            }) {
                Text(if (isTimerRunning) "Stop" else "Start")
            }

            Text(
                text = "Sentadillas con Mancuernas",
                style = MaterialTheme.typography.h5 // Usa un estilo más grande para el título
            )
            // Usamos un LazyColumn para mostrar el texto con los pasos a seguir
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp) // Agrega un espacio entre cada elemento
            ) {
                item {
                    Text(
                        text = "Pasos a seguir en la rutina:",
                        style = MaterialTheme.typography.h6, // Usa un estilo más pequeño para el subtítulo
                        textAlign = TextAlign.Center, // Alinea el subtítulo al centro
                        modifier = Modifier.padding(vertical = 8.dp) // Agrega un relleno al subtítulo
                    )
                    Divider(
                        modifier = Modifier.padding(vertical = 8.dp) // Agrega un relleno al divisor
                    )
                    Text(
                        text = "1. Sosteniendo las pesas a los lados, en posición vertical, los pies separados a la anchura de los hombros.",
                        style = MaterialTheme.typography.body1, // Usa el estilo definido por el tema
                        textAlign = TextAlign.Start, // Alinea el texto al inicio
                        modifier = Modifier.padding(start = 16.dp) // Agrega un relleno al inicio
                    )
                    Text(
                        text = "2. Doble de rodillas hasta que los muslos queden casi paralelos al suelo.",
                        style = MaterialTheme.typography.body1,
                        textAlign = TextAlign.Start,
                        modifier = Modifier.padding(start = 16.dp)
                    )
                    Text(
                        text = "3. Mantenga la espalda plana, la baja espalda ligeramente arqueada hacia dentro y la cabeza mirando adelante.",
                        style = MaterialTheme.typography.body1,
                        textAlign = TextAlign.Start,
                        modifier = Modifier.padding(start = 16.dp)
                    )
                }
            }
        }
    }
}