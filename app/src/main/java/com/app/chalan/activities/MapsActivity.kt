package com.app.chalan.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import com.app.chalan.R

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.app.chalan.databinding.ActivityMapsBinding

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {

        setupActionBar()

        mMap = googleMap
        // Agregar marcador en las coordenadas indicadas y hacer zoom de 14f
        val torreon = LatLng(25.53986916895781, -103.40568068906825)
        mMap.addMarker(MarkerOptions().position(torreon).title("Torreón, Coah."))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(torreon))
        val newLatLngZoom = CameraUpdateFactory.newLatLngZoom(torreon,14f)
        googleMap.animateCamera(newLatLngZoom)
    }

    private fun setupActionBar() {
        setSupportActionBar(binding.toolbarMapsActivity)
        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.ic_white_color_back_24dp)
        }

        binding.toolbarMapsActivity.setNavigationOnClickListener { onBackPressed() }

    }
}