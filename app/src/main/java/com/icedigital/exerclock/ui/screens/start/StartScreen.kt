package com.icedigital.exerclock.ui.screens.start

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.icedigital.exerclock.R

@Composable
fun StartScreen(onLoginClick: () -> Unit, onRegisterClick: () -> Unit) {
    Box(modifier = Modifier.fillMaxSize()) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center,
            content = {
                Image(
                    painter = painterResource(id = R.drawable.fondooficial),
                    contentDescription = "fondo",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            }
        )
        Column(
            modifier = Modifier.align(Alignment.BottomCenter)
            .padding(bottom = 120.dp), // Ajustar el valor del padding para subir o bajar los botones
        horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Spacer(modifier = Modifier.weight(2f)) // Espacio que ocupa todo el espacio vertical disponible

            Button(
                onClick = onLoginClick,
                colors = ButtonDefaults.buttonColors(backgroundColor = Color.Blue),
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier
                    .width(160.dp)
                    .height(50.dp)
            ) {
                Text(text = "Iniciar sesión", color = Color.White, fontSize = 16.sp)
            }
            Button(
                onClick = onRegisterClick,
                colors = ButtonDefaults.buttonColors(backgroundColor = Color.Blue),
                shape = CutCornerShape(10.dp),
                modifier = Modifier
                    .width(160.dp)
                    .height(50.dp)
            ) {
                Text(text = "Registro", color = Color.White, fontSize = 16.sp)
            }
        }
    }
}