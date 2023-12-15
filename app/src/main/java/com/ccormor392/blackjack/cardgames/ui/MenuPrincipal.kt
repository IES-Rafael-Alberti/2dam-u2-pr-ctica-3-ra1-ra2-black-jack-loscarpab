package com.ccormor392.blackjack.cardgames.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.ccormor392.blackjack.R
import com.ccormor392.blackjack.cardgames.data.Rutas.MuestraCarta
import com.ccormor392.blackjack.cardgames.data.Rutas.UnovsUno

@Composable
fun MenuPrincipal(
    navController: NavHostController
) {
    //columna usada para poner la imagen de fondo
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .paint(painterResource(id = R.drawable.tapete), contentScale = ContentScale.FillHeight)
            .padding(16.dp)
    ) {
        //imagen con cartas
        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = null,
            modifier = Modifier
                .size(280.dp)
                .padding(bottom = 32.dp),
        )
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
                .padding(bottom = 16.dp),
            onClick = { navController.navigate(UnovsUno.ruta) },
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = Color.White
            ),
            shape = RoundedCornerShape(8.dp)
        ) {
            Text(text = "1 vs 1", fontSize = 18.sp, fontWeight = FontWeight.Bold)
        }

        Button(
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp),
            onClick = { navController.navigate(MuestraCarta.ruta) },
            colors = ButtonDefaults.buttonColors(
                contentColor = Color.White,
                containerColor = MaterialTheme.colorScheme.primary,
            ),
            shape = RoundedCornerShape(8.dp)
        ) {
            Text(text = "Muestra Carta", fontSize = 18.sp, fontWeight = FontWeight.Bold)
        }
    }
}

