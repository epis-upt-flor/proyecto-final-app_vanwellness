package com.icedigital.exerclock.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.icedigital.exerclock.ui.screens.start.LoginScreen
import com.icedigital.exerclock.ui.screens.start.RegisterScreen
import com.icedigital.exerclock.ui.screens.start.StartScreen
import com.icedigital.exerclock.ui.screens.ui.InformeScreen
import com.icedigital.exerclock.ui.screens.ui.InicioScreen
import com.icedigital.exerclock.ui.screens.ui.PerfilScreen

@Composable
fun NavigationHost(
    navController: NavHostController,
    onBack: () -> Unit // Añadido el parámetro onBack
) {
    NavHost(navController = navController, startDestination = Destinations.Screen.StartScreen.route) {
        composable(Destinations.Screen.StartScreen.route) {
            StartScreen(
                onLoginClick = { navController.navigate(Destinations.Screen.LoginScreen.route) },
                onRegisterClick = { navController.navigate(Destinations.Screen.RegisterScreen.route) }
            )
        }
        composable(Destinations.Screen.RegisterScreen.route){
            RegisterScreen(
                onBack = { navController.popBackStack() },
                onRegisterSuccess = {
                    navController.navigate(Destinations.Screen.InicioScreen.route)
                }
            )
        }

        composable(Destinations.Screen.LoginScreen.route) {
            LoginScreen(
                onBack = onBack,
                onLoginSuccess = {
                    navController.navigate(Destinations.Screen.InicioScreen.route)
                }
            )
        }
        composable(Destinations.Screen.InicioScreen.route) {
            InicioScreen(onBack = onBack)
        }
        composable(Destinations.Screen.InformeScreen.route) {
            InformeScreen(onBack = onBack)
        }
        composable(Destinations.Screen.PerfilScreen.route) {
            PerfilScreen(onBack = onBack)
        }
    }
}
