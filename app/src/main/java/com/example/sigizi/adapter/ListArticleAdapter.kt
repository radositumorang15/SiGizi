

import android.content.Intent
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
import com.example.sigizi.data.response.Article
import com.example.sigizi.view.artikel.DetailArtikelActivity

class ListArticleAdapter : ListAdapter<Article, ListArticleAdapter.ArticleViewHolder>(ArticleDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_artikel, parent, false)
        return ArticleViewHolder(view)
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        val article = getItem(position)
        holder.bind(article)
    }

    inner class ArticleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val titleTextView: TextView = itemView.findViewById(R.id.articleTitle)
        private val summaryTextView: TextView = itemView.findViewById(R.id.articleSummary)
        private val articleImageView: ImageView = itemView.findViewById(R.id.articleImage)

        init {
            itemView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val article = getItem(position)
                    val intent = Intent(itemView.context, DetailArtikelActivity::class.java)
                    intent.putExtra("EXTRA_ARTICLE", article)
                    itemView.context.startActivity(intent)
                }
            }
        }

        fun bind(article: Article) {
            titleTextView.text = article.title
            summaryTextView.text = article.body

            Glide.with(itemView.context)
                .load(article.ArticleImages?.firstOrNull()?.url)
                .placeholder(R.drawable.noimage)
                .error(R.drawable.noimage)
                .centerCrop()
                .into(articleImageView)
        }
    }

    class ArticleDiffCallback : DiffUtil.ItemCallback<Article>() {
        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem == newItem
        }
    }

}
