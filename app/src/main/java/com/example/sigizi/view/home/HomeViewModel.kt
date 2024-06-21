package com.example.sigizi.view.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sigizi.data.pref.UserModel
import com.example.sigizi.repository.UserRepository
import kotlinx.coroutines.launch

class HomeViewModel(private val userRepository: UserRepository) : ViewModel() {

    private val _userSession = MutableLiveData<UserModel>()
    val userSession: LiveData<UserModel> = _userSession

    init {
        loadUserSession()
    }

    fun loadUserSession() {
        viewModelScope.launch {
            userRepository.getSession().collect { userModel ->
                _userSession.value = userModel
            }
        }
    }

}