package com.example.sigizi.view.forum

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.auth0.jwt.JWT
import com.auth0.jwt.interfaces.DecodedJWT
import com.bumptech.glide.Glide
import com.example.sigizi.adapter.CommentAdapter
import com.example.sigizi.data.pref.UserPreference
import com.example.sigizi.data.response.Forum
import com.example.sigizi.databinding.ActivityDetailForumBinding
import com.example.sigizi.di.ViewModelFactory
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.runBlocking

class DetailForumActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailForumBinding
    private lateinit var commentAdapter: CommentAdapter
    private lateinit var forum: Forum

    private val viewModel: DetailForumViewModel by viewModels {
        ViewModelFactory(this@DetailForumActivity)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailForumBinding.inflate(layoutInflater)
        setContentView(binding.root)

        forum = intent.getParcelableExtra<Forum>("EXTRA_FORUM")!!
        showForumDetail(forum)
        loadForumAndComments(forum.id)

        // Extract current user ID from session or wherever you store it
        val currentUserId = getCurrentUserId()

        // Initialize CommentAdapter with currentUserId
        val token = getToken()
        if (token != null) {
            // Initialize adapter here
            commentAdapter = CommentAdapter(currentUserId) { comment ->
                viewModel.deleteComment(comment.id, token)
            }
            binding.rvComments.layoutManager = LinearLayoutManager(this)
            binding.rvComments.adapter = commentAdapter
        } else {
            Toast.makeText(this, "User not logged in", Toast.LENGTH_SHORT).show()
            // Handle not logged in case
        }
        binding.btnSendComment.setOnClickListener {
            val commentBody = binding.etComment.text.toString().trim()
            if (commentBody.isNotEmpty()) {
                getToken()?.let { token ->
                    viewModel.postComment(commentBody, token)
                    // After posting a comment, reload the forum with its comments
                    loadForumAndComments(forum.id)
                }
                binding.etComment.setText("")
            } else {
                Toast.makeText(this, "Please enter a comment", Toast.LENGTH_SHORT).show()
            }
        }

        viewModel.comments.observe(this) { comments ->
            commentAdapter.submitList(comments)
        }
    }

    private fun showForumDetail(forum: Forum) {
        binding.tvDetailTitle.text = forum.title
        binding.tvDetailDescription.text = forum.body
        binding.tvDetailName.text = forum.User?.name ?: ""

        // Display image
        if (forum.image_url != null) {
            // If imageUrl is a single String
            Glide.with(this)
                .load(forum.image_url)
                .into(binding.ivDetailPhoto)
        } else {
            // If imageUrl is a List of ForumImage
            forum.ArticleImages?.let { imageList ->
                if (imageList.isNotEmpty()) {
                    Glide.with(this)
                        .load(imageList[0].url)
                        .into(binding.ivDetailPhoto)
                }
            }
        }
    }

    private fun loadForumAndComments(articleID: String) {
        getToken()?.let { token ->
            viewModel.loadForumWithComments(articleID, token)
        } ?: run {
            Toast.makeText(this, "User not logged in", Toast.LENGTH_SHORT).show()
        }
    }

    private fun getToken(): String? {
        return runBlocking {
            UserPreference.getToken(this@DetailForumActivity).firstOrNull()
        }
    }

    private fun getCurrentUserId(): String {
        return runBlocking {
            val userSession = UserPreference.getSession(this@DetailForumActivity).firstOrNull()
            userSession?.let {
                // Ubah token menjadi UUID
                val token = it.token
                // Ubah token menjadi UUID
                val decodedToken = decode(token)
                val userId = decodedToken.getClaim("id").asString() ?: ""
                return@runBlocking userId
            } ?: ""
        }
    }

    // Fungsi untuk mengonversi token JWT menjadi UUID
    private fun decode(token: String): DecodedJWT {
        return JWT.decode(token)
    }
}