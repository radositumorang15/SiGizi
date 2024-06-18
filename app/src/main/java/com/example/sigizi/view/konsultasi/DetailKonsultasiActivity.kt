package com.example.sigizi.view.konsultasi

import ConsultationData
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.sigizi.R
import com.example.sigizi.view.main.MainActivity

@Suppress("DEPRECATION")
class DetailKonsultasiActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_konsultasi2)

        val consultationData = intent.getParcelableExtra<ConsultationData>("consultationData")
        val userID = intent.getStringExtra("userID")
        val label = intent.getStringExtra("label")
        val ideal = intent.getStringExtra("ideal")
        val suggestion = intent.getStringExtra("suggestion")
        val food1Url = intent.getStringExtra("food1_url")
        val food2Url = intent.getStringExtra("food2_url")
        val food3Url = intent.getStringExtra("food3_url")

        // Use the data to populate the UI
        consultationData?.let {
            findViewById<TextView>(R.id.name).text = it.name
            findViewById<TextView>(R.id.label).text = label
            findViewById<TextView>(R.id.bb).text = "${it.bb} Kg"
            findViewById<TextView>(R.id.pb).text = "${it.pb} Cm"
            findViewById<TextView>(R.id.lk).text = "${it.lk} Cm"
            findViewById<TextView>(R.id.ideal).text = ideal
            findViewById<TextView>(R.id.suggestion).text = suggestion
        }

        val homeButton = findViewById<Button>(R.id.HomeButton)  // Get the button reference

        homeButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

    }
}