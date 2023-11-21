package com.ccormor392.blackjack.classes

/**
 * Una clase que usa un companion object para que se pueda llamar a sus metodos sin crear un objeto.
 * Tiene un unico atributo el cual es una lista de Cartas
 * @property listaBaraja Array list con onjetos de tipo Carta
 * @see Carta
 * @constructor Cuando se inicia se crea la baraja
 */
class Baraja {
    companion object{
        private var listaBaraja = arrayListOf<Carta>()
        init {
            crearBaraja()
        }

        /**
         * Primero borra la lista de barajas para facilitar el reinicio de la baraja y despues recorriendo los valores de los enum Palo y Naipe se van creando las cartas. Cuando añade todas las cartas las baraja
         * @see Palo
         * @see Naipe
         */
        fun crearBaraja(){
            listaBaraja.clear()
            for (paloIterator in Palo.values()){
                for (numeroIterator in Naipe.values()){
                    val carta = Carta(paloIterator, numeroIterator)
                    listaBaraja.add(carta)
                }
            }
            barajar()
        }

        /**
         * Desordena el array list de Cartas
         */
        private fun barajar(){
            listaBaraja.shuffle()
        }

        /**
         * Si queda cartas en la baraja te la devuelve
         * @return objeto tipo Carta si quedan, si no, null
         */
        fun dameCarta():Carta?{
            if (listaBaraja.size > 0) {
                val ultimo = listaBaraja.last()
                listaBaraja.remove(ultimo)
                return ultimo
            }
            return null
        }
    }
}