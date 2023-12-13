package com.ccormor392.blackjack.cardgames.ui

import androidx.activity.compose.BackHandler
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
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
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
import com.ccormor392.blackjack.cardgames.data.Carta


@Composable
fun UnoVsUno(viewModel: UnovsUnoViewModel, navController: NavHostController) {
    val jugador by viewModel.jugadorActivo.observeAsState()
    val puntos by viewModel.puntosJugadorActivo.observeAsState()
    val puedePasarTurno by viewModel.puedePasarTurno.observeAsState()
    val mostrarDialogoNombres by viewModel.mostrarIngresarNombres.observeAsState()

    BackHandler {
        viewModel.restart()
        navController.popBackStack()
    }

    if (mostrarDialogoNombres!!) {
        DialogoNombreJugadores(onNamesEntered = {
            viewModel.nombresYaMetidos()
            viewModel.cambiarNombres()
        }, viewModel = viewModel)
    }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .paint(painterResource(id = R.drawable.tapete), contentScale = ContentScale.FillHeight)
            .padding(vertical = 50.dp, horizontal = 20.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        PintarFilaNombreJugador(nombreJugador = jugador!!.nombre)
        PintarFilaCartasYPuntuacion(
            manoJugador = jugador!!.listaCartas,
            puntos = puntos!!,
        )
        PintarFilaBotonesPedirYPasar(
            onClickPedirCarta = { viewModel.pedirCarta() },
            onClickPasarTurno = { viewModel.cambiarTurno() },
            puedePasarTurno!!
        )
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
fun PintarFilaCartasYPuntuacion(manoJugador: MutableList<Carta>, puntos: Int) {
    Row(
        Modifier
            .height(560.dp)
            .fillMaxWidth()
    ) {
        Column(Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
            PintarMano(mano = manoJugador)
            TextoFilaNombre(string = puntos.toString(), fontWeight = FontWeight.Bold)
        }

    }
}

@Composable
fun PintarMano(mano: MutableList<Carta>) {
    var padding = 20
    Box(Modifier.padding(bottom = 20.dp), contentAlignment = Alignment.TopCenter) {
        for (carta in mano) {
            Box(Modifier.padding(top = padding.dp)) {
                PintarCarta(
                    idDrawable = carta.idDrawable
                )
            }
            padding += 30
        }
    }
}

@Composable
fun PintarCarta(idDrawable: Int) {
    Image(
        painter = painterResource(
            id = idDrawable
        ), contentDescription = "Carta vista", modifier = Modifier.size(250.dp)
    )
}

@Composable
fun PintarFilaBotonesPedirYPasar(
    onClickPedirCarta: () -> Unit,
    onClickPasarTurno: () -> Unit,
    puedePasarTurno: Boolean
) {

    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
        Button(onClick = {
            onClickPedirCarta()
        }, enabled = !puedePasarTurno) {
            Text(text = "Pedir carta")
        }
        Button(onClick = { /*TODO*/ }, enabled = !puedePasarTurno) {
            Text(text = "Plantarme")
        }
        Button(onClick = { onClickPasarTurno() }, enabled = puedePasarTurno) {
            Text(text = "Pasar Turno")
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DialogoNombreJugadores(onNamesEntered: () -> Unit, viewModel: UnovsUnoViewModel) {
    // Observar los cambios en los nombres de los jugadores desde el ViewModel
    val nombreJ1 by viewModel.nombreDialogoJ1.observeAsState("")
    val nombreJ2 by viewModel.nombreDialogoJ2.observeAsState("")

    AlertDialog(
        onDismissRequest = { },
        title = { Text("Ingrese los nombres de los jugadores") },
        text = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(320.dp)
                    .padding(16.dp),
                verticalArrangement = Arrangement.SpaceEvenly
            ) {
                // Campo de texto para el nombre del Jugador 1
                OutlinedTextField(
                    value = nombreJ1,
                    onValueChange = { viewModel.cambiarNombreJ1(it) },
                    label = { Text("Nombre del Jugador 1") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp)
                )

                // Campo de texto para el nombre del Jugador 2
                OutlinedTextField(
                    value = nombreJ2,
                    onValueChange = { viewModel.cambiarNombreJ2(it) },
                    label = { Text("Nombre del Jugador 2") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp)
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    onNamesEntered()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
            ) {
                Text("Aceptar")
            }
        })

}


