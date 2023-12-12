package com.ccormor392.blackjack.cardgames.ui

import android.annotation.SuppressLint
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ccormor392.blackjack.cardgames.data.Baraja
import com.ccormor392.blackjack.cardgames.data.Carta
import com.ccormor392.blackjack.cardgames.data.Jugador

class UnovsunoViewModel(application: Application): AndroidViewModel(application) {

    @SuppressLint("StaticFieldLeak")
    val context = getApplication<Application>().applicationContext

    private val _jugador = MutableLiveData<Jugador>()
    private val _puntosJugador = MutableLiveData<Int>()
    private val _jugador2 = MutableLiveData<Jugador>()
    private val _puntosJugador2 = MutableLiveData<Int>()

    private val _jugadorActivo = MutableLiveData<Jugador>()
    val jugadorActivo: LiveData<Jugador> = _jugadorActivo

    private val _turnoJ1 = MutableLiveData<Boolean>()

    private val _puntosJugadorActivo = MutableLiveData<Int>()
    val puntosJugadorActivo: LiveData<Int> = _puntosJugadorActivo

    init {
        restart()
        Baraja.crearBaraja(context = context)
    }

    fun pedirCarta(){
        val mano = _jugadorActivo.value!!.mano
        val cartaCogida = Baraja.dameCarta()!!
        mano.listaCartas.add(cartaCogida)
        _jugadorActivo.value = _jugador.value!!.copy(mano = mano)
        calcPoints(cartaCogida)
    }
    fun cambiarTurno(){
        _turnoJ1.value = !_turnoJ1.value!!
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
    }
    private fun calcPoints(carta: Carta) {
        if (carta.nombre.valor == 1 && _puntosJugadorActivo.value!! + carta.puntosMin < 21)
           _puntosJugadorActivo.value = _puntosJugadorActivo.value!! + carta.puntosMax
        else _puntosJugadorActivo.value = _puntosJugadorActivo.value!! + carta.puntosMin
    }
    fun restart(){
        _jugador.value = Jugador("Pepe")
        _puntosJugador.value = 0
        _jugador2.value = Jugador("joselui")
        _puntosJugador2.value = 0
        _jugadorActivo.value = _jugador.value
        _turnoJ1.value = true
        _puntosJugadorActivo.value = 0
    }
}