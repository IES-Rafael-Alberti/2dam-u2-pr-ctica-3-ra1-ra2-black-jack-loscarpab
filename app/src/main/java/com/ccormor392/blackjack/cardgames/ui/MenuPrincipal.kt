package com.ccormor392.blackjack.cardgames.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.ccormor392.blackjack.cardgames.data.Rutas.*

@Composable
fun MenuPrincipal(
    navController: NavHostController
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {
        Button(
            modifier = Modifier.padding(bottom = 50.dp),
            onClick = { navController.navigate(UnovsUno.ruta) }
        ) {
            Text(text = "1 vs 1")
        }

        Button(
            onClick = { navController.navigate(MuestraCarta.ruta) }
        ) {
            Text(text = "Muestra Carta")
        }
    }
}