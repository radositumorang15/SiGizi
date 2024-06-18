package com.example.sigizi.repository

import com.example.sigizi.data.network.ApiService
import com.example.sigizi.data.response.Article
import com.example.sigizi.data.response.ArticleResponse
import com.example.sigizi.data.response.CreateArticleRequest
import okhttp3.MultipartBody


class ArticleRepository(private val apiService: ApiService) {
    suspend fun createArticle(
        token: String,
        title: String,
        body: String
    ): ArticleResponse {
        val response = apiService.createArticle(
            "Bearer $token",
            CreateArticleRequest(title, body)
        )
        if (response.isSuccessful) {
            return response.body() ?: throw Exception("Response body is null")
        } else {
            throw Exception("Failed to create article: ${response.errorBody()?.string()}")
        }
    }

    suspend fun uploadArticleImage(token: String, articleID: String, file: MultipartBody.Part): ArticleResponse {
        val response = apiService.uploadArticleImage("Bearer $token", articleID, file)
        if (response.isSuccessful) {
            return response.body() ?: throw Exception("Response body is null")
        } else {
            throw Exception("Failed to upload image: ${response.errorBody()?.string()}")
        }
    }

    suspend fun deleteArticle(token: String, articleID: String): ArticleResponse {
        val response = apiService.deleteArticle("Bearer $token", articleID)
        if (response.isSuccessful) {
            return response.body() ?: throw Exception("Response body is null")
        } else {
            throw Exception("Failed to delete article: ${response.errorBody()?.string()}")
        }
    }

    suspend fun getAllArticles(): List<Article> {
        val response = apiService.getAllArticles()
        if (response.isSuccessful) {
            return response.body()?.data ?: throw Exception("Response data is null")
        } else {
            throw Exception("Failed to get articles: ${response.errorBody()?.string()}")
        }
    }

    suspend fun getArticleById(token: String, articleID: String): ArticleResponse {
        val response = apiService.getArticleById("Bearer $token", articleID)
        if (response.isSuccessful) {
            return response.body() ?: throw Exception("Response body is null")
        } else {
            throw Exception("Failed to get article: ${response.errorBody()?.string()}")
        }
    }
}