package com.example.sigizi.view.login

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.sigizi.databinding.ActivityLoginBinding
import com.example.sigizi.di.ViewModelFactory
import com.example.sigizi.view.main.MainActivity

@Suppress("DEPRECATION")
class LoginActivity : AppCompatActivity() {
    private var successDialog: AlertDialog? = null
    private lateinit var binding: ActivityLoginBinding
    private lateinit var viewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupView()
        setupAction()

        val viewModelFactory = ViewModelFactory(this)
        viewModel = ViewModelProvider(this, viewModelFactory)[LoginViewModel::class.java]

        viewModel.loginResult.observe(this, { isLoggedIn ->
            if (isLoggedIn == true) {
                binding.progressBar.visibility = View.VISIBLE
                showSuccessDialog()
            } else if (isLoggedIn == false) {
                binding.progressBar.visibility = View.GONE
                showFailureToast()
            }
        })
    }






    private fun setupAction() {
        binding.loginButton.setOnClickListener {
            val email = binding.emailEditText.text.toString()
            val password = binding.passwordEditText.text.toString()

            if (email.isNotBlank() && password.isNotBlank()) {
                // Lakukan login hanya jika email dan password tidak kosong
                viewModel.login(email, password)
            } else {
                // Tampilkan pesan jika email atau password kosong
                showEmptyFieldToast()
            }
        }
    }

    private fun showEmptyFieldToast() {
        Toast.makeText(this, "Please input email and password", Toast.LENGTH_SHORT).show()
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

    private fun showSuccessDialog() {
        successDialog?.dismiss()

        successDialog = AlertDialog.Builder(this).apply {
            setTitle("Selamat datang")
            setMessage("kamu sudah berhasil login. Klik selanjutnya.")
            setPositiveButton("Selanjutnya") { _, _ ->
                startActivity(Intent(this@LoginActivity, MainActivity::class.java).apply {
                    flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                })
                finish()
            }
        }.create()

        successDialog?.show()
    }

    private fun showFailureToast() {
        runOnUiThread {
            Toast.makeText(this@LoginActivity, "Login failed", Toast.LENGTH_SHORT).show()
        }
    }
}