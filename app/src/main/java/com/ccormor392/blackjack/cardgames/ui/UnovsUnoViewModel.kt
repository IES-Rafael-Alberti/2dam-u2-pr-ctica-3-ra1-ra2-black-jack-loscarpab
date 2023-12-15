package com.ccormor392.blackjack.cardgames.ui

import android.annotation.SuppressLint
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ccormor392.blackjack.cardgames.data.Baraja
import com.ccormor392.blackjack.cardgames.data.Carta
import com.ccormor392.blackjack.cardgames.data.Jugador

class UnovsUnoViewModel(application: Application) : AndroidViewModel(application) {

    @SuppressLint("StaticFieldLeak")
    val context = getApplication<Application>().applicationContext!!

    private val _jugador = MutableLiveData<Jugador>()
    private val _puntosJugador = MutableLiveData<Int>()
    val puntosJugador: LiveData<Int> = _puntosJugador
    private val _puedeJugarJugador = MutableLiveData<Boolean>()

    private val _jugador2 = MutableLiveData<Jugador>()
    private val _puntosJugador2 = MutableLiveData<Int>()
    val puntosJugador2: LiveData<Int> = _puntosJugador2
    private val _puedeJugarJugador2 = MutableLiveData<Boolean>()

    private val _turnoJ1 = MutableLiveData<Boolean>()

    private val _jugadorActivo = MutableLiveData<Jugador>()
    val jugadorActivo: LiveData<Jugador> = _jugadorActivo

    private val _puntosJugadorActivo = MutableLiveData<Int>()
    val puntosJugadorActivo: LiveData<Int> = _puntosJugadorActivo

    private val _puedePasarTurno = MutableLiveData<Boolean>()
    val puedePasarTurno: LiveData<Boolean> = _puedePasarTurno


    private val _mostrarIngresarNombres = MutableLiveData<Boolean>()
    val mostrarIngresarNombres: LiveData<Boolean> = _mostrarIngresarNombres

    private val _nombreDialogoJ1 = MutableLiveData<String>()
    val nombreDialogoJ1: LiveData<String> = _nombreDialogoJ1

    private val _nombreDialogoJ2 = MutableLiveData<String>()
    val nombreDialogoJ2: LiveData<String> = _nombreDialogoJ2

    private val _mostrarPartidaFinalizada = MutableLiveData<Boolean>()
    val mostrarPartidaFinalizada: LiveData<Boolean> = _mostrarPartidaFinalizada


    init {
        restart()
    }
    fun aceptarDialogoNombres():Boolean{
        if (nombreDialogoJ1.value!!.isNotBlank() && nombreDialogoJ2.value!!.isNotBlank()){
            nombresYaMetidos()
            cambiarNombres()
            darDosCartasIniciales()
            _puntosJugadorActivo.value = _puntosJugador.value
            comprobarSiPuedePasarTurno()
            return true
        }
        else return false

    }
    fun darDosCartasIniciales() {
        for (i in 1..2) {
            _puntosJugador.value =
                pedirCarta(jugador = _jugador.value!!, puntos = _puntosJugador.value!!)
            _puntosJugador2.value =
                pedirCarta(jugador = _jugador2.value!!, puntos = _puntosJugador2.value!!)
        }
    }

    fun pedirCarta() {
        val cartaCogida = Baraja.dameCarta()!!
        _jugadorActivo.value!!.listaCartas.add(cartaCogida)
        calcPoints(cartaCogida)
        comprobarSiPuedePasarTurno()
    }
    fun comprobarSiPuedePasarTurno(){
        if (puntosJugadorActivo.value!! == 21){
            if (!_puedeJugarJugador.value!! || !_puedeJugarJugador2.value!!){
                _mostrarPartidaFinalizada.value = true
            }
            plantarse(false)
            _puedePasarTurno.value = true
        }
        if (puntosJugadorActivo.value!! > 21){
            _puedePasarTurno.value = true
            if (_turnoJ1.value!!)_puedeJugarJugador.value = false
            else _puedeJugarJugador2.value = false
        }
    }
    fun plantarse(pasarTurno:Boolean = true){
        if (_turnoJ1.value!!) _puedeJugarJugador.value = false

        if (!_turnoJ1.value!!) _puedeJugarJugador2.value = false

        if (pasarTurno) cambiarTurno()
    }

    private fun pedirCarta(jugador: Jugador, puntos: Int): Int {
        val cartaCogida = Baraja.dameCarta()!!
        jugador.listaCartas.add(cartaCogida)
        return calcPoints(cartaCogida, puntos)
    }

    fun cambiarTurno() {
        _turnoJ1.value = !_turnoJ1.value!!
        _puedePasarTurno.value = false
        if (_turnoJ1.value!!) {
            _jugadorActivo.value = _jugador.value
            _puntosJugador2.value = _puntosJugadorActivo.value
            _puntosJugadorActivo.value = _puntosJugador.value
        } else {
            _jugadorActivo.value = _jugador2.value
            _puntosJugador.value = _puntosJugadorActivo.value
            _puntosJugadorActivo.value = _puntosJugador2.value
        }
        if (!_puedeJugarJugador.value!! && !_puedeJugarJugador2.value!!)
            _mostrarPartidaFinalizada.value  = true
    }


    private fun calcPoints(carta: Carta) {
        if (carta.nombre.valor == 1 && _puntosJugadorActivo.value!! + carta.puntosMax < 21)
            _puntosJugadorActivo.value = _puntosJugadorActivo.value!! + carta.puntosMax
        else _puntosJugadorActivo.value = _puntosJugadorActivo.value!! + carta.puntosMin
    }

    private fun calcPoints(carta: Carta, puntos: Int): Int {
        return if (carta.nombre.valor == 1 && puntos + carta.puntosMax < 21)
            puntos + carta.puntosMax
        else puntos + carta.puntosMin
    }

    fun cambiarNombres() {
        _jugador.value!!.nombre = _nombreDialogoJ1.value!!
        _jugador2.value!!.nombre = _nombreDialogoJ2.value!!
    }

    fun cambiarNombreJ1(nuevoNombre: String) {
        _nombreDialogoJ1.value = nuevoNombre
    }

    fun cambiarNombreJ2(nuevoNombre: String) {
        _nombreDialogoJ2.value = nuevoNombre
    }

    fun nombresYaMetidos() {
        _mostrarIngresarNombres.value = false
    }

    fun resultadoPartida():String{
        if (_puntosJugador.value!! > 21){
            return "${_jugador2.value!!.nombre} gana"
        }
        else if (_puntosJugador2.value!! > 21){
            return "${_jugador.value!!.nombre} gana"
        }
        else if (_puntosJugador.value!! > _puntosJugador2.value!!){
            return "${_jugador.value!!.nombre} gana"
        }
        else if (_puntosJugador2.value!! > _puntosJugador2.value!!){
            return "${_jugador2.value!!.nombre} gana"
        }
        else if (_puntosJugador.value!! > 21 && _puntosJugador2.value!! > 21){
            return "Sois muy malos, habeis perdido los dos"
        }
        else{
            return "Empate"
        }
    }

    fun restart() {
        _mostrarPartidaFinalizada.value = false
        _mostrarIngresarNombres.value = true
        _jugador.value = Jugador("Jugador 1")
        _puntosJugador.value = 0
        _nombreDialogoJ1.value = ""
        _puedeJugarJugador.value = true
        _jugador2.value = Jugador("Jugador 2")
        _puntosJugador2.value = 0
        _nombreDialogoJ2.value = ""
        _puedeJugarJugador2.value = true
        _jugadorActivo.value = _jugador.value
        _turnoJ1.value = true
        _puntosJugadorActivo.value = 0
        _puedePasarTurno.value = false
        Baraja.crearBaraja(context = context)
        _puntosJugadorActivo.value = _puntosJugador.value
    }
}