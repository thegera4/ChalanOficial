package com.app.chalan.activities

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.app.chalan.R
import com.app.chalan.activities.utils.Constants
import com.app.chalan.activities.utils.GlideLoader
import com.app.chalan.databinding.ActivityMainBinding
import com.app.chalan.databinding.ActivityProblemaBinding
import java.io.IOException

class ProblemaActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProblemaBinding

    private var mProductImageFileUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProblemaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupActionBar()

        binding.ivFotoReferencia.setOnClickListener {
            loadPhoto()
        }

        binding.btnEnviar.setOnClickListener {

        /*TODO Alberto agregar la funcionalidad para guardar la informacion tanto de la foto como
        *  de la descripcion del problema en firebase (crear colecciones de datos, una para las
        * fotos cargadas y otra para la informacion de descripcion del problema y ligarlas
        * al UID, user id) */


            //Intent para pasar a la siguiente pantalla
            val intent = Intent(this@ProblemaActivity, ContactosActivity::class.java)
            startActivity(intent)
        }

        binding.btnOmitir.setOnClickListener {
            //Intent para pasar a la siguiente pantalla
            val intent = Intent(this@ProblemaActivity, ContactosActivity::class.java)
            startActivity(intent)
        }




    }

    //funcion para action bar
    private fun setupActionBar() {
        setSupportActionBar(binding.toolbarProblemaActivity)
        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.ic_white_color_back_24dp)
            actionBar.setTitle(R.string.problema_describe_titulo)
        }
        binding.toolbarProblemaActivity.setNavigationOnClickListener { onBackPressed() }

    }

    //funcion para cargar foto al dar click en la imagen de referencia
    private fun loadPhoto(){
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE ) ==
            PackageManager.PERMISSION_GRANTED) {

            Constants.showImageChooser(this@ProblemaActivity)


        } else {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                Constants.READ_PERMISSION_CODE_REF_IMAGE
            )

        }
    }

    @Suppress("DEPRECATION")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK){
            if (requestCode == Constants.PICK_IMAGE_REQUEST_CODE){
                if (data != null) {
                    //binding.ivFotoReferencia.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_vector_edit))
                    mProductImageFileUri = data.data!!
                    try {
                        GlideLoader(this).loadUserPicture(mProductImageFileUri!!,
                            binding.ivFotoReferencia)
                    } catch (e: IOException){
                        e.printStackTrace()
                    }
                }
            }
        } else if (resultCode == Activity.RESULT_CANCELED){
            Log.e("Request Cancelled", "Image selection cancelled")
        }
    }


}