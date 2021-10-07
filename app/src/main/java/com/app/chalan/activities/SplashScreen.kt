package com.app.chalan.activities

import android.content.Intent
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.WindowManager
import com.app.chalan.activities.firebase.FirestoreClass
import com.app.chalan.databinding.ActivitySplashScreenBinding

@Suppress("DEPRECATION")
class SplashScreen : AppCompatActivity() {

    private lateinit var binding: ActivitySplashScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //para esconder action bar y hacer que el splash ocupe toda la pantalla
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        //para usar fuentes customizadas
        val typeFace: Typeface = Typeface.createFromAsset(assets,"molecules.otf")
        binding.tvNombreApp.typeface = typeFace

        //Usar metodo postDelayed(Runnable, time)para enviar mensaje con retardo
        Handler().postDelayed({

            //para logear automaticamente al usuario con Firebase
            var currentUserID = FirestoreClass().getCurrentUserId()
            if (currentUserID.isNotEmpty()){
                startActivity(Intent(this, MainActivity::class.java))
            } else {
                startActivity(Intent(this, IntroActivity::class.java))
            }
            finish()
        }, 3000) //3000 milisegundos = 3 segundos
    }
}