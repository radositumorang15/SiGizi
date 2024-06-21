package com.example.sigizi.view.history

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.sigizi.R

class HistoryDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history_detail)

        // Get data from intent extras
        val name = intent.getStringExtra("EXTRA_NAME")
        val age = intent.getIntExtra("EXTRA_AGE", 0)
        val gender = intent.getStringExtra("EXTRA_GENDER")
        val weight = intent.getIntExtra("EXTRA_WEIGHT", 0)
        val height = intent.getIntExtra("EXTRA_HEIGHT", 0)
        val lk = intent.getIntExtra("EXTRA_LK", 0)
        val label = intent.getStringExtra("EXTRA_LABEL")
        val ideal = intent.getStringExtra("EXTRA_IDEAL")
        val suggestion = intent.getStringExtra("EXTRA_SUGGESTION")

        // Set data to TextViews
        findViewById<TextView>(R.id.name).text = name
        findViewById<TextView>(R.id.Label).text = label
        findViewById<TextView>(R.id.bb).text = "$weight Kg"
        findViewById<TextView>(R.id.lk).text = "$lk Cm"
        findViewById<TextView>(R.id.tb).text = "$height Cm"
        findViewById<TextView>(R.id.ideal).text = ideal
        findViewById<TextView>(R.id.suggestion).text = suggestion
    }
}
