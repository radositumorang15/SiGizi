package com.example.sigizi.view.nutrisi

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.sigizi.R
import com.example.sigizi.databinding.ActivityDetailNutrisiBinding

class DetailNutrisiActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityDetailNutrisiBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val umur = intent.getStringExtra("EXTRA_TITLE")
        val nutrisi = intent.getStringExtra("EXTRA_DESCRIPTION")
        val makanan = intent.getStringExtra("EXTRA_FOOD") // Ganti EXTRA_DESCRIPTION dengan EXTRA_FOOD

        umur?.let { binding.umur.text = it }
        nutrisi?.let { binding.nutrisi.text = it }
        makanan?.let { binding.makanan.text = it }
    }
}