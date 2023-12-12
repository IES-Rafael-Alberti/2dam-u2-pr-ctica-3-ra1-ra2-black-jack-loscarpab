package com.ccormor392.blackjack.cardgames.data

import java.io.Serializable

/**
 * Contiene el nombre y el palo de una carta
 * @param palo el palo al que pertenece la carta
 * @param nombre el nombre de la carta
 * @property puntosMax los puntos maximos que puede sumar en el juego Blackjack
 * @property puntosMin los puntos minimos que puede sumar en el juego Blackjack
 * @property idDrawable el ID que le corresponde a la carta segun su palo y nombre para mostrarse en pantalla
 * @constructor Necesitas saber el palo y el nombre para crear la carta. Cuando se crean se asignan automaticamente segun el palo y el nombre, los puntos que puede hacer y el idDrawable que le corresponde
 * @see Palo
 * @see Naipe
 */
data class Carta(var palo: Palo, var nombre: Naipe, var idDrawable:Int) {
    var puntosMin:Int
    var puntosMax:Int

    init {
        //si es un As sus valores puedes ser 11 y 1
        if (nombre.valor == 1){
            puntosMax = 11
            puntosMin = 1
        }
        //si es una figura solo puntua 10
        else if (nombre.valor > 10){
            puntosMax = 10
            puntosMin = 10
        }
        //los los puntos de los demas son su numero
        else{
            puntosMax = nombre.valor
            puntosMin = nombre.valor
        }
    }

}