package com.example.mapas.modelo.entidades

import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import java.io.Serializable

class Rutas (var nombreRuta: String, var kilometros: Double, var inicioRuta: String, var finRuta: String): Serializable{

}