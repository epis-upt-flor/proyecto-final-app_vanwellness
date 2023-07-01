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
fun RutinaAbdomen(onBack: () -> Unit) {
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
                text = "Rutina de Abdomen",
                style = MaterialTheme.typography.h4 // Usa el estilo definido por el tema
            )
            GifImage(modifier = Modifier.size(180.dp), imageResource = R.drawable.abdominal)
            TimerCircle(time, isTimerRunning, 300000L)

            Button(onClick = {
                isTimerRunning = !isTimerRunning
                if (isTimerRunning) {
                    coroutineScope.launch {
                        while (isTimerRunning && time > 0) {
                            delay(1000L)
                            time -= 1000L
                        }
                        if (time <= 0) {
                            isTimerRunning = false
                            // Incrementar el contador en Firebase
                            userRef.update("contadorAbdomen", FieldValue.increment(1))
                        }
                    }
                }
            }) {
                Text(if (isTimerRunning) "Stop" else "Start")
            }

            Text(
                text = "Abdominales",
                style= MaterialTheme.typography.h5 // Usa un estilo más grande para el título
            )

            LazyColumn {
                item {
                    Text(
                        text = "Pasos a seguir en la rutina:",
                        style = MaterialTheme.typography.h6,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                    Divider(
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                }
                items(4) { index ->
                    Text(
                        text = when (index) {
                            0 -> "1. Acuéstese de espaldas con las rodillas extendidas."
                            1 -> "2. Coloque sus manos a cada lado de su cabeza o sujetando un balón."
                            2 -> "3.Levanta los hombros del suelo sin despegar la parte inferior de la espalda para trabajar los músculos del pecho."
                            else -> "4.Tensa los abdominales y regresa a la posición inicial al finalizar el movimiento."
                        },
                        style = MaterialTheme.typography.body1,
                        textAlign = TextAlign.Start,
                        modifier = Modifier.padding(start = 16.dp)
                    )
                }
            }
        }
    }
}