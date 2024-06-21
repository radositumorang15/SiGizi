package com.example.sigizi.view.forum

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sigizi.data.response.Forum
import com.example.sigizi.repository.ForumRepository
import kotlinx.coroutines.launch

class ForumViewModel(private val repository: ForumRepository) : ViewModel() {

    private val _forumes = MutableLiveData<List<Forum>>()
    val forumes: LiveData<List<Forum>>
        get() = _forumes

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean>
        get() = _isLoading

    private val _isDeleted = MutableLiveData<Boolean>()
    val isDeleted: LiveData<Boolean>
        get() = _isDeleted

    init {
        fetchForumes()
    }

   private fun fetchForumes() {
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

    fun deleteForum(token: String, forumId: String) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                repository.deleteForum(token, forumId)
                _isDeleted.value = true
                fetchForumes()  // Refresh forum list after deletion
            } catch (e: Exception) {
                e.printStackTrace()
                _isDeleted.value = false
            } finally {
                _isLoading.value = false
            }
        }
    }
}
