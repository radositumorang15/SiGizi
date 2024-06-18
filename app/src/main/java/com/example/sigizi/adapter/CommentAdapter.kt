package com.example.sigizi.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.sigizi.R
import com.example.sigizi.data.response.ForumComment
import com.example.sigizi.view.artikel.formatDate
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.TimeZone

class CommentAdapter(
    private val currentUserId: String, // ID pengguna saat ini
    private val onDeleteClick: (ForumComment) -> Unit
) : ListAdapter<ForumComment, CommentAdapter.CommentViewHolder>(CommentDiffCallback()) {

    inner class CommentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val commentText: TextView = itemView.findViewById(R.id.comment_text)
        private val createdAtText: TextView = itemView.findViewById(R.id.create_at)
        private val deleteButton: ImageButton = itemView.findViewById(R.id.delete_button)
        private val nametext: TextView = itemView.findViewById(R.id.name)

        fun bind(comment: ForumComment) {
            // Set comment text
            commentText.text = comment.body
           nametext.text = comment.User?.name ?: ""

            // Set created at text with custom format
            createdAtText.text = formatDate(comment.createdAt)

            // Set delete button click listener
            deleteButton.setOnClickListener {
                onDeleteClick(comment)
            }

            // Set delete button visibility based on current user
            if (comment.userID == currentUserId) {
                deleteButton.visibility = View.VISIBLE
                Log.d("ButtonVisibility", "Delete button is visible for comment: ${comment.id}")
            } else {
                deleteButton.visibility = View.GONE
                Log.d("ButtonVisibility", "Delete button is hidden for comment: ${comment.id}")
                Log.d("ButtonVisibility", "Current user ID: $currentUserId, Comment user ID: ${comment.userID}")
            }
        }
    }

    override fun onBindViewHolder(holder: CommentViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_commentcard, parent, false)
        return CommentViewHolder(view)
    }

    class CommentDiffCallback : DiffUtil.ItemCallback<ForumComment>() {
        override fun areItemsTheSame(oldItem: ForumComment, newItem: ForumComment): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: ForumComment, newItem: ForumComment): Boolean {
            return oldItem == newItem
        }
    }

    private fun formatDate(dateString: String): String {
        return try {
            val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
            inputFormat.timeZone = TimeZone.getTimeZone("UTC") // Set zona waktu ke UTC
            val date = inputFormat.parse(dateString)
            val outputFormat = SimpleDateFormat("MMM dd, yyyy HH:mm", Locale("id", "ID")) // Gunakan kode bahasa dan negara untuk Indonesia
            outputFormat.timeZone = TimeZone.getTimeZone("Asia/Jakarta") // Set zona waktu ke Asia/Jakarta (Waktu Indonesia Barat)
            outputFormat.format(date ?: "")
        } catch (e: Exception) {
            dateString // Kembalikan string asli jika parsing gagal
        }
    }
}