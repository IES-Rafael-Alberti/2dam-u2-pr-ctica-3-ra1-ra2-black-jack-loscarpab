package com.ccormor392.blackjack.cardgames.data

import java.io.Serializable

data class Mano(val listaCartas:MutableList<Carta> = mutableListOf<Carta>(), val valorTotal: Int = 0)