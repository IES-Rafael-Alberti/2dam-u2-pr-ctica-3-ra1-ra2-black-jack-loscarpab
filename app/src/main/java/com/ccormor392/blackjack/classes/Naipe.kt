package com.ccormor392.blackjack.classes

/**
 * El nombre que puede tener el valor de una carta, en este caso, el de una baraja francesa
 */
enum class Naipe(val valor: Int) {
    AS(1),
    DOS(2),
    TRES(3),
    CUATRO(4),
    CINCO(5),
    SEIS(6),
    SIETE(7),
    OCHO(8),
    NUEVE(9),
    DIEZ(10),
    JOTA(11),
    REINA(12),
    REY(13)
}