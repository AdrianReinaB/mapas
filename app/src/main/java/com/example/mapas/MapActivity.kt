package com.example.mapas

import android.Manifest
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.widget.FrameLayout
import android.widget.Toast
import androidx.appcompat.graphics.drawable.DrawerArrowDrawable.ArrowDirection
import androidx.appcompat.widget.SearchView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import com.google.android.gms.common.util.CollectionUtils.listOf
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.Dash
import com.google.android.gms.maps.model.Dot
import com.google.android.gms.maps.model.Gap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.PatternItem
import com.google.android.gms.maps.model.Polyline
import com.google.android.gms.maps.model.PolylineOptions
import com.google.android.gms.maps.model.RoundCap
import com.google.android.gms.tasks.Task
import java.io.IOException
import java.util.Locale

class MapActivity : FragmentActivity(), OnMapReadyCallback, GoogleMap.OnMapClickListener {
    //Mapa simple
    /*lateinit var gMap:GoogleMap
    lateinit var map:FrameLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)

        map=findViewById(R.id.map)

        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(p0: GoogleMap) {

        this.gMap = p0

        val sevilla = LatLng(37.3828300, -5.9731700)

        this.gMap.addMarker(MarkerOptions().position(sevilla).title("Está aquí"))
        this.gMap.moveCamera(CameraUpdateFactory.newLatLng(sevilla))



    }*/

    //Mapa con tu localizacion
    /*lateinit var puntoActual:Location
    lateinit var fusedClient:FusedLocationProviderClient
    companion object {
        private const val REQUEST_CODE = 101
    }

    lateinit var map:FrameLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)

        map=findViewById(R.id.map)

        fusedClient=LocationServices.getFusedLocationProviderClient(this)
        getLocation()
    }

    private fun getLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), REQUEST_CODE)
            return
        }

        val task: Task<Location> = fusedClient.lastLocation

        task.addOnSuccessListener { location ->
            if (location != null) {
                puntoActual = location
                val supportMapFragment = supportFragmentManager.findFragmentById(R.id.map) as? SupportMapFragment
                supportMapFragment?.getMapAsync(this@MapActivity)
            }
        }
    }


    override fun onMapReady(p0: GoogleMap) {

        var latLng = LatLng(puntoActual.latitude, puntoActual.longitude)
        var marcador: MarkerOptions=MarkerOptions().position(latLng).title("Estas aqui")
        p0.animateCamera(CameraUpdateFactory.newLatLng(latLng))
        p0.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10f))
        p0.addMarker(marcador)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode== REQUEST_CODE){
            if (grantResults.size>0&&grantResults[0]==PackageManager.PERMISSION_GRANTED){
                getLocation()
            }
        }
    }*/

    //Mapa personalizado
    /*lateinit var gMap:GoogleMap
    lateinit var map:FrameLayout
    private val marcadores = mutableListOf<Marker>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)

        map=findViewById(R.id.map)

        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(p0: GoogleMap) {

        gMap = p0

        val sevilla = LatLng(37.3828300, -5.9731700)

        val marker=MarkerOptions().position(sevilla).title("Estas aqui")

        /*
        gMap.addMarker(marker)
        gMap.moveCamera(CameraUpdateFactory.newLatLng(sevilla))
*/
        gMap.setOnMapClickListener(this)
        //gMap.setOnMarkerClickListener(this)

    }

    override fun onMapClick(pos: LatLng) {
        val geocoder = Geocoder(applicationContext, Locale.getDefault())

        //gMap.clear()
        var posicion=LatLng(pos.latitude, pos.longitude)
        gMap.animateCamera(CameraUpdateFactory.newLatLng(posicion))
        gMap.animateCamera(CameraUpdateFactory.newLatLngZoom(posicion, 5f))
        var direccion= geocoder.getFromLocation(pos.latitude, pos.longitude, 1) as MutableList<Address>
        var marcador: MarkerOptions=MarkerOptions().position(pos).title("${direccion.get(0).subAdminArea}")
        val marker = gMap.addMarker(marcador)
        if (marker != null) {
            marcadores.add(marker)
        }
    }

    override fun onMarkerClick(marker: Marker): Boolean {
        marker.remove()
        marcadores.remove(marker)
        return true
    }*/

    //Mapa de busqueda
    lateinit var puntoActual: Location
    lateinit var fusedClient: FusedLocationProviderClient
    var gMap: GoogleMap? = null

    companion object {
        private const val REQUEST_CODE = 101
    }

    var marker: Marker? = null
    lateinit var map: FrameLayout
    var buscador: SearchView? = null
    private val marcadores = mutableListOf<Marker>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)

        map = findViewById(R.id.map)
        buscador = findViewById(R.id.busqueda)
        buscador?.clearFocus()

        fusedClient = LocationServices.getFusedLocationProviderClient(this)
        getLocation()

        buscador?.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                val loc = buscador?.query.toString()
                if (loc.isEmpty()) {
                    Toast.makeText(
                        this@MapActivity,
                        "No disponible esa localizacion",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    val geocoder = Geocoder(this@MapActivity, Locale.getDefault())
                    try {
                        val addressList: List<Address> =
                            geocoder.getFromLocationName(loc, 1) ?: emptyList()
                        if (addressList.isNotEmpty()) {
                            val latLng = LatLng(addressList[0].latitude, addressList[0].longitude)
                            marker?.remove()
                            val markerOptions = MarkerOptions().position(latLng).title(loc)
                            markerOptions.icon(
                                BitmapDescriptorFactory.defaultMarker(
                                    BitmapDescriptorFactory.HUE_AZURE
                                )
                            )
                            val cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 5f)
                            gMap?.animateCamera(cameraUpdate)
                            marker = gMap?.addMarker(markerOptions)
                        }
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }
                }
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }
        })

    }

    private fun getLocation() {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                REQUEST_CODE
            )
            return
        }

        val task: Task<Location> = fusedClient.lastLocation

        task.addOnSuccessListener { location ->
            if (location != null) {
                puntoActual = location
                val supportMapFragment =
                    supportFragmentManager.findFragmentById(R.id.map) as? SupportMapFragment
                supportMapFragment?.getMapAsync(this@MapActivity)
            }
        }
    }


    override fun onMapReady(p0: GoogleMap) {
        gMap = p0
        var latLng = LatLng(puntoActual.latitude, puntoActual.longitude)
        if (latLng == null) {
            var marcador: MarkerOptions = MarkerOptions().position(latLng).title("Estas aqui")
            p0.animateCamera(CameraUpdateFactory.newLatLng(latLng))
            p0.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10f))
            p0.addMarker(marcador)
            marcadores.add(latLng)
        }
        //createPolylines()

        p0.setOnMapClickListener(this)
    }

    override fun onMapClick(pos: LatLng) {
        val geocoder = Geocoder(applicationContext, Locale.getDefault())
        //if(marcadores==null){
        var posicion = LatLng(pos.latitude, pos.longitude)
        Log.d("posiciones", posicion.latitude.toString()+" "+posicion.longitude.toString())
        if (posicion!=null){
            gMap?.animateCamera(CameraUpdateFactory.newLatLng(posicion))
            //gMap?.animateCamera(CameraUpdateFactory.newLatLngZoom(posicion, 5f))
            var direccion =
                geocoder.getFromLocation(pos.latitude, pos.longitude, 1) as MutableList<Address>
            var marcador: MarkerOptions =
                MarkerOptions().position(pos).title("${direccion.get(0).subAdminArea}")
            val marker = gMap?.addMarker(marcador)
            if (marker != null) {
                marcadores.add(marker)
            }
            Toast.makeText(
                this,
                posicion.latitude.toString() + " " + posicion.longitude.toString(),
                Toast.LENGTH_SHORT
            ).show()
        }else{
            Toast.makeText(this, "No encontrado la posicion", Toast.LENGTH_SHORT).show()
        }

        createPolylines(posicion)
        //}
    }


    private fun createPolylines(pos: LatLng) {
        val pattern: List<PatternItem> = listOf(
            Gap(10f),
            Dash(20f)
        )

        var lineas= mutableListOf<PolylineOptions>()

        val polyline = PolylineOptions()

        lineas.add(polyline.add(pos).color(ContextCompat.getColor(this, R.color.rojo))
            .width(5f)
            .pattern(pattern))
        for (linea:PolylineOptions in lineas){
            Log.d("linea", linea.toString())
        }

        val line = gMap?.addPolyline(polyline)
        line?.startCap = RoundCap()



    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE) {
            if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getLocation()
            }
        }
    }


}