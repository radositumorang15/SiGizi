package com.example.sigizi.view.profile

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.sigizi.data.response.User
import com.example.sigizi.data.response.UserResponse
import com.example.sigizi.databinding.FragmentProfileBinding
import com.example.sigizi.data.pref.UserModel
import com.example.sigizi.data.pref.UserPreference
import com.example.sigizi.repository.UserRepository
import com.example.sigizi.di.Injection
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

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
                        val userProfileResponse = response.body()
                        val userProfile = userProfileResponse?.data
                        Log.d("ProfileFragment", "User profile fetched: $userProfile")
                        withContext(Dispatchers.Main) {
                            populateUserProfile(userProfile)
                        }
                    } else {
                        // Handle error
                        Log.e("ProfileFragment", "Error fetching user profile: ${response.errorBody()?.string()}")
                    }
                } else {
                    // Handle case when token is null
                    Log.e("ProfileFragment", "Token is null")
                }
            } catch (e: Exception) {
                // Handle exception
                Log.e("ProfileFragment", "Exception fetching user profile", e)
            }
        }
    }
    private fun updateUserProfile(name: String, email: String) {
        viewLifecycleOwner.lifecycleScope.launch {
            try {
                val tokenValue = UserPreference.getToken(requireContext()).firstOrNull()
                if (tokenValue != null) {
                    val user = User(name = name, email = email) // Pastikan properti lainnya seperti id, roleId, createdAt, dsb, jika diperlukan, diatur nilainya di sini
                    val response = userRepository.updateUserProfile("Bearer $tokenValue", user)
                    if (response.isSuccessful) {
                        val userProfileResponse = response.body()
                        val userProfile = userProfileResponse?.data
                        Log.d("ProfileFragment", "User profile updated successfully: $userProfile")
                        fetchUserProfile() // Memuat ulang data profil
                    } else {
                        val errorBody = response.errorBody()?.string()
                        Log.e("ProfileFragment", "Error updating user profile: $errorBody")
                        // Tangani kesalahan dengan lebih spesifik
                    }
                } else {
                    Log.e("ProfileFragment", "Token is null")
                    // Tangani jika token null
                }
            } catch (e: Exception) {
                Log.e("ProfileFragment", "Exception updating user profile", e)
                // Tangani exception
            }
        }
    }

    private fun createUserFromInput(name: String, email: String): User {
        return User(name = name, email = email)
    }

    private fun logout() {
        viewLifecycleOwner.lifecycleScope.launch {
            userRepository.clearSession()
            loadUserSession()
        }
    }

    private fun populateUserProfile(user: User?) {
        user?.let {
            binding.namaInputEditText.setText(it.name)
            binding.emailInputEditText.setText(it.email)
        } ?: Log.e("ProfileFragment", "User profile data is null")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
