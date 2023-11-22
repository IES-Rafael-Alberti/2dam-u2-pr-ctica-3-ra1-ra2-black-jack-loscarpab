package com.ccormor392.blackjack.classes

import java.io.Serializable

class Jugador(val nombre:String):Serializable {
    val mano = Mano()
    var fichas = 500
    val puedoPlantar = false

}