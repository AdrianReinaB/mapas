package com.example.mapas.modelo.daos

import android.util.Log
import com.example.mapas.modelo.conexion.BDFichero
import com.example.mapas.modelo.entidades.Rutas
import com.example.mapas.modelo.interfaces.InterfaceDao
import com.example.mapas.modelo.interfaces.InterfaceDaoRuta

class DaoRuta: InterfaceDaoRuta, InterfaceDao {
    lateinit var conexion: BDFichero
    override fun createConexion(conex: BDFichero) {
        conexion = conex as BDFichero
    }

    override fun nuevaRuta(ru: Rutas) {
        val lista=conexion.leer()
        lista.add(ru)
        conexion.escribir(lista)
    }

    override fun getRutas(): MutableList<Rutas> {
        return conexion.leer()
    }

    override fun actualizaRuta(ruAnt: Rutas, ruNueva: Rutas) {
        val lista = conexion.leer()
        var rutaEncontrada = lista.find { it.nombreRuta == ruAnt.nombreRuta }
        if (rutaEncontrada != null) {
            rutaEncontrada.nombreRuta = ruNueva.nombreRuta// Actualizar el nombre de la categoría encontrada

            conexion.escribir(lista)
        } else {
            Log.d("error", "La categoría ${ruAnt.nombreRuta} no existe")
        }
    }

    override fun borraRuta(ru: Rutas) {
        val lista = conexion.leer()
        val rutaEncontrada = lista.find { it.nombreRuta == ru.nombreRuta }
        if (rutaEncontrada != null) {
            lista.remove(rutaEncontrada)
            conexion.escribir(lista)

        } else {
            Log.d("error", "La categoría ${ru.nombreRuta} no existe")
        }
    }
}