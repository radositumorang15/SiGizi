package com.example.sigizi.repository

import com.example.sigizi.data.network.ApiService
import com.example.sigizi.data.response.CommentRequest
import com.example.sigizi.data.response.CreateForumRequest
import com.example.sigizi.data.response.Forum
import com.example.sigizi.data.response.ForumApiResponse
import com.example.sigizi.data.response.ForumResponse
import okhttp3.MultipartBody

class ForumRepository(private val apiService: ApiService) {

    suspend fun createForum(token: String, title: String, body: String): ForumResponse {
        val response = apiService.createForum("Bearer $token", CreateForumRequest(title, body))
        if (response.isSuccessful) {
            return response.body() ?: throw Exception("Response body is null")
        } else {
            throw Exception("Failed to create Forum: ${response.errorBody()?.string()}")
        }
    }

    suspend fun uploadForumImage(token: String, articleID: String, file: MultipartBody.Part): ForumResponse {
        val response = apiService.uploadForumImage("Bearer $token", articleID, file)
        if (response.isSuccessful) {
            return response.body() ?: throw Exception("Response body is null")
        } else {
            throw Exception("Failed to upload image: ${response.errorBody()?.string()}")
        }
    }

    suspend fun deleteForum(token: String, articleID: String): ForumResponse {
        val response = apiService.deleteForum("Bearer $token", articleID)
        if (response.isSuccessful) {
            return response.body() ?: throw Exception("Response body is null")
        } else {
            throw Exception("Failed to delete Forum: ${response.errorBody()?.string()}")
        }
    }

    suspend fun getAllForum(): List<Forum> {
        val response = apiService.getAllForum()
        if (response.isSuccessful) {
            val forumResponse = response.body()
            if (forumResponse != null && forumResponse.status == "success") {
                return forumResponse.data?.map { forumData ->
                    Forum(
                        id = forumData.id,
                        userID = forumData.userID,
                        articleID = forumData.articleID,
                        title = forumData.title,
                        body = forumData.body,
                        createdAt = forumData.createdAt,
                        updatedAt = forumData.updatedAt,
                        image_url = forumData.ArticleImages?.firstOrNull()?.url,
                        comments = forumData.comments,
                        ArticleImages = forumData.ArticleImages,
                        User = forumData.User
                    )
                } ?: throw Exception("Forum data is null")
            } else {
                throw Exception("Failed to get Forum: ${forumResponse?.message ?: "Unknown error"}")
            }
        } else {
            throw Exception("Failed to get Forum: ${response.errorBody()?.string()}")
        }
    }

    suspend fun getForumById(token: String, articleID: String): ForumResponse {
        val response = apiService.getForumById("Bearer $token", articleID)
        if (response.isSuccessful) {
            return response.body() ?: throw Exception("Response body is null")
        } else {
            throw Exception("Failed to get Forum: ${response.errorBody()?.string()}")
        }
    }

    suspend fun postComment(token: String, articleID: String, body: String): ForumApiResponse<ForumResponse> {
        val response = apiService.postComment("Bearer $token", articleID, CommentRequest(body))
        if (response.isSuccessful) {
            return response.body() ?: throw Exception("Response body is null")
        } else {
            throw Exception("Failed to post comment: ${response.errorBody()?.string()}")
        }
    }

    suspend fun deleteComment(token: String, commentID: String): ForumApiResponse<Any> {
        val response = apiService.deleteComment("Bearer $token", commentID)
        if (response.isSuccessful) {
            return response.body() ?: throw Exception("Response body is null")
        } else {
            throw Exception("Failed to delete comment: ${response.errorBody()?.string()}")
        }
    }
}