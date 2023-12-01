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
import com.ccormor392.blackjack.classes.Partida

@Preview
@Composable
fun UnoVsUno() {
    val partida by rememberSaveable { mutableStateOf(Partida()) }
    var jugadorVisto by rememberSaveable { mutableStateOf(partida.jugador1) }
    var puntos by rememberSaveable { mutableStateOf("") }
    val context = LocalContext.current
    val baraja = Baraja

    TodaLaPantalla(jugador = jugadorVisto, context = context, puntos = puntos) {
        jugadorVisto.mano.cogerCarta(baraja.dameCarta()!!)
        puntos = if (jugadorVisto.mano.valorTotal == 21) "¡BlackJack!"
        else jugadorVisto.mano.valorTotal.toString()
        jugadorVisto = partida.jugador2
        println("pruebacommit")
    }

}

@Composable
fun TodaLaPantalla(jugador: Jugador, context: Context, puntos: String, onClickPedirCarta: () -> Unit){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .paint(painterResource(id = R.drawable.tapete), contentScale = ContentScale.FillHeight)
            .padding(vertical = 50.dp, horizontal = 20.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        PintarFilaNombreJugador(nombreJugador = jugador.nombre)
        PintarFilaCartasYPuntuacion(
            manoJugador = jugador.mano,
            puntos = puntos,
            context = context
        )
        PintarFilaBotonesPedirYPasar {
            onClickPedirCarta()
        }
    }
}
@Composable
fun PintarFilaNombreJugador(nombreJugador: String) {
    Row(horizontalArrangement = Arrangement.Center, modifier = Modifier.fillMaxWidth()) {
        TextoFilaNombre(string = "Turno de: ")
        TextoFilaNombre(
            string = nombreJugador.uppercase(),
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun TextoFilaNombre(string: String, fontWeight: FontWeight = FontWeight.Normal) {
    Text(
        text = string,
        fontSize = 30.sp,
        color = Color.White,
        fontWeight = fontWeight
    )
}

@Composable
fun PintarFilaCartasYPuntuacion(manoJugador: Mano, puntos: String, context: Context) {
    Row(
        Modifier
            .height(560.dp)
            .fillMaxWidth()
    ) {
        Column(Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
            PintarMano(mano = manoJugador, context = context)
            TextoFilaNombre(string = puntos, fontWeight = FontWeight.Bold)
        }

    }
}

@Composable
fun PintarMano(mano: Mano, context: Context) {
    var padding = 20
    Box(Modifier.padding(bottom = 20.dp), contentAlignment = Alignment.TopCenter) {
        for (carta in mano.listaCartas) {
            Box(Modifier.padding(top = padding.dp)) {
                PintarCarta(
                    context = context,
                    idDrawable = carta.idDrawable
                )
            }
            padding += 30
        }
    }
}

@Composable
fun PintarCarta(context: Context, idDrawable: Int) {
    Image(
        painter = painterResource(
            id = context.resources.getIdentifier(
                "carta$idDrawable",
                "drawable",
                context.packageName
            )
        ), contentDescription = "Carta vista", modifier = Modifier.size(250.dp)
    )
}

@Composable
fun PintarFilaBotonesPedirYPasar(onClickPedirCarta: () -> Unit) {
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
