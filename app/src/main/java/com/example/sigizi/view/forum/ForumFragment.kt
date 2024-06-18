package com.example.sigizi.view.forum

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
import com.example.sigizi.adapter.ListForumAdapter
import com.example.sigizi.databinding.FragmentForumBinding
import com.example.sigizi.di.ViewModelFactory
import com.google.android.material.floatingactionbutton.FloatingActionButton

class ForumFragment : Fragment() {

    private var _binding: FragmentForumBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: ListForumAdapter
    private val viewModel: ForumViewModel by viewModels {
        ViewModelFactory(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentForumBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView(view)
        observeViewModel()

        val createForumButton: FloatingActionButton = binding.createForum
        createForumButton.setOnClickListener {
            val intent = Intent(requireContext(), ForumActivity::class.java)
            startActivity(intent)
        }
    }

    private fun setupRecyclerView(view: View) {
        val recyclerView: RecyclerView = view.findViewById(R.id.rvForum)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        adapter = ListForumAdapter()
        recyclerView.adapter = adapter

    }


    private fun observeViewModel() {
        viewModel.forumes.observe(viewLifecycleOwner, Observer { forumes ->
            forumes?.let {
                adapter.submitListWithSort(it)
            }
        })

        viewModel.isLoading.observe(viewLifecycleOwner, Observer { isLoading ->
            if (isLoading) {
                binding.progressBar.visibility = View.VISIBLE
            } else {
                binding.progressBar.visibility = View.GONE
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
