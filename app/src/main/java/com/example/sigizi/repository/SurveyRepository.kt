package com.example.sigizi.repository

import ConsultationData
import PredictionResponse
import android.util.Log
import com.example.sigizi.data.network.ApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class SurveyRepository(private val apiService: ApiService) {

    suspend fun predictData(consultationData: ConsultationData): PredictionResponse? {
        return withContext(Dispatchers.IO) {
            try {
                val response = apiService.predictConsultation(consultationData) // Replace with your actual API call name
                if (response.isSuccessful) {
                    response.body()
                } else {
                    null
                }
            } catch (e: Exception) {
                Log.e("SurveyRepository", "Error: ${e.message}")
                null
            }
        }
    }
}