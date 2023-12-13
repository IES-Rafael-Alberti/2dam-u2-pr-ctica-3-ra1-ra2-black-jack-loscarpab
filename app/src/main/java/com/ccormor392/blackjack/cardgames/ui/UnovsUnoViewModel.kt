package com.ccormor392.blackjack.cardgames.ui

import android.annotation.SuppressLint
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ccormor392.blackjack.cardgames.data.Baraja
import com.ccormor392.blackjack.cardgames.data.Carta
import com.ccormor392.blackjack.cardgames.data.Jugador

class UnovsUnoViewModel(application: Application): AndroidViewModel(application) {

    @SuppressLint("StaticFieldLeak")
    val context = getApplication<Application>().applicationContext!!

    private val _jugador = MutableLiveData<Jugador>()
    val jugador: LiveData<Jugador> = _jugador
    private val _puntosJugador = MutableLiveData<Int>()

    private val _jugador2 = MutableLiveData<Jugador>()
    val jugador2: LiveData<Jugador> = _jugador2
    private val _puntosJugador2 = MutableLiveData<Int>()

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
    val nombreDialogoJ1:LiveData<String> = _nombreDialogoJ1

    private val _nombreDialogoJ2 = MutableLiveData<String>()
    val nombreDialogoJ2:LiveData<String> = _nombreDialogoJ2


    init {
        restart()
    }
    fun darDosCartasIniciales(){
        for (i in 0..1){
            _puntosJugador.value = pedirCarta(jugador = _jugador.value!!, puntos = _puntosJugador.value!!)
            _puntosJugador2.value = pedirCarta(jugador = _jugador2.value!!, _puntosJugador2.value!!)
        }
    }
    fun pedirCarta(){
        val cartaCogida = Baraja.dameCarta()!!
        _jugadorActivo.value!!.listaCartas.add(cartaCogida)
        calcPoints(cartaCogida)
        _puedePasarTurno.value = true
    }
    private fun pedirCarta(jugador: Jugador, puntos:Int):Int{
        val cartaCogida = Baraja.dameCarta()!!
        jugador.listaCartas.add(cartaCogida)
        return calcPoints(cartaCogida, puntos)
    }
    fun cambiarTurno(){
        _turnoJ1.value = !_turnoJ1.value!!
        _puedePasarTurno.value = false
        if (_turnoJ1.value!!){
            _jugadorActivo.value = _jugador.value
            _puntosJugador2.value = _puntosJugadorActivo.value
            _puntosJugadorActivo.value = _puntosJugador.value
        }
        else{
            _jugadorActivo.value = _jugador2.value
            _puntosJugador.value = _puntosJugadorActivo.value
            _puntosJugadorActivo.value = _puntosJugador2.value
        }
        if (puntosJugadorActivo.value!! >= 21){
            _puedePasarTurno.value = true
        }
    }
    private fun calcPoints(carta: Carta) {
        if (carta.nombre.valor == 1 && _puntosJugadorActivo.value!! + carta.puntosMax < 21)
           _puntosJugadorActivo.value = _puntosJugadorActivo.value!! + carta.puntosMax
        else _puntosJugadorActivo.value = _puntosJugadorActivo.value!! + carta.puntosMin
    }
    private fun calcPoints(carta: Carta, puntos: Int):Int {
        return if (carta.nombre.valor == 1 && puntos + carta.puntosMax < 21)
            puntos + carta.puntosMax
        else puntos + carta.puntosMin
    }
    fun cambiarNombres(){
        _jugador.value!!.nombre = _nombreDialogoJ1.value!!
        _jugador2.value!!.nombre = _nombreDialogoJ2.value!!
    }
    fun cambiarNombreJ1(nuevoNombre:String){
        _nombreDialogoJ1.value = nuevoNombre
    }
    fun cambiarNombreJ2(nuevoNombre:String){
        _nombreDialogoJ2.value = nuevoNombre
    }
    fun nombresYaMetidos(){
        _mostrarIngresarNombres.value = false
    }
    fun restart(){
        _mostrarIngresarNombres.value = true
        _jugador.value = Jugador("Jugador 1")
        _puntosJugador.value = 0
        _nombreDialogoJ1.value = ""
        _jugador2.value = Jugador("Jugador 2")
        _puntosJugador2.value = 0
        _nombreDialogoJ2.value = ""
        _jugadorActivo.value = _jugador.value
        _turnoJ1.value = true
        _puntosJugadorActivo.value = 0
        _puedePasarTurno.value = false
        Baraja.crearBaraja(context = context)
        darDosCartasIniciales()
        _puntosJugadorActivo.value = _puntosJugador.value
    }
}