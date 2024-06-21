

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.sigizi.R
import com.example.sigizi.data.pref.Article
import com.example.sigizi.view.artikel.DetailArtikelActivity


class ListArticleAdapter(private val articles: MutableList<Article> = mutableListOf()) : RecyclerView.Adapter<ListArticleAdapter.ArticleViewHolder>() {

    fun setData(newArticles: List<Article>) {
        articles.clear()
        articles.addAll(newArticles)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_artikel, parent, false)
        return ArticleViewHolder(view)
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        val article = articles[position]
        holder.bind(article)
    }

    override fun getItemCount(): Int {
        return articles.size
    }

    inner class ArticleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val titleTextView: TextView = itemView.findViewById(R.id.articleTitle)
        private val summaryTextView: TextView = itemView.findViewById(R.id.articleSummary)
        private val articleImageView: ImageView = itemView.findViewById(R.id.articleImage)

        init {
            itemView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val article = articles[position]
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
                .load(article.imageUrl)
                .placeholder(R.drawable.noimage)
                .error(R.drawable.noimage)
                .centerCrop()
                .into(articleImageView)
        }
    }
}