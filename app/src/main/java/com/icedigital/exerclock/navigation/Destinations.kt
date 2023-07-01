package com.icedigital.exerclock.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.ui.graphics.vector.ImageVector

object Destinations {
    sealed class Screen(val route: String, val title: String, val icon: ImageVector? = null) {
        object StartScreen : Screen("start_screen", "Start Screen")
        object RegisterScreen : Screen("register_screen", "Register Screen")
        object LoginScreen : Screen("login_screen", "Login Screen")
        object InicioScreen : Screen("inicio_screen", "Inicio Screen", Icons.Filled.Home)
        object InformeScreen : Screen("informe_screen", "Informe Screen", Icons.Filled.Description)
        object PerfilScreen : Screen("perfil_screen", "Perfil Screen", Icons.Filled.Person)
    }
}
