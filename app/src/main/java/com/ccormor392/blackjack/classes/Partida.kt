package com.ccormor392.blackjack.classes

import java.io.Serializable

class Partida :Serializable {
    var jugador1 = Jugador("j1")
    var jugador2 = Jugador("j2")
    var turnoJugador1 = true
    fun cambiarTurno():Boolean{
        return !turnoJugador1
    }
}