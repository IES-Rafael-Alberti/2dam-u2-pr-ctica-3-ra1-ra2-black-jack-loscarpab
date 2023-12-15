package com.ccormor392.blackjack.cardgames.ui

import android.annotation.SuppressLint
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ccormor392.blackjack.cardgames.data.Baraja
import com.ccormor392.blackjack.cardgames.data.Carta
import com.ccormor392.blackjack.cardgames.data.Jugador

/**
 * Esta clase es el viewModel de modo de juego uno contra uno del Blackjack
 * @property context el contexto de la aplicacion
 * @property _jugador livedata privado que guarda la informacion del jugador 1
 * @property _puntosJugador livedata privado que guarda los puntos del jugador 1
 * @property puntosJugador livedata publico que observa el estado de _puntosJugador
 * @property _puedeJugarJugador livedata privado que tiene un booleano si puede o no jugar el jugador 1
 * @property _jugador2 livedata privado que guarda la informacion del jugador 2
 * @property _puntosJugador2 livedata privado que guarda los puntos del jugador 2
 * @property puntosJugador2 livedata publico que observa el estado de _puntosJugador2
 * @property _puedeJugarJugador2 livedata privado que tiene un booleano si puede o no jugar el jugador 2
 * @property _turnoJ1 livedata privado que determina si es o no el turno del jugador1
 * @property _jugadorActivo livedata privado que contiene los datos del jugador que se va a mostrar en pantalla
 * @property jugadorActivo livedata publico que observa el estado de _jugadorActivo
 * @property _puntosJugadorActivo livedata privado que contiene los puntos del jugador que se va a mostrar en pantalla
 * @property puntosJugadorActivo livedata publico que observa el estado de _puntosJugadorActivo
 * @property _puedePasarTurno livedata privado que indica si el jugadorActivo puede pasar turno o no
 * @property puedePasarTurno livedata publico que observa el estado de _puedePasarTurno
 * @property _mostrarIngresarNombres livedata privado que habilita o deshabilita el dialogo para meter el nombre de los jugadores
 * @property mostrarIngresarNombres livedata publico que observa el estado de _mostrarIngresarNombres
 * @property _nombreDialogoJ1 livedata privado que contiene el nombre introducido en el dialogo para el jugador 1
 * @property nombreDialogoJ1 livedata publico que observa el estado de _nombreDialogoJ1
 * @property _nombreDialogoJ2 livedata privado que contiene el nombre introducido en el dialogo para el jugador 2
 * @property nombreDialogoJ2 livedata publico que observa el estado de _nombreDialogoJ2
 * @property _mostrarPartidaFinalizada livedata privado que habilita o deshabilita el dialogo del final de la partida
 * @property mostrarPartidaFinalizada livedata publico que observa el estado de _mostrarPartidaFinalizada
 *
 */
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

    /**
     * Funcion que desactiva el dialogo de introducir los nombres, asigna los nombres a cada jugador y da dos cartas iniciales a cada jugador.
     * @return True si ninguno de los dos nombres esta vacío o false si alguno de los dos está vacio
     */
    fun aceptarDialogoNombres():Boolean{
        return if (nombreDialogoJ1.value!!.isNotBlank() && nombreDialogoJ2.value!!.isNotBlank()){
            nombresYaMetidos()
            cambiarNombres()
            darDosCartasIniciales()
            _puntosJugadorActivo.value = _puntosJugador.value
            comprobarSiJugadorActivoHaFinalizado()
            true
        } else false

    }

    /**
     * Funcion que da dos cartas a los dos jugadores
     */
    private fun darDosCartasIniciales() {
        for (i in 1..2) {
            _puntosJugador.value =
                pedirCarta(jugador = _jugador.value!!, puntos = _puntosJugador.value!!)
            _puntosJugador2.value =
                pedirCarta(jugador = _jugador2.value!!, puntos = _puntosJugador2.value!!)
        }
    }

    /**
     * funcion que pide carta y devuelve los puntos conseguidos con la nueva carta
     * Se usa solo para dar las dos cartas iniciales
     * @param jugador al que le añado la carta
     * @param puntos los puntos del jugador que coge la carta
     * @see darDosCartasIniciales
     */
    private fun pedirCarta(jugador: Jugador, puntos: Int): Int {
        val cartaCogida = Baraja.dameCarta()!!
        jugador.listaCartas.add(cartaCogida)
        return calcPoints(cartaCogida, puntos)
    }

    /**
     * funcion que añade una carta al jugador activo y le calcula los puntos
     * Solo se usa cuando un jugador pulsa el boton de pedir carta
     */
    fun pedirCarta() {
        val cartaCogida = Baraja.dameCarta()!!
        _jugadorActivo.value!!.listaCartas.add(cartaCogida)
        calcPoints(cartaCogida)
        comprobarSiJugadorActivoHaFinalizado()
    }

    /**
     * funcion que comprueba si el jugador activo puede volver a jugar o no, y si puede pasar turno o la partida ha finalizado
     */
    private fun comprobarSiJugadorActivoHaFinalizado(){
        if (puntosJugadorActivo.value!! >= 21){
            if (!_puedeJugarJugador.value!! || !_puedeJugarJugador2.value!!){
                if (_turnoJ1.value!!) _puntosJugador.value = _puntosJugadorActivo.value
                if (!_turnoJ1.value!!) _puntosJugador2.value = _puntosJugadorActivo.value
                _mostrarPartidaFinalizada.value = true
            }
            plantarse(false)
            _puedePasarTurno.value = true
        }
    }

    /**
     * Anula la posibilidad de que el jugador que este activo no pueda volver a jugar
     * @param pasarTurno si quieres pasar el turno o no
     */
    fun plantarse(pasarTurno:Boolean = true){
        if (_turnoJ1.value!!) _puedeJugarJugador.value = false

        if (!_turnoJ1.value!!) _puedeJugarJugador2.value = false

        if (pasarTurno) cambiarTurno()
    }


    /**
     * cambia de turno del jugador 1 y todos sus datos a jugador 2 o viceversa, el jugador que le toque el turno ocupa los datos de jugador activo
     */
    fun cambiarTurno() {
        //cambia el turno
        _turnoJ1.value = !_turnoJ1.value!!
        //una vez cambiado el turno no puedes pasar de turno hasta que pidas carta o te plantes
        _puedePasarTurno.value = false
        //si es turno del j1 le asigna a jugador activo todos los datos lo de jugador 1
        if (_turnoJ1.value!!) {
            _jugadorActivo.value = _jugador.value
            _puntosJugador2.value = _puntosJugadorActivo.value
            _puntosJugadorActivo.value = _puntosJugador.value
        }
        //si es turno del j2 le asigna a jugador activo todos los datos lo de jugador 2
        else {
            _jugadorActivo.value = _jugador2.value
            _puntosJugador.value = _puntosJugadorActivo.value
            _puntosJugadorActivo.value = _puntosJugador2.value
        }
        if (!_puedeJugarJugador.value!! && !_puedeJugarJugador2.value!!)
            _mostrarPartidaFinalizada.value  = true
    }

    /**
     * calcula los puntos del jugador activo respecto a la nueva carta
     * @param carta la nueva carta del jugador
     */
    private fun calcPoints(carta: Carta) {
        if (carta.nombre.valor == 1 && _puntosJugadorActivo.value!! + carta.puntosMax < 21)
            _puntosJugadorActivo.value = _puntosJugadorActivo.value!! + carta.puntosMax
        else _puntosJugadorActivo.value = _puntosJugadorActivo.value!! + carta.puntosMin
    }
    /**
     * calcula los puntos del jugador activo respecto a la nueva carta, se usa para las dos cartas iniciales
     * @param carta la nueva carta del jugador
     * @param puntos del jugador que recibe la carta
     * @return los nuevos puntos del jugador
     */
    private fun calcPoints(carta: Carta, puntos: Int): Int {
        return if (carta.nombre.valor == 1 && puntos + carta.puntosMax < 21)
            puntos + carta.puntosMax
        else puntos + carta.puntosMin
    }

    /**
     * asigna los nombres del dialogo al jugador
     */
    private fun cambiarNombres() {
        _jugador.value!!.nombre = _nombreDialogoJ1.value!!
        _jugador2.value!!.nombre = _nombreDialogoJ2.value!!
    }

    /**
     * actuliza el texto del nombre del jugador 1 del textField
     */
    fun cambiarNombreJ1(nuevoNombre: String) {
        _nombreDialogoJ1.value = nuevoNombre
    }
    /**
     * actuliza el texto del nombre del jugador 2 del textField
     */
    fun cambiarNombreJ2(nuevoNombre: String) {
        _nombreDialogoJ2.value = nuevoNombre
    }

    /**
     * una vez se pulsa aceptar y cumple la condicion se cierra el dialogo
     * @see aceptarDialogoNombres
     */
    fun nombresYaMetidos() {
        _mostrarIngresarNombres.value = false
    }

    /**
     * Estudia las distintas posibilidades para declarar el ganador segun las reglas del blackjack
     * @return el texto a mostrar en el dialogo de partida finalizada
     */
    fun resultadoPartida():String{
        if (_puntosJugador.value!! > 21 && _puntosJugador2.value!! > 21){
            return "Sois muy malos, habeis perdido los dos"
        }
        else if (_puntosJugador.value!! > 21 && _puntosJugador2.value!! < 21){
            return "${_jugador2.value!!.nombre} gana"
        }
        else if (_puntosJugador2.value!! > 21 && _puntosJugador.value!! < 21){
            return "${_jugador.value!!.nombre} gana"
        }
        else if (_puntosJugador.value!! > _puntosJugador2.value!!){
            return "${_jugador.value!!.nombre} gana"
        }
        else if (_puntosJugador2.value!! > _puntosJugador2.value!!){
            return "${_jugador2.value!!.nombre} gana"
        }
        else{
            return "Empate"
        }
    }

    /**
     * Inicializa todas las variables
     */
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