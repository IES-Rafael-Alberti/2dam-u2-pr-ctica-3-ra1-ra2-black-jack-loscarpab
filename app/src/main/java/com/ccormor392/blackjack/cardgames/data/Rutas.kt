package com.ccormor392.blackjack.cardgames.data

sealed class Rutas(val ruta: String) {

    object UnovsUno : Rutas("UnovsUno")
    object MuestraCarta : Rutas("MuestraCarta")
    object MenuPrincipal : Rutas("MenuPrincipal")

}