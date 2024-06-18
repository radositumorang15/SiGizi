package com.example.sigizi.view.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.sigizi.data.pref.UserModel
import com.example.sigizi.repository.UserRepository
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class MainViewModel(private val userRepository: UserRepository) : ViewModel() {
    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> get() = _loading

    private val _userSession = MutableLiveData<UserModel>() // Non-null UserModel

    // Return LiveData<UserModel> instead of LiveData<UserModel?>
    fun getSession(): LiveData<UserModel> {
        return userRepository.getSession().asLiveData()
    }

    init {
        loadUserSession()
    }

    private fun loadUserSession() {
        viewModelScope.launch {
            userRepository.getSession().collect { userModel ->
                // Use !! to assert non-null
                _userSession.value = userModel!!
            }
        }
    }

    fun logout() {
        viewModelScope.launch {
            userRepository.clearSession()
            loadUserSession()
        }
    }
}