package com.app.chalan.activities

import android.content.Intent
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import com.app.chalan.databinding.ActivityIntroBinding

class IntroActivity : AppCompatActivity() {

    private lateinit var binding: ActivityIntroBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityIntroBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //para esconder la barra y hacer que el splash ocupe toda la pantalla
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        //para usar fuentes customizadas
        val typeFace: Typeface = Typeface.createFromAsset(assets, "molecules.otf")
        binding.tvNombreAppIntro.typeface = typeFace

        binding.btnRegistrarse.setOnClickListener {
            startActivity(Intent(this, EntrarActivity::class.java))
        }

        binding.btnYaTengoUnaCuenta.setOnClickListener {
            startActivity(Intent(this, RegistrarseActivity::class.java))
        }
    }



}