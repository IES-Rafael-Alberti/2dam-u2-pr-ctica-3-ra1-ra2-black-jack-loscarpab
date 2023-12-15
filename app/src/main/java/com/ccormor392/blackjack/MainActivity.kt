package com.ccormor392.blackjack

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ccormor392.blackjack.cardgames.data.Rutas.*
import com.ccormor392.blackjack.cardgames.ui.MenuPrincipal
import com.ccormor392.blackjack.cardgames.ui.MuestraCarta
import com.ccormor392.blackjack.cardgames.ui.MuestraCartaViewModel
import com.ccormor392.blackjack.cardgames.ui.UnoVsUno
import com.ccormor392.blackjack.cardgames.ui.UnovsUnoViewModel


class MainActivity : ComponentActivity() {
    private val unoVsUnoViewModel:UnovsUnoViewModel by viewModels()
    private val muestraCartaViewModel:MuestraCartaViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MaterialTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()

                    NavHost(
                        navController = navController,
                        startDestination = MenuPrincipal.ruta
                    ) {
                        composable(MenuPrincipal.ruta) {
                            MenuPrincipal(
                                navController = navController
                            )
                        }
                        composable(UnovsUno.ruta) {
                            UnoVsUno(
                                unoVsUnoViewModel = unoVsUnoViewModel,
                                navController = navController
                            )
                        }
                        composable(MuestraCarta.ruta) {
                            MuestraCarta(
                                navController = navController,
                                muestraCartaViewModel
                            )
                        }
                    }
                }
            }
        }
    }
}
