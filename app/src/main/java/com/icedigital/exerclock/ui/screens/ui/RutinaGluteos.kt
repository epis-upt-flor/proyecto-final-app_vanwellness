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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import com.icedigital.exerclock.utils.TimerCircle
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView

@Composable
fun RutinaGluteos(onBack: () -> Unit) {
    // Usamos un rememberSaveable para guardar el estado del tiempo y el estado del temporizador
    var time by rememberSaveable { mutableStateOf(30000L) } // 5 minutos * 60 segundos * 1000 milisegundos
    var isTimerRunning by rememberSaveable { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()

    // Configuración de Firestore
    val db = Firebase.firestore
    val userId = FirebaseAuth.getInstance().currentUser?.uid // Obtiene el ID de usuario dinámicamente
    val userRef = db.collection("users").document(userId ?: "")

    // Configuración del YouTubePlayerView
    val context = LocalContext.current
    val youtubePlayerView = remember { YouTubePlayerView(context) }
    val videoId = "wL87zJ6wgxw"

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
                text = "Rutina de Glúteos",
                style = MaterialTheme.typography.h4 // Usa el estilo definido por el tema
            )
            AndroidView(
                modifier = Modifier
                    .fillMaxWidth() // Esto hace que la vista ocupe todo el ancho de la pantalla
                    .aspectRatio(16/9f), // Mantén la relación de aspecto 16:9 para los videos de YouTube
                factory = { youtubePlayerView },
                update = {
                    it.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
                        override fun onReady(youTubePlayer: YouTubePlayer) {
                            val videoId = "wL87zJ6wgxw"
                            youTubePlayer.loadVideo(videoId, 0f)
                        }
                    })
                }
            )

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
                            userRef.update("contadorGluteos", FieldValue.increment(1))
                        }
                    }
                }
            }) {
                Text(if (isTimerRunning) "Stop" else "Start")
            }

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
                            0 -> "1. Paso uno para la rutina."
                            1 -> "2. Paso dos para la rutina."
                            2 -> "3. Paso tres para la rutina."
                            else -> "4. Paso cuatro para la rutina."
                        },
                        style = MaterialTheme.typography.body1,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                }
            }
        }
    }
}
