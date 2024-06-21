package com.example.sigizi.data.response

import com.google.gson.annotations.SerializedName

data class UserResponse(
    @SerializedName("status")
    val status: String?,
    @SerializedName("code")
    val code: Int?,
    @SerializedName("message")
    val message: String?,
    @SerializedName("data")
    val data: User?
)

data class User(
    @SerializedName("id")
    val id: String? = null,

    @SerializedName("name")
    val name: String?,

    @SerializedName("email")
    val email: String?,

    @SerializedName("role_id")
    val roleId: String? = null,

    @SerializedName("createdAt")
    val createdAt: String? = null
)