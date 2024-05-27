package com.example.mapas

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.graphics.drawable.DrawerArrowDrawable.ArrowDirection
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.FragmentActivity
import com.example.mapas.modelo.entidades.Rutas
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
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationView
import com.google.maps.android.SphericalUtil
import java.io.IOException
import java.text.DecimalFormat
import java.util.Locale


class MapActivity : AppCompatActivity(), OnMapReadyCallback, GoogleMap.OnMapClickListener,
    NavigationView.OnNavigationItemSelectedListener {
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
    private var marcadores = mutableListOf<Marker>()
    private var posiciones = mutableListOf<LatLng>()
    private var polilinea: Polyline? = null
    private var rutas = mutableListOf<Rutas>()
    private var kilometros = mutableListOf<Double>()
    private lateinit var resultado: TextView

    private lateinit var drawer: DrawerLayout
    private lateinit var toggle: ActionBarDrawerToggle
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)

        //Navigation Drawer
        drawer = findViewById(R.id.drawer_layout)
        val navigationView: NavigationView = findViewById(R.id.nav_view)
        navigationView.setNavigationItemSelectedListener(this)
        val headerView = navigationView.getHeaderView(0)
        resultado = headerView.findViewById(R.id.res)

        //toolbar
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.title = ""
        toggle = ActionBarDrawerToggle(
            this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close
        )
        drawer.addDrawerListener(toggle)
        toggle.syncState()

        //Botones flotantes
        val fab: FloatingActionButton = findViewById(R.id.fab)
        fab.setOnClickListener {
            marcadores.clear()
            posiciones.clear()
            gMap?.clear()
            onMapReady(gMap!!)
        }

        val fab1: FloatingActionButton = findViewById(R.id.fab1)
        fab1.setOnClickListener {
            if (posiciones.size >= 2) {
                val builder = AlertDialog.Builder(this)
                builder.setTitle("Introduce el nombre de la ruta")

                val inflater = LayoutInflater.from(this)
                val dialogView = inflater.inflate(R.layout.dialog, null)
                builder.setView(dialogView)

                val nombreRuta = dialogView.findViewById<EditText>(R.id.nombreRuta)

                builder.setPositiveButton("Aceptar") { dialog, which ->
                    guardarRutas(nombreRuta.text.toString())
                    kilometros.clear()
                }

                builder.setNegativeButton("Cancelar") { dialog, which ->
                }

                val dialog: AlertDialog = builder.create()
                dialog.show()
                true
            } else {
                Toast.makeText(
                    this,
                    "No hay suficientes marcadores para guardar una ruta",
                    Toast.LENGTH_SHORT
                ).show()
            }

        }

        //Mapa
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
                                    BitmapDescriptorFactory.HUE_CYAN
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
        var marcador: MarkerOptions = MarkerOptions().position(latLng).title("Estas aqui")
        p0.animateCamera(CameraUpdateFactory.newLatLng(latLng))
        p0.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10f))
        p0.addMarker(marcador)
        p0.setOnMapClickListener(this)
    }

    override fun onMapClick(pos: LatLng) {
        val pattern: List<PatternItem> = listOf(
            Gap(10f),
            Dash(20f)
        )
        val geocoder = Geocoder(applicationContext, Locale.getDefault())
        val direccion =
            geocoder.getFromLocation(pos.latitude, pos.longitude, 1) as MutableList<Address>
        if (marcadores.size == 0) {
            val marcador = MarkerOptions().position(pos).title(direccion[0].subAdminArea).icon(
                BitmapDescriptorFactory.defaultMarker(
                    BitmapDescriptorFactory.HUE_AZURE
                )
            )

            val marker = gMap?.addMarker(marcador)
            if (marker != null) {
                marcadores.add(marker)
                posiciones.add(pos)
                if (polilinea != null) {
                    polilinea?.remove()
                }
                polilinea = gMap?.addPolyline(
                    PolylineOptions().addAll(posiciones)
                        .color(ContextCompat.getColor(this, R.color.rojo)).width(5f)
                        .startCap(RoundCap()).endCap(RoundCap()).pattern(pattern)
                )
            }
        } else {
            val marcador = MarkerOptions().position(pos).title(direccion[0].subAdminArea).icon(
                BitmapDescriptorFactory.defaultMarker(
                    BitmapDescriptorFactory.HUE_RED
                )
            )
            val marker = gMap?.addMarker(marcador)
            if (marker != null) {
                marcadores.add(marker)
                posiciones.add(pos)
                if (polilinea != null) {
                    polilinea?.remove()
                }
                polilinea = gMap?.addPolyline(
                    PolylineOptions().addAll(posiciones)
                        .color(ContextCompat.getColor(this, R.color.rojo)).width(5f)
                        .startCap(RoundCap()).endCap(RoundCap()).pattern(pattern)
                )
            }
        }

        if (posiciones.size >= 2) {
            var distancia =
                SphericalUtil.computeDistanceBetween(posiciones[posiciones.size - 2], pos)
            distancia = distancia / 1000
            val formato = DecimalFormat("#.##")
            val num = formato.format(distancia).replace(",", ".")
            kilometros.add(num.toDouble())
        }
    }

    fun guardarRutas(nomRuta: String) {
        val geocoder = Geocoder(applicationContext, Locale.getDefault())
        var direccion1 = mutableListOf<Address>()
        var direccion2 = mutableListOf<Address>()
        direccion1 = geocoder.getFromLocation(
            posiciones.first().latitude,
            posiciones.first().longitude,
            1
        ) as MutableList<Address>
        direccion2 = geocoder.getFromLocation(
            posiciones.last().latitude,
            posiciones.last().longitude,
            1
        ) as MutableList<Address>
        rutas.add(
            Rutas(
                nomRuta,
                kilometros.sum(),
                direccion1[0].getLocality(),
                direccion2[0].getLocality()
            )
        )


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

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.drawer_rut -> {
                if(rutas.size!=0){
                    resultado.text=""
                    for (rut in rutas){
                        val formato = DecimalFormat("#.##")
                        val num = formato.format(rut.kilometros).replace(",", ".")
                        resultado.append(rut.nombreRuta+": "+rut.inicioRuta+"-"+rut.finRuta+"-"+num.toDouble()+"KM")
                        resultado.append("\n")
                    }
                    Toast.makeText(this, "Rutas actualizadas", Toast.LENGTH_SHORT).show()
                }else{
                    resultado.text="Sin rutas"
                }

            }
            R.id.drawer_rut_borrar->{
                rutas.clear()
                resultado.text="Rutas"
            }
        }
        //drawer.closeDrawer(GravityCompat.START)
        return true
    }


}