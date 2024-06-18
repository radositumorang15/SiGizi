package com.example.sigizi.view.signup


import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.sigizi.databinding.ActivitySignupBinding
import com.example.sigizi.di.ViewModelFactory
import com.example.sigizi.view.login.LoginActivity

@Suppress("DEPRECATION")
class SignupActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignupBinding
    private lateinit var viewModel: SignUpViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val viewModelFactory = ViewModelFactory(this)
        viewModel = ViewModelProvider(this, viewModelFactory)[SignUpViewModel::class.java]

        binding.signupButton.setOnClickListener {
            val name = binding.nameEditText.text.toString().trim()
            val email = binding.emailEditText.text.toString().trim()
            val password = binding.passwordEditText.text.toString().trim()

            viewModel.register(name, email, password)
        }
        setupView()
        observeRegistrationResult()

    }

    private fun setupView() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
        supportActionBar?.hide()
    }

    private fun observeRegistrationResult() {
        viewModel.registrationResult.observe(this, Observer { result ->
            binding.progressBar.visibility = View.VISIBLE
            result?.onSuccess { message ->
                showMessage(message, true)
                binding.progressBar.visibility = View.GONE
            }
            result?.onFailure { exception ->
                showMessage("Registration failed: ${exception.message}", false)
            }
        })
    }

    private fun showMessage(message: String, success: Boolean) {
        AlertDialog.Builder(this).apply {
            setMessage(message)
            setPositiveButton("OK") { _, _ ->
                if (success) {
                    redirectToLogin()
                }
            }
            create()
            show()
        }
    }

    private fun redirectToLogin() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }



}