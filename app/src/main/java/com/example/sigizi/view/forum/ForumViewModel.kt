package com.example.sigizi.view.forum

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sigizi.data.response.Article
import com.example.sigizi.data.response.Forum
import com.example.sigizi.repository.ArticleRepository
import com.example.sigizi.repository.ForumRepository
import kotlinx.coroutines.launch

class ForumViewModel(private val repository: ForumRepository) : ViewModel() {

    private val _forumes = MutableLiveData<List<Forum>>()
    val forumes: LiveData<List<Forum>>
        get() = _forumes

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean>
        get() = _isLoading

    init {
        fetchForumes()
    }

    fun fetchForumes() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val forumesList = repository.getAllForum()
                _forumes.value = forumesList
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                _isLoading.value = false
            }
        }
    }
}