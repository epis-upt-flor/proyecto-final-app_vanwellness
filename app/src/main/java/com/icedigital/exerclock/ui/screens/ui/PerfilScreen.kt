package com.icedigital.exerclock.ui.screens.ui

import android.annotation.SuppressLint
import androidx.compose.runtime.collectAsState
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberImagePainter
import com.icedigital.exerclock.R
import com.icedigital.exerclock.data.PerfilViewModel

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun PerfilScreen(onBack: () -> Unit) {
    val perfilViewModel: PerfilViewModel = viewModel()

    val imagePickerCover = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        uri?.let {
            perfilViewModel.saveCoverPicture(it)
        }
    }

    val imagePickerProfile = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        uri?.let {
            perfilViewModel.saveProfilePicture(it)
        }
    }

    val perfilIcon = painterResource(id = R.drawable.perfil)
    val portadaIcon = painterResource(id = R.drawable.portada)

    val coverUrl = perfilViewModel.coverPictureUrl.collectAsState().value
    val profileUrl = perfilViewModel.profilePictureUrl.collectAsState().value
    val userName by perfilViewModel.userName.collectAsState()
    val description by perfilViewModel.description.collectAsState()

    Scaffold(
        content = {
            Column {
                Box(
                    modifier = Modifier
                        .height(200.dp)
                        .fillMaxWidth()
                        .clickable { imagePickerCover.launch("image/*") }, // Mueve el clickable aquí
                    contentAlignment = Alignment.Center
                ) {
                    val coverUrl = perfilViewModel.coverPictureUrl.collectAsState().value
                    val coverPainter = rememberImagePainter(data = coverUrl)

                    Image(
                        painter = coverPainter,
                        contentDescription = "Cover Picture",
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )

                    if (coverUrl == null || coverUrl.isEmpty()) { // Solo muestra el icono si no hay una imagen cargada
                        Image(
                            painter = portadaIcon,
                            contentDescription = "Change Cover Picture",
                            modifier = Modifier
                                .size(48.dp) // Aumenta el tamaño aquí
                        )
                    }
                }

                Box(
                    modifier = Modifier
                        .size(200.dp)
                        .padding(16.dp)
                        .clickable { imagePickerProfile.launch("image/*") }, // Mueve el clickable aquí
                    contentAlignment = Alignment.Center
                ) {
                    val profileUrl = perfilViewModel.profilePictureUrl.collectAsState().value
                    val profilePainter = rememberImagePainter(data = profileUrl)

                    Image(
                        painter = profilePainter,
                        contentDescription = "Profile Picture",
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )

                    if (profileUrl == null || profileUrl.isEmpty()) { // Solo muestra el icono si no hay una imagen cargada
                        Image(
                            painter = perfilIcon,
                            contentDescription = "Change Profile Picture",
                            modifier = Modifier
                                .size(48.dp) // Aumenta el tamaño aquí
                        )
                    }
                }

                var userName by remember { mutableStateOf("") }
                var tempUserName by remember { mutableStateOf("") }
                var isDialogVisibleUserName by remember { mutableStateOf(false) }

                perfilViewModel.userName.collectAsState().value?.let { currentUserName ->
                    userName = currentUserName
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(userName)
                    IconButton(onClick = {
                        tempUserName = userName // copy the current username to a temp variable
                        isDialogVisibleUserName = true  // show the dialog
                    }) {
                        Icon(Icons.Default.Edit, contentDescription = "Edit username")
                    }
                }

                if (isDialogVisibleUserName) {
                    AlertDialog(
                        onDismissRequest = {
                            isDialogVisibleUserName = false
                        },
                        title = { Text("Editar nombre de usuario") },
                        text = {
                            TextField(value = tempUserName, onValueChange = { tempUserName = it })
                        },
                        confirmButton = {
                            Row(
                                horizontalArrangement = Arrangement.Center,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Button(onClick = {
                                    userName = tempUserName
                                    perfilViewModel.saveUserName(tempUserName)
                                    isDialogVisibleUserName = false
                                }) {
                                    Text("Confirmar")
                                }
                            }
                        },
                        dismissButton = {
                            Row(
                                horizontalArrangement = Arrangement.Center,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Button(onClick = {
                                    isDialogVisibleUserName = false
                                }) {
                                    Text("Cancelar")
                                }
                            }
                        }
                    )
                }

                var description by remember { mutableStateOf(perfilViewModel.description.value) }
                var tempDescription by remember { mutableStateOf("") }
                var isDialogVisibleDescription by remember { mutableStateOf(false) }

                Text(description)

                IconButton(onClick = {
                    tempDescription = description // copy the current description to a temp variable
                    isDialogVisibleDescription = true  // show the dialog
                }) {
                    Icon(Icons.Default.Edit, contentDescription = "Edit description")
                }

                if (isDialogVisibleDescription) {
                    AlertDialog(
                        onDismissRequest = {
                            isDialogVisibleDescription = false
                        },
                        title = { Text("Editar descripción") },
                        text = {
                            TextField(value = tempDescription, onValueChange = { tempDescription = it })
                        },
                        confirmButton = {
                            Button(onClick = {
                                description = tempDescription
                                perfilViewModel.saveDescription(tempDescription)
                                isDialogVisibleDescription = false
                            }) {
                                Text("Confirmar")
                            }
                        },
                        dismissButton = {
                            Button(onClick = {
                                isDialogVisibleDescription = false
                            }) {
                                Text("Cancelar")
                            }
                        }
                    )
                }
            }
        }
    )
}