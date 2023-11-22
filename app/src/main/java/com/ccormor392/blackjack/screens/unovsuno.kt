package com.ccormor392.blackjack.screens

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ccormor392.blackjack.R
import com.ccormor392.blackjack.classes.Baraja
import com.ccormor392.blackjack.classes.Jugador
import com.ccormor392.blackjack.classes.Mano

@Preview
@Composable
fun UnoVsUno() {
    val jugador by rememberSaveable { mutableStateOf(Jugador("pepe")) }
    var puntos by rememberSaveable { mutableStateOf("") }
    val context = LocalContext.current
    val baraja = Baraja

    Column(
        modifier = Modifier
            .fillMaxSize()
            .paint(painterResource(id = R.drawable.tapete), contentScale = ContentScale.FillHeight)
            .padding(vertical = 50.dp, horizontal = 20.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        PintarFilaNombreJugador(jugador = jugador)
        PintarFilaCartasYPuntuacion(jugador = jugador, puntos = puntos, context = context)
        PintarFilaBotonesPedirYPasar {
            jugador.mano.cogerCarta(baraja.dameCarta()!!)
            puntos = jugador.mano.calcularValor().toString()
        }

    }
}


@Composable
fun PintarFilaNombreJugador(jugador: Jugador){
    Row(horizontalArrangement = Arrangement.Center, modifier = Modifier.fillMaxWidth()) {
        Text(
            text = jugador.nombre.uppercase(),
            fontSize = 30.sp,
            color = Color.White,
            fontWeight = FontWeight.Bold
        )
    }
}
@Composable
fun PintarFilaCartasYPuntuacion(jugador: Jugador, puntos:String,context:Context){
    Row(
        Modifier
            .height(500.dp)
            .fillMaxWidth()
    ) {
        Column(Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = puntos,
                fontSize = 30.sp,
                color = Color.White,
                fontWeight = FontWeight.Bold
            )
            PintarMano(mano = jugador.mano, context = context)
        }

    }
}
@Composable
fun PintarMano(mano: Mano, context: Context) {
    var padding = 50
    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.BottomCenter) {
        for (cartas in mano.listaCartas) {
            Box(Modifier.padding(bottom = padding.dp)) {
                Image(
                    painter = painterResource(
                        id = context.resources.getIdentifier(
                            "carta${cartas.idDrawable}",
                            "drawable",
                            context.packageName
                        )
                    ), contentDescription = "Carta vista", modifier = Modifier.size(350.dp)
                )
            }
            padding += 30
        }
    }
}
@Composable
fun PintarFilaBotonesPedirYPasar(onClickPedirCarta:()->Unit){
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
        Button(onClick = {
            onClickPedirCarta()
        }) {
            Text(text = "Pedir carta")
        }
        Button(onClick = { /*TODO*/ }) {
            Text(text = "Plantarme")
        }
    }
}
