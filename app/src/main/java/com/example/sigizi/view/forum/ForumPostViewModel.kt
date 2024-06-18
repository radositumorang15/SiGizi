package com.example.sigizi.view.forum

import androidx.lifecycle.ViewModel
import com.example.sigizi.data.response.CreateForumRequest
import com.example.sigizi.data.response.ForumResponse
import com.example.sigizi.repository.ForumRepository
import okhttp3.MultipartBody

class ForumPostViewModel(private val repository: ForumRepository) : ViewModel() {

    suspend fun createForum(token: String, request: CreateForumRequest): ForumResponse {
        return repository.createForum(token, request.title, request.body)
    }

    suspend fun uploadForumImage(token: String, articleID: String, file: MultipartBody.Part): ForumResponse {
        return repository.uploadForumImage(token, articleID, file)
    }
}