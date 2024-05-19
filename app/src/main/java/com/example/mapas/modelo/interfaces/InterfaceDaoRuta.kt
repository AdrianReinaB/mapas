package com.example.mapas.modelo.interfaces

import com.example.mapas.modelo.entidades.Rutas

interface InterfaceDaoRuta {

    fun nuevaRuta(ru: Rutas)

    fun getRutas(): MutableList<Rutas>

    fun actualizaRuta(ruAnt: Rutas, ruNueva: Rutas)

    fun borraRuta(ru: Rutas)
}