package com.example.sigizi.data.pref

import java.io.Serializable

data class Article(
    val title: String,
    val body: String,
    val source:String,
    val imageUrl: Int // Assuming this is a resource ID
) : Serializable