package com.app.chalan.activities.maps

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.app.chalan.R
import com.app.chalan.activities.ProblemaActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.app.chalan.databinding.ActivityMapsBinding
import com.google.android.gms.maps.model.Marker

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

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {

        setupActionBar()

        mMap = googleMap

        // Agregar marcador en las coordenadas indicadas y hacer zoom de 14f
        val torreon = LatLng(25.53986916895781, -103.40568068906825)
        mMap.addMarker(MarkerOptions().position(torreon).title("Torreón, Coah.")
            .draggable(true))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(torreon))
        val newLatLngZoom = CameraUpdateFactory.newLatLngZoom(torreon,14f)
        googleMap.animateCamera(newLatLngZoom)

        // Agregar listeners para marcador
        mMap.setOnMarkerDragListener(object: GoogleMap.OnMarkerDragListener{
            override fun onMarkerDragStart(marker: Marker) {
                //Codigo para cuando marcador se empieza a arrastrar
            }

            override fun onMarkerDrag(marker: Marker) {
                //Codigo para cuando marcador se esta arrastrando
            }

            override fun onMarkerDragEnd(marker: Marker) {
                //Codigo para cuando marcador termina de arrastrarse

                /*TODO Alberto: agregar el codigo que permita obtener la informacion de la
                * locacion seleccionada por el usuario, mostrarla en el cuadrito del marcador
                * y almacenar esa informacion. Se tendra que desplegar un cuadro de dialogo que
                * le pregunte al usuario si esta seguro de que esa es la locacion donde requiere
                * el servicio. Al dar si, se pasa a la siguiente pantalla (ProblemaActivity). Si da
                * no, se cierra el cuadro de dialogo y se vuelve al mapa para seleccionar otra vez
                * la locacion donde se quiere el servicio.
                * Tambien agregar la captura de la informacion en caso de que el usuario use el
                * cuadro de texto para introducir el codigo postal manualmente. Solo se almacenara
                * la informacion por el momento */

                //Intent para al momento de arrastar marcador y dejarlo en una posicion, llevarlo al siguiente activity o pantalla
                val intent = Intent(this@MapsActivity, ProblemaActivity::class.java)
                startActivity(intent)
                finish()
            }
        })
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