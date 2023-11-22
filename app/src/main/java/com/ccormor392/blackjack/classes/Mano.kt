package com.ccormor392.blackjack.classes

import java.io.Serializable

class Mano():Serializable {
    var listaCartas = mutableListOf<Carta>()
    var valorTotal = 0

    fun cogerCarta(carta: Carta){
        listaCartas.add(carta)
        valorTotal+=carta.puntosMin
    }
    fun calcularValor():Int{
        var valor = 0
        if (listaCartas.isNotEmpty()){
            for (carta in listaCartas){
                valor += carta.puntosMin
            }
        }
        return valor
    }

    override fun toString(): String {
        return if (listaCartas.isNotEmpty()) "Mano(listaCartas=${listaCartas[0].toString()}, valorTotal=$valorTotal)"
        else "Mano(listaCartas=$listaCartas, valorTotal=$valorTotal)"
    }
}