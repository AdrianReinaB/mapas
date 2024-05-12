package com.example.mapas

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.widget.FrameLayout
import androidx.core.app.ActivityCompat
import androidx.fragment.app.FragmentActivity
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.tasks.Task

class MapActivity : FragmentActivity(),  OnMapReadyCallback{
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

    lateinit var puntoActual:Location
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
    }
}