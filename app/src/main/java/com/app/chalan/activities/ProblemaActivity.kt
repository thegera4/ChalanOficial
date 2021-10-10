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



    }

}