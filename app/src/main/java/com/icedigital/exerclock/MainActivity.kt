package com.icedigital.exerclock

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.icedigital.exerclock.navigation.Destinations
import com.icedigital.exerclock.navigation.NavigationHost
import com.icedigital.exerclock.ui.components.BottomNavigationBar

class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterialScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            MaterialTheme {
                // Use rememberNavController to remember and retrieve a NavHostController
                val navController = rememberNavController()

                // Lista de rutas que requieren la barra de navegación
                val routesWithBottomNav = listOf(
                    Destinations.Screen.InicioScreen.route,
                    Destinations.Screen.InformeScreen.route,
                    Destinations.Screen.PerfilScreen.route
                )

                Scaffold(
                    bottomBar = {
                        val currentRoute = currentRoute(navController)
                        if (currentRoute in routesWithBottomNav) {
                            BottomNavigationBar(navController, items = listOf(
                                Destinations.Screen.InicioScreen,
                                Destinations.Screen.InformeScreen,
                                Destinations.Screen.PerfilScreen
                            ))
                        }
                    }
                ){
                    NavigationHost(navController, onBack = { onBackPressed() })
                }
            }
        }
    }
}
@Composable
private fun currentRoute(navController: NavController): String? {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    return navBackStackEntry?.destination?.route
}
