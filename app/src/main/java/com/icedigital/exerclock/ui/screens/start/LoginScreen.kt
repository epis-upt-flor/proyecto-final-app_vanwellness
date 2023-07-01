package com.icedigital.exerclock.ui.screens.start

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.icedigital.exerclock.R
import com.icedigital.exerclock.data.AuthResultCode
import com.icedigital.exerclock.data.LoginViewModel
import com.icedigital.exerclock.data.AuthResultCode.SUCCESS


@Composable

fun LoginScreen (onBack: () -> Unit, onLoginSuccess: () -> Unit) {
    val viewModel: LoginViewModel = viewModel()
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        viewModel.handleLoginResult(result.resultCode, result.data)
    }
    val authResultCode by viewModel.authResultCode.observeAsState()

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("") }

    LaunchedEffect(authResultCode) {
        if (authResultCode == SUCCESS) {
            onLoginSuccess()
            viewModel.resetAuthResultCode() // deberías añadir este método en tu ViewModel para reiniciar authResultCode
        }
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color.Black
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                modifier = Modifier.size(250.dp),
                painter = painterResource(id = R.drawable.icono_sinfondo),
                contentDescription = "Icono"
            )
            Text(
                text = "¡Bienvenido a Van Wellness!",
                textAlign = TextAlign.Center,
                fontSize = 25.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
            Spacer(modifier = Modifier.size(10.dp))
            Text(
                text = "Ingrese sus datos para iniciar sesión",
                color = Color.White,
                fontSize = 18.sp
            )

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text("Email") },
                    modifier = Modifier.fillMaxWidth(),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        backgroundColor = Color.White
                    )
                )

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    label = { Text("Contraseña") },
                    visualTransformation = PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    modifier = Modifier.fillMaxWidth(),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        backgroundColor = Color.White
                    )
                )

            when (authResultCode) {
                SUCCESS -> {
                    // Haz nada aquí
                }

                AuthResultCode.CANCELLED -> {
                    Text(text = "Por favor, inicia sesión con tu email o Google")
                }

                AuthResultCode.ERROR -> {
                    Text(text = "Ocurrió un error al iniciar sesión")
                    Button(
                        onClick = {
                            if (email.isNotEmpty() && password.isNotEmpty()) {
                                viewModel.login(email, password)
                            } else {
                                errorMessage = "Por favor, ingresa un email y contraseña válidos."
                            }
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(text = "Iniciar sesión")
                    }
                }

                else -> {
                    Button(
                        onClick = {
                            if (email.isNotEmpty() && password.isNotEmpty()) {
                                viewModel.login(email, password)
                            } else {
                                errorMessage = "Por favor, ingresa un email y contraseña válidos."
                            }
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(text = "Iniciar sesión")
                    }
                }
            }

            if (errorMessage.isNotEmpty()) {
                Text(text = errorMessage)
            }
        }
    }
}
}
