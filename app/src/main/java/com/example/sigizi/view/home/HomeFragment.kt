package com.example.sigizi.view.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.sigizi.databinding.FragmentHomeBinding
import com.example.sigizi.di.ViewModelFactory
import com.example.sigizi.view.artikel.DetailArtikelActivity
import com.example.sigizi.view.history.HistoryActivity
import com.example.sigizi.view.konsultasi.ActivityKonsultasi
import com.example.sigizi.view.nutrisi.NutrisiActivity

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val viewModel: HomeViewModel by viewModels {
        ViewModelFactory(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Load user session
        viewModel.loadUserSession()


        binding.imageView1.setOnClickListener {
            val intent = Intent(requireActivity(), ActivityKonsultasi::class.java)
            startActivity(intent)
        }

        binding.imageView3.setOnClickListener {
            val intent = Intent(requireActivity(), NutrisiActivity::class.java)
            startActivity(intent)
        }

        binding.imageView2Section.setOnClickListener {
            val intent = Intent(requireContext(), HistoryActivity::class.java)
            startActivity(intent)
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
