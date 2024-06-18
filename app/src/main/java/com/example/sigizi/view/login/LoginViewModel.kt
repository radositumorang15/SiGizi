package com.example.sigizi.view.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sigizi.repository.UserRepository
import kotlinx.coroutines.launch


class LoginViewModel(private val repository: UserRepository) : ViewModel() {
    private val _loginResult = MutableLiveData<Boolean?>()
    val loginResult: LiveData<Boolean?>
        get() = _loginResult

    fun login(email: String, password: String) {
        _loginResult.postValue(null)
        viewModelScope.launch {
            try {
                val response = repository.login(email, password)
                if (response.code == 200) {
                    repository.saveSession(response.token) // Save session token
                    _loginResult.postValue(true)
                } else {
                    _loginResult.postValue(false)
                }
            } catch (e: Exception) {
                _loginResult.postValue(false)
            }
        }
    }
}

