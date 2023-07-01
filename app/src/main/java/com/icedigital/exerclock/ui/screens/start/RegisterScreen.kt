package com.icedigital.exerclock.ui.screens.start

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.icedigital.exerclock.data.RegisterViewModel
import com.icedigital.exerclock.data.RegisterViewModel.RegistrationState.SUCCESS
@Composable
fun RegisterScreen(
    onBack: () -> Unit,
    onRegisterSuccess: () -> Unit
) {
    val viewModel: RegisterViewModel = viewModel()
    val registrationState by viewModel.registrationState.observeAsState()
    val name = remember { mutableStateOf("") }
    val email = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }

    LaunchedEffect(registrationState) {
        if (registrationState == SUCCESS) {
            onRegisterSuccess()
            viewModel.resetRegistrationState()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Registro de usuario")
        Spacer(Modifier.size(16.dp))
        OutlinedTextField(
            value = name.value,
            placeholder = { Text(text = "Nombre") },
            label = { Text(text = "Nombre") },
            onValueChange = { name.value = it },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.size(16.dp))
        OutlinedTextField(
            value = email.value,
            placeholder = { Text(text = "user@email.com") },
            label = { Text(text = "email") },
            onValueChange = { email.value = it },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.size(16.dp))
        OutlinedTextField(
            value = password.value,
            placeholder = { Text(text = "password") },
            label = { Text(text = "password") },
            onValueChange = { password.value = it },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.size(16.dp))

        Button(
            onClick = {
                viewModel.resetRegistrationState() // Agrega esta línea
                viewModel.register(name.value, email.value, password.value)
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Registrarse")
        }
    }
}
