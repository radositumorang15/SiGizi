package com.example.sigizi.repository

import android.content.Context
import android.util.Log
import com.example.sigizi.data.network.ApiService
import com.example.sigizi.data.pref.UserModel
import com.example.sigizi.data.pref.UserPreference
import com.example.sigizi.data.response.LoginRequest
import com.example.sigizi.data.response.LoginResponse
import com.example.sigizi.data.response.RegisterRequest
import com.example.sigizi.data.response.RegisterResponse
import com.example.sigizi.data.response.User
import com.example.sigizi.data.response.UserResponse
import kotlinx.coroutines.flow.Flow
import okhttp3.ResponseBody
import retrofit2.Response

class UserRepository(private val apiService: ApiService, private val context: Context) {

    suspend fun saveSession(token: String) {
        UserPreference.saveSession(context, token)
    }

    fun getToken(): Flow<String>{
        return UserPreference.getToken(context)
    }

    fun getSession(): Flow<UserModel> {
        return UserPreference.getSession(context)
    }

    suspend fun clearSession() {
        UserPreference.clearSession(context)
    }

    suspend fun register(name: String, email: String, password: String): RegisterResponse {
        try {
            // Pastikan data yang dikirim tidak null
            if (name.isNotBlank() && email.isNotBlank() && password.isNotBlank()) {
                val request = RegisterRequest(name, email, password)
                val response = apiService.register(request)
                if (response.isSuccessful) {
                    return response.body() ?: throw Exception("Empty response body")
                } else {
                    val errorBody = response.errorBody()?.string()
                    Log.e("UserRepository", "Registration failed: $errorBody")
                    throw Exception("Registration failed: $errorBody")
                }
            } else {
                // Jika salah satu data null, lempar Exception
                throw Exception("Registration data cannot be null")
            }
        } catch (e: Exception) {
            Log.e("UserRepository", "Exception during registration: ${e.message}", e)
            throw e
        }
    }

    suspend fun login(email: String, password: String): LoginResponse {
        val loginRequest = LoginRequest(email, password)
        val response = apiService.login(loginRequest)
        if (response.isSuccessful) {
            val loginResponse = response.body()
            loginResponse?.token?.let {
                saveSession(it)
            } ?: throw Exception("Login failed: Token is null")
            return loginResponse
        } else {
            throw Exception("Login failed: ${response.errorBody()?.string()}")
        }
    }

    suspend fun getUserProfile(token: String): Response<UserResponse> {
        return apiService.getUserProfile(token)
    }

    suspend fun updateUserProfile(token: String, user: User): Response<UserResponse> {
        return apiService.updateUserProfile(token, user)
    }
}
