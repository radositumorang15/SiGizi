package com.example.sigizi.data.pref

data class UserModel(
    val email: String,
    val token: String,
    val isLoggedIn: Boolean
)