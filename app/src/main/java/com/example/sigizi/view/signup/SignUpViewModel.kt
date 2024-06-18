package com.example.sigizi.view.signup

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sigizi.data.response.RegisterResponse
import com.example.sigizi.repository.UserRepository
import kotlinx.coroutines.launch

class SignUpViewModel(private val repository: UserRepository) : ViewModel() {

    private val _registrationResult = MutableLiveData<Result<String>?>()
    val registrationResult: LiveData<Result<String>?> = _registrationResult

    fun register(name: String?, email: String?, password: String?) {
        if (name.isNullOrBlank() || email.isNullOrBlank() || password.isNullOrBlank()) {
            _registrationResult.postValue(Result.failure(Exception("Semua field harus diisi")))
            return
        }

        _registrationResult.postValue(null) // Clear previous result
        viewModelScope.launch {
            try {
                Log.d("SignUpViewModel", "Registering user: name=$name, email=$email, password=$password")
                val response = repository.register(name, email, password)
                if (response.code == 200) {
                    _registrationResult.postValue(Result.success("Pendaftaran berhasil silahkan login ke akun anda!!"))
                } else {
                    _registrationResult.postValue(Result.failure(Exception("Pendaftaran gagal: ${response.message}")))
                }
            } catch (e: Exception) {
                _registrationResult.postValue(Result.failure(Exception("Pendaftaran gagal akun dengan email tersebut sudah terdaftar")))
            }
        }
    }
}
