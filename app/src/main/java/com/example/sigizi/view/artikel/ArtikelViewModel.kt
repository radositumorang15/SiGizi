package com.example.sigizi.view.artikel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sigizi.data.response.Article
import com.example.sigizi.repository.ArticleRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.MultipartBody

class ArtikelViewModel(private val repository: ArticleRepository) : ViewModel() {

    private val _articles = MutableLiveData<List<Article>>()
    val articles: LiveData<List<Article>>
        get() = _articles

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean>
        get() = _isLoading

    init {
        fetchArticles()
    }

    fun fetchArticles() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val articlesList = repository.getAllArticles()
                _articles.value = articlesList
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun refreshArticles() {
        fetchArticles()
    }
}