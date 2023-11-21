package com.ccormor392.blackjack.screens

import android.widget.Toast
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
import com.ccormor392.blackjack.R
import com.ccormor392.blackjack.classes.Baraja


@Preview(showBackground = true)
@Composable
fun MuestraCarta() {
    val context = LocalContext.current
    var nombreDrawable by rememberSaveable { mutableStateOf("reverso") }
    val baraja = Baraja
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
                id = context.resources.getIdentifier(
                    "carta$nombreDrawable",
                    "drawable",
                    context.packageName
                )
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
                val cartaMostrada = baraja.dameCarta()//pide carta a la baraja
                if (cartaMostrada == null) {
                    //si carta mostrada es null significa que la baraja se ha quedado sin cartas
                    nombreDrawable = "reverso"
                    Toast.makeText(context, "No quedan mas cartas", Toast.LENGTH_SHORT).show()
                }
                //si no es nula cambiamos la variable nombreDrawable con el id de la carta cogida de la baraja
                else nombreDrawable = cartaMostrada.idDrawable.toString()
            }, modifier = Modifier.padding(end = 10.dp)) {
                Text(text = "Dame una carta")
            }
            //boton para reiniciar la baraja
            Button(onClick = {
                //llamamos a baraja para que cree de nuevo otra baraja
                baraja.crearBaraja()
                //y cambiamos el nombreDrawable para que se muestre el reverso
                nombreDrawable = "reverso"
                Toast.makeText(context, "Baraja reiniciada!", Toast.LENGTH_SHORT).show()
            }) {
                Text(text = "Reiniciar")
            }
        }
    }
}

