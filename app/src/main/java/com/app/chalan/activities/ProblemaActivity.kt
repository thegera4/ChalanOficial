package com.app.chalan.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.app.chalan.R
import com.app.chalan.databinding.ActivityMainBinding
import com.app.chalan.databinding.ActivityProblemaBinding

class ProblemaActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProblemaBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProblemaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        /*TODO Alberto agregar la funcionalidad para que al dar click en la image placeholder
        el usuario tenga la opcion de abrir la galeria o la camara del telefono
        si seleccionara la camara, se debera mandar llamar a la app de camara del telefono
        y podra tomar foto para usarla y colocarla en el imageview, pero si selecciona galeria
        el usuario podra seleccionar desde la galeria de su telefono cualquier imagen para
        cargarla en esta seccion. */


    }

}