package com.example.mapas.modelo.conexion

import android.content.Context
import com.example.mapas.modelo.entidades.Rutas
import java.io.IOException
import java.io.ObjectInputStream
import java.io.ObjectOutputStream

class BDFichero (private val context: Context) {
    val nombre="listaDeRutas.dat"

    fun escribir(lista:MutableList<Rutas>,nombreArchivo: String=nombre) {
        try {
            val fileOutputStream = context.openFileOutput(nombreArchivo, Context.MODE_PRIVATE)
            val objectOutputStream = ObjectOutputStream(fileOutputStream)
            objectOutputStream.writeObject(lista)
            objectOutputStream.close()
            fileOutputStream.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
    fun leer():MutableList<Rutas> {
        var lista: MutableList<Rutas>? = null
        try {
            val fileInputStream = context.openFileInput(nombre)
            val objectInputStream = ObjectInputStream(fileInputStream)
            lista = objectInputStream.readObject() as MutableList<Rutas>
            objectInputStream.close()
            fileInputStream.close()
        } catch (e: IOException) {
            e.printStackTrace()
        } catch (e: ClassNotFoundException) {
            e.printStackTrace()
        }
        return lista ?: mutableListOf()
    }



    fun borrarArchivos() {
        val archivo = context.getFileStreamPath(nombre)
        if (archivo.exists()) {
            archivo.delete()
        }
    }
}