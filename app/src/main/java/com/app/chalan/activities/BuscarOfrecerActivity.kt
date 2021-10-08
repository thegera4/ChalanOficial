package com.app.chalan.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.app.chalan.databinding.ActivityBuscarOfrecerBinding

class BuscarOfrecerActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBuscarOfrecerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBuscarOfrecerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnBuscarServicio.setOnClickListener {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
        }
    }
}