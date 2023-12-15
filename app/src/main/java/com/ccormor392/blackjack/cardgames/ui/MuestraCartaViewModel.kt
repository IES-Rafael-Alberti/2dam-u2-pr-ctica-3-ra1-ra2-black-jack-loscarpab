package com.ccormor392.blackjack.cardgames.ui

import android.annotation.SuppressLint
import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ccormor392.blackjack.R
import com.ccormor392.blackjack.cardgames.data.Baraja

/**
 * El viewModel de la pantalla MuestraCarta
 * @property context el contexto de la aplicacion
 * @property _idDrawable livedata privado usado para pintar la carta
 * @property idDrawable livedata publico que observa el estado de _idDrawable
 */
class MuestraCartaViewModel(application: Application) : AndroidViewModel(application) {

    @SuppressLint("StaticFieldLeak")
    val context = getApplication<Application>().applicationContext!!
    private val _idDrawable = MutableLiveData<Int>()
    val idDrawable: LiveData<Int> = _idDrawable

    init {
        restart()
    }

    /**
     * inicializa las variables
     */
    fun restart(){
        _idDrawable.value = R.drawable.cartareverso
        Baraja.crearBaraja(context)
    }

    /**
     * Funcion usada para coger y mostrar una carta de la baraja
     */
    fun dameCarta(){
        val cartaMostrada = Baraja.dameCarta()//pide carta a la baraja
        if (cartaMostrada == null) {
            //si carta mostrada es null significa que la baraja se ha quedado sin cartas
            _idDrawable.value = R.drawable.cartareverso
            Toast.makeText(context, "No quedan mas cartas", Toast.LENGTH_SHORT).show()
        }
        //si no es nula cambiamos la variable nombreDrawable con el id de la carta cogida de la baraja
        else _idDrawable.value = cartaMostrada.idDrawable
    }
}