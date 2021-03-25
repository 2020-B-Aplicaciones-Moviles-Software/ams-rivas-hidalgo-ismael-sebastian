package com.example.proyecto2bappmov

import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.location.LocationListener
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import com.github.florent37.runtimepermission.kotlin.askPermission
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import java.io.IOException
import java.lang.Exception
import java.lang.IndexOutOfBoundsException
import java.util.*
import java.util.jar.Manifest

class DireccionActivity : AppCompatActivity(), OnMapReadyCallback, LocationListener, GoogleMap.OnCameraMoveListener, GoogleMap.OnCameraMoveStartedListener, GoogleMap.OnCameraIdleListener {

    private var mMap: GoogleMap? = null
    lateinit var mapView: MapView
    private val MAP_VIEW_BUNDLE_KEY = "MapViewBundleKey"
    private val DEFAULT_ZOOM = 15f
    private var fusedLocationProviderClient: FusedLocationProviderClient? = null
    lateinit var tvCurrentAddress: TextView
    val user= FirebaseAuth.getInstance().currentUser
    var correo = ""

    override fun onMapReady(googleMap: GoogleMap?) {

        mapView.onResume()
        mMap = googleMap

        askPermissionLocation()

        if(ActivityCompat.checkSelfPermission(
                        this,
                        android.Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                        this,
                        android.Manifest.permission.ACCESS_COARSE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
        ){
            return
        }
        mMap!!.isMyLocationEnabled = true
        mMap!!.setOnCameraMoveListener(this)
        mMap!!.setOnCameraMoveStartedListener(this)
        mMap!!.setOnCameraIdleListener(this)



    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_direccion)

        mapView = findViewById(R.id.mv_mapa)
        tvCurrentAddress = findViewById(R.id.tv_address)


        askPermissionLocation()

        var mapViewBundle: Bundle? = null
        if(savedInstanceState != null){
            mapViewBundle = savedInstanceState.getBundle(MAP_VIEW_BUNDLE_KEY)
        }

        mapView.onCreate(mapViewBundle)
        mapView.getMapAsync(this)

        correo = user?.email.toString()

        val botonGuardarDireccion = findViewById<Button>(R.id.btn_guardar_direccion)
        botonGuardarDireccion
            .setOnClickListener {
                val db = FirebaseFirestore.getInstance()
                val ref = db.collection("usuario").document(correo)
                val newAddress = hashMapOf("direccion" to tvCurrentAddress.text)
                ref
                    .set(newAddress, SetOptions.merge())
            }
    }

    public override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        askPermissionLocation()

        var mapViewBundle = outState.getBundle(MAP_VIEW_BUNDLE_KEY)
        if(mapViewBundle == null){
            mapViewBundle = Bundle()
            outState.putBundle(MAP_VIEW_BUNDLE_KEY, mapViewBundle)
        }

        mapView.onSaveInstanceState(mapViewBundle)

    }

    private fun askPermissionLocation() {
        askPermission(
                android.Manifest.permission.ACCESS_FINE_LOCATION,
                android.Manifest.permission.ACCESS_COARSE_LOCATION
        ) {
            getCurrentLocation()
        }.onDeclined { e ->
            if(e.hasDenied()) {
                e.denied.forEach{

                }
                AlertDialog.Builder(this)
                        .setMessage("Por favor, acepte los permisos para continuar")
                        .setPositiveButton("yes") { _, _ ->
                            e.askAgain()
                        }
                        .setNegativeButton("no") { dialog, _ ->
                            dialog.dismiss()
                        }
                        .show()
            }
            if(e.hasForeverDenied()) {
                e.foreverDenied.forEach{

                }
                e.goToSettings()
            }
        }
    }

    private fun getCurrentLocation() {
        fusedLocationProviderClient =
                    LocationServices.getFusedLocationProviderClient(this@DireccionActivity)
        try {
            @SuppressLint("MissingPermission") val location =
                    fusedLocationProviderClient!!.getLastLocation()
            location.addOnCompleteListener(object : OnCompleteListener<Location>{
                override fun onComplete(loc: Task<Location>) {
                    if(loc.isSuccessful) {
                        val currentLocation = loc.result
                        if(currentLocation != null){
                            moveCamera(
                                    LatLng(currentLocation.latitude, currentLocation.longitude),
                                    DEFAULT_ZOOM
                            )
                        }
                    } else {
                        askPermissionLocation()
                        //Toast.makeText(this@DireccionActivity, "No se encuentra la ubicación actual",Toast.LENGTH_SHORT).show()
                    }
                }
            })

        } catch (se: Exception){
            Log.e("Tag", "Security Exception")
        }
    }

    private fun moveCamera(latLng: LatLng, zoom: Float) {
        mMap!!.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom))
    }

    override fun onLocationChanged(location: Location) {
        val geocoder = Geocoder(this, Locale.getDefault())
        var addresses: List<Address>? = null
        try {
            addresses = geocoder.getFromLocation(location.latitude, location.longitude, 1)
        }catch (e: IOException){
            e.printStackTrace()
        }
        setAddress(addresses!![0])
    }

    private fun setAddress(addresses: Address) {
        if(addresses != null) {
            if(addresses.getAddressLine(0) != null) {
                tvCurrentAddress!!.setText(addresses.getAddressLine(0))
            }
            if(addresses.getAddressLine(1) != null) {
                tvCurrentAddress!!.setText(
                        tvCurrentAddress.getText().toString() + addresses.getAddressLine(1)
                )
            }
        }
    }

    override fun onCameraMove() {
        Log.i("Tag", "Not implemented")
    }

    override fun onCameraMoveStarted(p0: Int) {

    }

    override fun onCameraIdle() {
        var addresses: List <Address>? = null
        val geocoder = Geocoder(this, Locale.getDefault())
        try {
            addresses = geocoder.getFromLocation(mMap!!.cameraPosition.target.latitude, mMap!!.cameraPosition.target.longitude, 1)
            setAddress(addresses!![0])
        }catch (e: IndexOutOfBoundsException){
            e.printStackTrace()
        }catch (e: IOException){
            e.printStackTrace()
        }
    }
}