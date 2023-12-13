package com.ccormor392.blackjack.cardgames.data

import java.io.Serializable

data class Jugador(var nombre:String, val listaCartas:MutableList<Carta> = mutableListOf<Carta>())