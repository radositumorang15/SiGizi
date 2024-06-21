package com.example.sigizi.data.response

import com.google.gson.annotations.SerializedName

data class Child(
    @SerializedName("id") val id: String,
    @SerializedName("userID") val userID: String,
    @SerializedName("name") val name: String,
    @SerializedName("umur") val age: Int,
    @SerializedName("jenis_kelamin") val gender: String,
    @SerializedName("bb") val weight: Int,
    @SerializedName("pb") val height: Int,
    @SerializedName("lk") val lk: Int,
    @SerializedName("label") val label: String,
    @SerializedName("ideal") val ideal: String,
    @SerializedName("suggestion") val suggestion: String,
    @SerializedName("createdAt") val createdAt: String
)

data class ApiResponse(
    @SerializedName("status") val status: String,
    @SerializedName("code") val code: Int,
    @SerializedName("message") val message: String,
    @SerializedName("data") val data: List<Child> // This should match the array of children
)
