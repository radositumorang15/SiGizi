package com.example.sigizi.view.konsultasi

import ConsultationData
import ConsultationResult
import CustomResult
import PredictionResponse
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.sigizi.repository.SurveyRepository
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class KonsultasiViewModel(private val repository: SurveyRepository) : ViewModel() {

    private val _predictionResult = MutableLiveData<PredictionResponse>()
    val predictionResult: LiveData<PredictionResponse> = _predictionResult

    fun predictData(
        name: String,
        gender: String,
        age: Float,
        weight: Float,
        headCircumference: Float,
        height: Float
    ) {
        viewModelScope.launch {
            val result = repository.predictData(
                ConsultationData(name, gender, age, weight, headCircumference, height)
            )
            _predictionResult.value = result
        }
    }
}