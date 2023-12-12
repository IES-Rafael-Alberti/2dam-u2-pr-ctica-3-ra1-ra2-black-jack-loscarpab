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
    val jugador: LiveData<Jugador> = _jugador

    private val _puntos = MutableLiveData<Int>()
    val puntos: LiveData<Int> = _puntos

    init {
        restart()
        Baraja.crearBaraja(context = context)
    }

    fun pedirCarta(){
        val mano = _jugador.value!!.mano
        val cartaCogida = Baraja.dameCarta()!!
        mano.listaCartas.add(cartaCogida)
        _jugador.value = _jugador.value!!.copy(mano = mano)
        calcPoints(cartaCogida)
    }
    private fun calcPoints(carta: Carta) {
        if (carta.nombre.valor == 1 && _puntos.value!! + carta.puntosMin < 21)
           _puntos.value = _puntos.value!! + carta.puntosMax
        else _puntos.value = _puntos.value!! + carta.puntosMin
    }
    fun restart(){
        _jugador.value = Jugador("Pepe")
        _puntos.value = 0
    }
}