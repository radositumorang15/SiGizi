package com.example.sigizi.view.artikel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sigizi.data.response.ArticleResponse
import com.example.sigizi.data.response.CreateArticleRequest
import com.example.sigizi.repository.ArticleRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.MultipartBody

class ArticlePostViewModel(private val repository: ArticleRepository) : ViewModel() {

    suspend fun createArticle(token: String, request: CreateArticleRequest): ArticleResponse {
        return repository.createArticle(token, request.title, request.body)
    }

    suspend fun uploadArticleImage(token: String, articleID: String, file: MultipartBody.Part): ArticleResponse {
        return repository.uploadArticleImage(token, articleID, file)
    }
}