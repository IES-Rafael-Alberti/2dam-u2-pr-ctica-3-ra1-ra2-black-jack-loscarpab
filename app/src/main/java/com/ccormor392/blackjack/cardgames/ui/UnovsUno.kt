package com.ccormor392.blackjack.cardgames.ui

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.ccormor392.blackjack.R
import com.ccormor392.blackjack.cardgames.data.Carta
import com.ccormor392.blackjack.cardgames.data.Rutas.*


@Composable
fun UnoVsUno(unoVsUnoViewModel: UnovsUnoViewModel, navController: NavHostController) {
    val jugador by unoVsUnoViewModel.jugadorActivo.observeAsState()
    val puntos by unoVsUnoViewModel.puntosJugadorActivo.observeAsState()
    val puedePasarTurno by unoVsUnoViewModel.puedePasarTurno.observeAsState()
    val mostrarDialogoNombres by unoVsUnoViewModel.mostrarIngresarNombres.observeAsState()
    val mostrarDialogoFinPartida by unoVsUnoViewModel.mostrarPartidaFinalizada.observeAsState()

    BackHandler {
        unoVsUnoViewModel.restart()
        navController.popBackStack()
    }

    if (mostrarDialogoFinPartida!!){
        DialogoFinPartida(
            ganador = unoVsUnoViewModel.resultadoPartida(),
            puntosJugador1 = unoVsUnoViewModel.puntosJugador.value!!,
            puntosJugador2 = unoVsUnoViewModel.puntosJugador2.value!!,
            onVolverInicioClick = { navController.navigate(MenuPrincipal.ruta) },
            onNuevaPartidaClick = {unoVsUnoViewModel.restart()})
    }

    if (mostrarDialogoNombres!!) {
        DialogoNombreJugadores(onNamesEntered = {
            unoVsUnoViewModel.aceptarDialogoNombres()
        }, viewModel = unoVsUnoViewModel)
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
            onClickPedirCarta = { unoVsUnoViewModel.pedirCarta() },
            onClickPasarTurno = { unoVsUnoViewModel.cambiarTurno() },
            puedePasarTurno!!, viewModel = unoVsUnoViewModel
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
    puedePasarTurno: Boolean,
    viewModel: UnovsUnoViewModel
) {

    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
        Button(onClick = {
            onClickPedirCarta()
        }, enabled = !puedePasarTurno) {
            Text(text = "Pedir carta")
        }
        Button(onClick = { viewModel.plantarse() }, enabled = !puedePasarTurno) {
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
@Composable
fun DialogoFinPartida(
    ganador: String,
    puntosJugador1: Int,
    puntosJugador2: Int,
    onVolverInicioClick: () -> Unit,
    onNuevaPartidaClick: () -> Unit
) {
    AlertDialog(
        onDismissRequest = { },
        title = {
            Text(
                text = "Partida Finalizada",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
        },
        text = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalArrangement = Arrangement.SpaceEvenly
            ) {
                Text(
                    text = ganador,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Puntos Jugador 1: $puntosJugador1",
                    fontSize = 16.sp,
                    textAlign = TextAlign.Center
                )
                Text(
                    text = "Puntos Jugador 2: $puntosJugador2",
                    fontSize = 16.sp,
                    textAlign = TextAlign.Center
                )
            }
        },
        confirmButton = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Button(
                    onClick = {
                        onVolverInicioClick()
                    },
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = 8.dp)
                ) {
                    Text("Volver a Inicio")
                }
                Button(
                    onClick = {
                        onNuevaPartidaClick()
                    },
                    modifier = Modifier
                        .weight(1f)
                ) {
                    Text("Nueva Partida")
                }
            }
        }
    )
}



