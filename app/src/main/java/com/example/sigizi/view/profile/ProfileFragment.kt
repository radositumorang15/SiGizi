package com.example.sigizi.view.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import com.example.sigizi.data.response.UserResponse
import com.example.sigizi.databinding.FragmentProfileBinding
import com.example.sigizi.data.pref.UserModel
import com.example.sigizi.data.pref.UserPreference
import com.example.sigizi.repository.UserRepository
import com.example.sigizi.di.Injection
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    private val userRepository: UserRepository by lazy {
        Injection.provideUserRepository(requireContext())
    }

    private var userSession: UserModel? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViews()

        loadUserSession()
        fetchUserProfile()

        binding.buttonupdateprofile.setOnClickListener {
            val name = binding.namaInputEditText.text.toString().trim()
            val email = binding.emailInputEditText.text.toString().trim()
            updateUserProfile(name, email)
        }

        binding.logoutbutton.setOnClickListener {
            logout()
        }

        binding.deletebutton.setOnClickListener {
            // Implement delete account logic here
        }
    }

    private fun setupViews() {
        // Set up your views here
    }

    private fun loadUserSession() {
        viewLifecycleOwner.lifecycleScope.launch {
            userRepository.getSession().collect { userModel ->
                userSession = userModel
                // Populate user session data if needed
            }
        }
    }

    private fun fetchUserProfile() {
        viewLifecycleOwner.lifecycleScope.launch {
            try {
                val tokenValue = UserPreference.getToken(requireContext()).firstOrNull()
                if (tokenValue != null) {
                    val response = userRepository.getUserProfile("Bearer $tokenValue")
                    if (response.isSuccessful) {
                        val userProfile = response.body()
                        populateUserProfile(userProfile)
                    } else {
                        // Handle error
                    }
                } else {
                    // Handle case when token is null
                }
            } catch (e: Exception) {
                // Handle exception
            }
        }
    }

    private fun updateUserProfile(name: String, email: String) {
        viewLifecycleOwner.lifecycleScope.launch {
            try {
                val tokenValue = UserPreference.getToken(requireContext()).firstOrNull()
                if (tokenValue != null) {
                    val user = UserResponse("", name, email)
                    val response = userRepository.updateUserProfile("Bearer $tokenValue", user)
                    if (response.isSuccessful) {
                        // Profile updated successfully
                        fetchUserProfile() // Refresh profile data
                    } else {
                        // Handle error
                    }
                } else {
                    // Handle case when token is null
                }
            } catch (e: Exception) {
                // Handle exception
            }
        }
    }

    private fun logout() {
        viewLifecycleOwner.lifecycleScope.launch {
            userRepository.clearSession()
            loadUserSession()
        }
    }

    private fun populateUserProfile(user: UserResponse?) {
        user?.let {
            binding.namaInputEditText.setText(it.name)
            binding.emailInputEditText.setText(it.email)
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}