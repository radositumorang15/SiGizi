package com.example.sigizi.view.artikel

import ListArticleAdapter
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sigizi.R
import com.example.sigizi.di.ViewModelFactory
import com.google.android.material.floatingactionbutton.FloatingActionButton
class ArtikelFragment : Fragment() {

    private lateinit var adapter: ListArticleAdapter
    private val viewModel: ArtikelViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_artikel, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView(view)
        observeViewModel()
    }

    private fun setupRecyclerView(view: View) {
        val recyclerView: RecyclerView = view.findViewById(R.id.rvArticle)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        adapter = ListArticleAdapter()
        recyclerView.adapter = adapter
    }

    private fun observeViewModel() {
        viewModel.articles.observe(viewLifecycleOwner, Observer { articles ->
            articles?.let {
                adapter.setData(it)
            }
        })

        viewModel.isLoading.observe(viewLifecycleOwner, Observer { isLoading ->
            // Handle loading state if needed
        })
    }
}