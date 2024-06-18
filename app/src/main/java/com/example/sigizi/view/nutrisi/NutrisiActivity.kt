package com.example.sigizi.view.nutrisi

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sigizi.R
import com.example.sigizi.adapter.NutrisiAdapter
import com.example.sigizi.data.pref.DetailNutrisi
import com.example.sigizi.data.pref.Nutrisi
import com.example.sigizi.databinding.ActivityNutrisiBinding

class NutrisiActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNutrisiBinding
    private lateinit var nutrisiAdapter: NutrisiAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNutrisiBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val nutrisiList = createNutrisiList()
        nutrisiAdapter = NutrisiAdapter(nutrisiList)

        binding.rvNutrisi.apply {
            layoutManager = LinearLayoutManager(this@NutrisiActivity)
            adapter = nutrisiAdapter
        }
    }

    private fun createNutrisiList(): List<DetailNutrisi> {
        val umurArray = resources.getStringArray(R.array.list_umur_bayi)
        val jenisNutrisiArray = resources.getStringArray(R.array.jenis_nutrisi)
        val jenisMakananArray = resources.getStringArray(R.array.jenis_makanan)

        val nutrisiList = mutableListOf<DetailNutrisi>()
        for (i in umurArray.indices) {
            val nutrisi = DetailNutrisi(
                umurArray[i],
                jenisNutrisiArray[i], // Pisahkan jenis nutrisi dan jenis makanan di sini
                jenisMakananArray[i]
            )
            nutrisiList.add(nutrisi)
        }
        return nutrisiList
    }
}