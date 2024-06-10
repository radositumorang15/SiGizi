package com.example.sigizi.view.artikel
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.sigizi.R
import com.example.sigizi.databinding.FragmentArtikelBinding
import com.example.sigizi.view.forum.ArtikelViewModel

class ArtikelFragment : Fragment() {

    private var _binding: FragmentArtikelBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        ViewModelProvider(this).get(ArtikelViewModel::class.java)

        return inflater.inflate(R.layout.fragment_artikel, container, false)
    }
}