package com.example.sigizi.view.artikel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.sigizi.R
import com.example.sigizi.data.pref.Article

class ArtikelViewModel(application: Application) : AndroidViewModel(application) {

    private val _articles = MutableLiveData<List<Article>>()
    val articles: LiveData<List<Article>>
        get() = _articles

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean>
        get() = _isLoading

    init {
        fetchArticlesFromResources()
    }

    private fun fetchArticlesFromResources() {
        _isLoading.value = true

        val titles = getApplication<Application>().resources.getStringArray(R.array.articleTitle)
        val images = getApplication<Application>().resources.obtainTypedArray(R.array.articleImage)
        val summaries = getApplication<Application>().resources.getStringArray(R.array.articleSummary)
        val sources = getApplication<Application>().resources.getStringArray(R.array.articlelink)

        val articlesList = mutableListOf<Article>()
        for (i in titles.indices) {
            val article = Article(
                title = titles[i],
                body = summaries[i],
                source = sources[i],
                imageUrl = images.getResourceId(i, R.drawable.noimage) // Use default image if not found
            )
            articlesList.add(article)
        }
        images.recycle()
        _articles.value = articlesList
        _isLoading.value = false
    }
}