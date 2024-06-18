package com.example.sigizi.adapter

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.sigizi.R
import com.example.sigizi.data.response.Forum
import com.example.sigizi.view.forum.DetailForumActivity


class ListForumAdapter : ListAdapter<Forum, ListForumAdapter.ForumViewHolder>(ForumDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ForumViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_forumcard, parent, false)
        return ForumViewHolder(view)
    }

    override fun onBindViewHolder(holder: ForumViewHolder, position: Int) {
        val forum = getItem(position)
        holder.bind(forum)
    }

    inner class ForumViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val titleTextView: TextView = itemView.findViewById(R.id.judul)
        private val summaryTextView: TextView = itemView.findViewById(R.id.body)
        private val forumImageView: ImageView = itemView.findViewById(R.id.imageView)
        private val nameTextView: TextView = itemView.findViewById(R.id.tv_detail_name)

        init {
            itemView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val forum = getItem(position)
                    val intent = Intent(itemView.context, DetailForumActivity::class.java)
                    intent.putExtra("EXTRA_FORUM", forum)
                    itemView.context.startActivity(intent)
                }
            }
        }

        fun bind(forum: Forum) {
            titleTextView.text = forum.title
            summaryTextView.text = forum.body

            if (forum.User != null) {
                nameTextView.text = forum.User.name ?: "Unknown User"
            } else {
                nameTextView.text = "Unknown User"
            }

            if (!forum.ArticleImages.isNullOrEmpty()) {
                Glide.with(itemView.context)
                    .load(forum.ArticleImages.first().url)
                    .placeholder(R.drawable.noimage)
                    .error(R.drawable.noimage)
                    .centerCrop()
                    .into(forumImageView)
            } else {
                Glide.with(itemView.context)
                    .load(R.drawable.noimage)
                    .centerCrop()
                    .into(forumImageView)
            }
        }
    }

    class ForumDiffCallback : DiffUtil.ItemCallback<Forum>() {
        override fun areItemsTheSame(oldItem: Forum, newItem: Forum): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Forum, newItem: Forum): Boolean {
            return oldItem == newItem
        }
    }

    // Fungsi untuk mengurutkan daftar forum dari yang terbaru
    fun submitListWithSort(list: List<Forum>) {
        val sortedList = list.sortedByDescending { it.createdAt } // Mengurutkan dari yang terbaru
        submitList(sortedList)
    }
}