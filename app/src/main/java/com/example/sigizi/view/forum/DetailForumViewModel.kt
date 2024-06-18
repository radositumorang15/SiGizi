package com.example.sigizi.view.forum

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sigizi.data.response.Forum
import com.example.sigizi.data.response.ForumComment
import com.example.sigizi.repository.ForumRepository
import kotlinx.coroutines.launch

class DetailForumViewModel(private val forumRepository: ForumRepository) : ViewModel() {

    private val _forumData = MutableLiveData<Forum>()
    val forumData: LiveData<Forum>
        get() = _forumData

    private val _comments = MutableLiveData<List<ForumComment>>()
    val comments: LiveData<List<ForumComment>>
        get() = _comments

    private var articleID: String = "" // Property to store articleID

    fun loadForumWithComments(articleID: String, token: String) {
        this.articleID = articleID // Store received articleID
        viewModelScope.launch {
            try {
                val response = forumRepository.getForumById(token, articleID)
                if (response.status == "success") {
                    val forumData = response.data
                    val forum = Forum(
                        id = forumData?.articleID ?: "",
                        userID = forumData?.userID ?: "",
                        articleID = forumData?.articleID ?: "", // Use articleID from ForumData
                        title = forumData?.title ?: "",
                        body = forumData?.body ?: "",
                        createdAt = forumData?.createdAt,
                        updatedAt = forumData?.updatedAt,
                        image_url = forumData?.image_url,
                        comments = forumData?.comments,
                        ArticleImages = forumData?.ArticleImages,
                        User = forumData?.forumUser
                    )
                    _forumData.value = forum
                    _comments.value = forumData?.comments ?: emptyList()
                } else {
                    // Handle failure
                }
            } catch (e: Exception) {
                e.printStackTrace()
                // Handle error
            }
        }
    }

    fun postComment(commentBody: String, token: String) {
        val articleID = this.articleID // Use stored articleID
        viewModelScope.launch {
            try {
                val response = forumRepository.postComment(token, articleID, commentBody)
                if (response.status == "success") {
                    // Reload comments after posting
                    loadForumWithComments(articleID, token)
                } else {
                    // Handle failure
                }
            } catch (e: Exception) {
                e.printStackTrace()
                // Handle error
            }
        }
    }

    fun deleteComment(commentID: String, token: String) {
        viewModelScope.launch {
            try {
                val response = forumRepository.deleteComment(token, commentID)
                if (response.status == "success") {
                    // Reload comments after deleting
                    loadForumWithComments(articleID, token)
                } else {
                    // Handle failure
                }
            } catch (e: Exception) {
                e.printStackTrace()
                // Handle error
            }
        }
    }
}