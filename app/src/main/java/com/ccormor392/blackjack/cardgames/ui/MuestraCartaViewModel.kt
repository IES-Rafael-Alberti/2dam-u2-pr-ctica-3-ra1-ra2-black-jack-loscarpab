package com.ccormor392.blackjack.cardgames.ui

import android.annotation.SuppressLint
import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ccormor392.blackjack.R
import com.ccormor392.blackjack.cardgames.data.Baraja

class MuestraCartaViewModel(application: Application) : AndroidViewModel(application) {

    @SuppressLint("StaticFieldLeak")
    val context = getApplication<Application>().applicationContext!!
    private val _nombreDrawable = MutableLiveData<Int>()
    val nombreDrawable: LiveData<Int> = _nombreDrawable

    init {
        restart()
    }
    fun restart(){
        _nombreDrawable.value = R.drawable.cartareverso
        Baraja.crearBaraja(context)
    }
    fun dameCarta(){
        val cartaMostrada = Baraja.dameCarta()//pide carta a la baraja
        if (cartaMostrada == null) {
            //si carta mostrada es null significa que la baraja se ha quedado sin cartas
            _nombreDrawable.value = R.drawable.cartareverso
            Toast.makeText(context, "No quedan mas cartas", Toast.LENGTH_SHORT).show()
        }
        //si no es nula cambiamos la variable nombreDrawable con el id de la carta cogida de la baraja
        else _nombreDrawable.value = cartaMostrada.idDrawable
    }
}