package com.example.sigizi.di

import android.content.Context
import com.example.sigizi.data.network.ApiConfig
import com.example.sigizi.repository.ForumRepository
import com.example.sigizi.repository.HistoryRepository
import com.example.sigizi.repository.SurveyRepository
import com.example.sigizi.repository.UserRepository

object Injection {
    fun provideUserRepository(context: Context): UserRepository {
        val apiService = ApiConfig.getApiService(context)
        return UserRepository(apiService, context)
    }
    fun provideSurveyRepository(context: Context): SurveyRepository {
        val apiService = ApiConfig.getApiService(context)
        return SurveyRepository(apiService)
    }

    fun provideForumRepository(context: Context): ForumRepository {
        val apiService = ApiConfig.getApiService(context)
        return ForumRepository(apiService)

    }
    fun provideHistoryRepository(context: Context): HistoryRepository {
        val apiService = ApiConfig.getApiService(context)
        return HistoryRepository(apiService)

    }
}