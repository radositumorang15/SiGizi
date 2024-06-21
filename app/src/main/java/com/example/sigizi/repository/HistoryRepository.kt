package com.example.sigizi.repository

import com.example.sigizi.data.network.ApiService
import com.example.sigizi.data.response.Child

class HistoryRepository(private val apiService: ApiService) {

    suspend fun getAllChilds(token: String): List<Child>? {
        val response = apiService.getAllChildren("Bearer $token")
        return if (response.isSuccessful) {
            response.body()?.data
        } else {
            null
        }
    }

    suspend fun getChildById(token: String, childID: String): Child? {
        val response = apiService.getChildById("Bearer $token", childID)
        return if (response.isSuccessful) {
            response.body()
        } else {
            null
        }
    }

    suspend fun deleteChild(token: String, childID: String): Boolean {
        val response = apiService.deleteChild("Bearer $token", childID)
        return response.isSuccessful
    }
}