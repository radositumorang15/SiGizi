package com.example.sigizi.data.response

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.io.Serializable

data class ForumResponse(
    val status: String,
    val code: Int? = null,
    val message: String? = null,
    val data: ForumData? = null
)

data class ForumData(
    val articleID: String,
    val userID: String,
    val title: String,
    val body: String,
    val createdAt: String? = null,
    val updatedAt: String? = null,
    val image_url: String? = null,
    val comments: List<ForumComment>? = null,
    val ArticleImages: List<ForumImage>? = null,
    val forumUser: ForumUser?
)

@Parcelize
data class Forum(
    val id: String,
    val userID: String,
    val articleID: String?,
    val title: String,
    val body: String,
    val createdAt: String?,
    val updatedAt: String?,
    val image_url: String? = null,
    val comments: List<ForumComment>? = null,
    val ArticleImages: List<ForumImage>? = null,
    val User: ForumUser?
) : Parcelable

@Parcelize
data class ForumUser(
    val id: String,
    val name: String
) : Parcelable

data class ForumImage(
    val id: Int,
    val articleID: String,
    val filename: String? = null,
    val url: String,
    val updatedAt: String? = null,
    val createdAt: String? = null
) : Serializable


data class CreateForumRequest(
    val title: String,
    val body: String
)

data class ForumApiResponse<T>(
    val status: String,
    val code: Int,
    val message: String,
    val data: T
)

@Parcelize
data class ForumComment(
    val id: String,
    val articleID: String,
    val userID: String,
    val body: String,
    val createdAt: String,
    val updatedAt: String,
    val User: ForumUser?
) : Parcelable

data class CommentRequest(
    val body: String
)
