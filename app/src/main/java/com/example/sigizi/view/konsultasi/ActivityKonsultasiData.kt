package com.example.sigizi.view.konsultasi

import ConsultationData
import PredictionResponse
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.sigizi.databinding.ActivityKonsultasiDataBinding
import com.example.sigizi.di.ViewModelFactory

class ActivityKonsultasiData : AppCompatActivity() {

    private lateinit var binding: ActivityKonsultasiDataBinding

    private val viewModel: KonsultasiViewModel by viewModels {
        ViewModelFactory(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityKonsultasiDataBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.submit.setOnClickListener {
            sendDataToApi()
        }
    }

    private fun sendDataToApi() {
        val name = binding.namaInputEditText.text.toString()
        val age = binding.umurInputEditText.text.toString().toFloatOrNull() ?: -1f
        val weight = binding.bbInputEditText.text.toString().toFloatOrNull() ?: -1f
        val height = binding.pbInputEditText.text.toString().toFloatOrNull() ?: -1f
        val headCircumference = binding.lkInputEditText.text.toString().toFloatOrNull() ?: -1f

        val gender = if (binding.lakiLakiRadioButton.isChecked) "laki-laki" else "perempuan"

        if (age <= 0f || weight <= 0f || height <= 0f || headCircumference <= 0f) {
            Toast.makeText(this, "Mohon isi semua field dengan benar", Toast.LENGTH_SHORT).show()
            return
        }

        viewModel.predictData(name, gender, age, weight, headCircumference, height)
        viewModel.predictionResult.observe(this) { response ->
            handlePredictionResponse(response)
        }
    }

    private fun handlePredictionResponse(response: PredictionResponse?) {
        if (response != null && response.status == "success") {
            val data = response.data
            if (data != null) {
                // Creating ConsultationData object
                val consultationData = ConsultationData(
                    name = data.name,
                    jenis_kelamin = data.jenis_kelamin,
                    umur = data.umur,
                    bb = data.bb,
                    pb = data.pb,
                    lk = data.lk
                )

                // Passing data to DetailKonsultasiActivity
                val intent = Intent(this, DetailKonsultasiActivity::class.java).apply {
                    putExtra("consultationData", consultationData)
                    putExtra("label", data.label)
                    putExtra("ideal", data.ideal)
                    putExtra("suggestion", data.suggestion)
                }
                startActivity(intent)
            }
        } else {
            Toast.makeText(this, "Gagal memprediksi atau terjadi kesalahan", Toast.LENGTH_SHORT).show()
        }
    }
}