package com.example.sigizi.view.main

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.example.sigizi.R
import com.example.sigizi.data.network.ApiConfig
import com.example.sigizi.data.network.ApiService
import com.example.sigizi.databinding.ActivityMainBinding
import com.example.sigizi.di.ViewModelFactory
import com.example.sigizi.view.login.LoginActivity
import com.example.sigizi.view.welcome.WelcomeActivity
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModels {
        ViewModelFactory(this)
    }

    private lateinit var binding: ActivityMainBinding
    private lateinit var apiService: ApiService
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications, R.id.navigation_profile
            )
        )
        navView.setupWithNavController(navController)

        viewModel.getSession().observe(this) { user ->
            Log.e("MainActivity", "user: $user")
            if (!user.isLoggedIn) {
                startActivity(Intent(this@MainActivity, WelcomeActivity::class.java))
                finish()
            }
        }
        apiService = ApiConfig.getApiService(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.logout()
    }

    private fun redirectToLogin() {
        val intent = Intent(applicationContext, LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }
}