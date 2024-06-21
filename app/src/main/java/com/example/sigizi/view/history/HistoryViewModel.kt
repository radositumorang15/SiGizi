package com.example.sigizi.view.history

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sigizi.data.response.Child
import com.example.sigizi.repository.HistoryRepository
import kotlinx.coroutines.launch

class HistoryViewModel(private val repository: HistoryRepository) : ViewModel() {

    private val _children = MutableLiveData<List<Child>>()
    val children: LiveData<List<Child>> get() = _children

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error

    // Mengubah deleteChild untuk mengembalikan Boolean dari coroutine
    suspend fun deleteChild(token: String, childId: String): Boolean {
        return try {
            repository.deleteChild(token, childId)
        } catch (e: Exception) {
            _error.postValue("Exception occurred: ${e.message}")
            false
        }
    }

    // Function untuk fetch semua children
    fun fetchAllChilds(token: String) {
        viewModelScope.launch {
            try {
                val response = repository.getAllChilds(token)
                if (response != null) {
                    _children.postValue(response)
                } else {
                    _error.postValue("Failed to fetch children")
                }
            } catch (e: Exception) {
                _error.postValue("Exception occurred: ${e.message}")
            }
        }
    }
}
