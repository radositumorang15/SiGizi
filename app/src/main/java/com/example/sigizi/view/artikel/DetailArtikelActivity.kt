package com.example.sigizi.view.artikel

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.sigizi.data.response.Article
import com.example.sigizi.databinding.ActivityDetailArtikelBinding

class DetailArtikelActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailArtikelBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailArtikelBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val article = intent.getSerializableExtra("EXTRA_ARTICLE") as? Article
        article?.let {
            binding.tvDetailTitle.text = it.title
            binding.tvDetailDescription.text = it.body
            Glide.with(this)
                .load(it.ArticleImages?.firstOrNull()?.url)
                .into(binding.ivDetailPhoto)
        }
    }
}