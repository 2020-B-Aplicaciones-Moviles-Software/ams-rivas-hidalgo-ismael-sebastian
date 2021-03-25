package com.example.firebase

import android.app.Activity
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.PolygonOptions
import com.google.android.gms.maps.model.PolylineOptions

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var  mMap: GoogleMap
    var tienePermisos = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        solicitarPermisos()

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

    }

    override fun onMapReady(googleMap: GoogleMap?){
        if(googleMap != null){
            mMap = googleMap

            mMap
                .setOnPolygonClickListener {
                    Log.i("mapa", "setOnPolygonClickListener ${it}")
                }
            mMap
                .setOnPolylineClickListener {
                    Log.i("mapa", "setOnPolylineClickListener ${it}")
                }

            mMap
                .setOnMarkerClickListener {
                    Log.i("mapa", "setOnMarkerClickListener ${it}")
                    return@setOnMarkerClickListener true
                }
            mMap
                .setOnCameraMoveListener {
                    Log.i("mapa", "setOnCameraMoveListener")
                }
            mMap
                .setOnCameraMoveStartedListener {
                    Log.i("mapa", "setOnMarkerClickListener ${it}")
                }
            mMap
                .setOnCameraIdleListener {
                    Log.i("mapa", "setOnCameraIdleListener")
                }


            establecerConfiguracionMapa(mMap)
            val la18 = LatLng(-2.204480056594772, -79.91842937413377)
            val titulo = "La 15 + 3"
            val zoom = 17f
            anadirMarcador(la18, titulo)
            moverCamaraConZoom(la18, zoom)

            //Línea
            val polilineaUno = googleMap
                .addPolyline(
                    PolylineOptions()
                        .clickable(true)
                        .add(
                            LatLng(-2.2040969496308094, -79.91855797280037),
                            LatLng(-2.20562983267126, -79.91808942313257)
                        )
                )
            polilineaUno.tag = "linea-1"

            //Poligono
            val poligonoUno = googleMap
                .addPolygon(
                    PolygonOptions()
                        .clickable(true)
                        .add(
                            LatLng(-2.2046250495263084, -79.91814403343449),
                            LatLng(-2.205029087626727, -79.91760491683696),
                            LatLng(-2.2039591346399745, -79.91818147208708)
                        )
                )
            poligonoUno.fillColor = -0xc771c4
            poligonoUno.tag = "poligono-2"

        }
    }

    private fun moverCamaraConZoom(latLng: LatLng, zoom: Float = 10f) {

        mMap.moveCamera(
            CameraUpdateFactory
                .newLatLngZoom(latLng, zoom)
        )
    }

    private fun anadirMarcador(latLng: LatLng, title: String) {
        mMap.addMarker(
            MarkerOptions()
                .position(latLng)
                .title(title)
        )
    }



    fun establecerConfiguracionMapa(mapa: GoogleMap){

        val contexto = this.applicationContext
        with(mapa){
            val permisosFineLocation = androidx.core.content.ContextCompat
                .checkSelfPermission(
                    contexto,
                    android.Manifest.permission.ACCESS_FINE_LOCATION
                )
            val tienePermisos = permisosFineLocation == PackageManager.PERMISSION_GRANTED
            if(tienePermisos) {
                mapa.isMyLocationEnabled = true
            }

            uiSettings.isZoomControlsEnabled = true
            uiSettings.isMyLocationButtonEnabled = true

        }

    }

    fun solicitarPermisos(){
        val permisosFineLocation = ContextCompat
            .checkSelfPermission(
                this.applicationContext,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            )
        val tienePermisos = permisosFineLocation == PackageManager.PERMISSION_GRANTED
        if(tienePermisos) {
            Log.i("mapa", "Tiene permisos FINE LOCATION")
            this.tienePermisos = true
        } else {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(
                    android.Manifest.permission.ACCESS_FINE_LOCATION
                ),
                1
            )
        }
    }

}