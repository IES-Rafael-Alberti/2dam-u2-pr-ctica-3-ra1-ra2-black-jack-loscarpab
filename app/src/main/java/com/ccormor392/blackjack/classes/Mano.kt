package com.ccormor392.blackjack.classes

import java.io.Serializable

class Mano():Serializable {
    var listaCartas = mutableListOf<Carta>()
    var valorTotal = 0

    fun cogerCarta(carta: Carta){
        listaCartas.add(carta)
        calcularValor(carta)
    }
    fun calcularValor(carta: Carta){
        valorTotal += carta.puntosMin
    }

    override fun toString(): String {
        return if (listaCartas.isNotEmpty()) "Mano(listaCartas=${listaCartas[0].toString()}, valorTotal=$valorTotal)"
        else "Mano(listaCartas=$listaCartas, valorTotal=$valorTotal)"
    }
}