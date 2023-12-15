package com.ccormor392.blackjack.cardgames.ui

import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.ccormor392.blackjack.R
import com.ccormor392.blackjack.cardgames.data.Baraja



@Composable
fun MuestraCarta(navController: NavHostController, muestraCartaViewModel: MuestraCartaViewModel) {
    val nombreDrawable by muestraCartaViewModel.nombreDrawable.observeAsState()

    BackHandler {
        navController.popBackStack()
    }

    //columna que actua como fondo de la pantalla, en este caso con una imagen
    Column(
        modifier = Modifier
            .fillMaxSize()
            //esto sirve para poner una imagen como fondo
            .paint(painterResource(id = R.drawable.tapete), contentScale = ContentScale.FillHeight),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally

    ) {
        //carta mostrada en pantalla
        Image(
            painter = painterResource(
                id = nombreDrawable!!
            ), contentDescription = "Carta vista", modifier = Modifier.size(400.dp)
        )
        //linea de botones
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp), horizontalArrangement = Arrangement.Center
        ) {
            //Boton para pedir carta
            Button(onClick = {
               muestraCartaViewModel.dameCarta()
            }, modifier = Modifier.padding(end = 10.dp)) {
                Text(text = "Dame una carta")
            }
            //boton para reiniciar la baraja
            Button(onClick = {
                muestraCartaViewModel.restart()
            }) {
                Text(text = "Reiniciar")
            }
        }
    }
}

