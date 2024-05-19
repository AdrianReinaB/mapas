package com.example.mapas.modelo.interfaces

import com.example.mapas.modelo.conexion.BDFichero

interface InterfaceDao {
    fun createConexion(conex: BDFichero)
}