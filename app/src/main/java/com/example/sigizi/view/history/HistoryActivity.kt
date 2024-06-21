package com.example.sigizi.view.history

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.example.sigizi.adapter.HistoryAdapter
import com.example.sigizi.data.pref.UserPreference
import com.example.sigizi.databinding.ActivityHistoryBinding
import com.example.sigizi.di.ViewModelFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HistoryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHistoryBinding
    private val historyViewModel: HistoryViewModel by viewModels {
        ViewModelFactory(this)
    }
    private lateinit var adapter: HistoryAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()
        observeViewModel()

        fetchData()
    }

    private fun setupRecyclerView() {
        adapter = HistoryAdapter(mutableListOf()) { child ->
            lifecycleScope.launch {
                val token = UserPreference.getToken(this@HistoryActivity).firstOrNull()
                token?.let {
                    try {
                        val success = historyViewModel.deleteChild(it, child.id)
                        if (success) {
                            showToast("Berhasil menghapus anak")
                        } else {
                            showToast("Gagal menghapus anak")
                        }
                    } catch (e: Exception) {
                        Log.w(TAG, "Exception: ${e.message}")
                        showToast("Gagal menghapus anak")
                    }
                }
            }
        }
        binding.rvHistory.layoutManager = GridLayoutManager(this, 2)
        binding.rvHistory.adapter = adapter
    }

    private fun fetchData() {
        lifecycleScope.launch {
            val token = UserPreference.getToken(this@HistoryActivity).firstOrNull()
            token?.let {
                try {
                    historyViewModel.fetchAllChilds(it)
                } catch (e: Exception) {
                    Log.e(TAG, "Error fetching children", e)
                    // Handle error appropriately, e.g., show error message
                }
            } ?: Log.e(TAG, "Token is null or empty")
        }
    }

    private fun observeViewModel() {
        historyViewModel.children.observe(this, Observer { children ->
            adapter.updateData(children)
        })

        historyViewModel.error.observe(this, Observer { error ->
            Log.e(TAG, "Error observed in ViewModel: $error")
            // Handle error case
        })
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    companion object {
        private const val TAG = "HistoryActivity"
    }
}
