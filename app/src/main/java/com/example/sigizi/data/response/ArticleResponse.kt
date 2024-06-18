package com.example.sigizi.data.response

import java.io.Serializable

data class ArticleResponse(
    val status: String,
    val code: Int? = null,
    val message: String? = null,
    val data: ArticleData? = null
)

data class ArticleData(
    val articleID: String,
    val userID: String,
    val title: String,
    val body: String,
    val createdAt: String? = null,
    val updatedAt: String? = null,
    val image_url: String? = null,
    val comments: List<Comment>? = null,
    val ArticleImages: List<ArticleImage>? = null
)

data class Article(
    val id: String,
    val userID: String,
    val articleID: String,
    val title: String,
    val body: String,
    val createdAt: String? = null,
    val updatedAt: String? = null,
    val image_url: String? = null,
    val comments: List<Comment>? = null,
    val ArticleImages: List<ArticleImage>? = null
): Serializable

data class ArticleImage(
    val id: Int,
    val articleID: String,
    val filename: String? = null,
    val url: String,
    val updatedAt: String? = null,
    val createdAt: String? = null
)  : Serializable

data class Comment(
    val id: String,
    val body: String,
    val userID: String,
    val articleID: String,
    val createdAt: String,
    val updatedAt: String
): Serializable

data class CreateArticleRequest(
    val title: String,
    val body: String
)

data class ApiResponse<T>(
    val status: String,
    val code: Int,
    val message: String,
    val data: T
)