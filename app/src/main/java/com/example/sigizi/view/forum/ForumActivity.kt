package com.example.sigizi.view.forum

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.example.sigizi.data.pref.UserPreference
import com.example.sigizi.data.response.CreateForumRequest
import com.example.sigizi.databinding.ActivityForumBinding
import com.example.sigizi.di.ViewModelFactory
import com.example.sigizi.view.artikel.reduceFileImage
import com.example.sigizi.view.artikel.uriToFile
import com.example.sigizi.view.main.MainActivity
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody

class ForumActivity : AppCompatActivity() {

    private lateinit var binding: ActivityForumBinding
    private var currentImageUri: Uri? = null

    private val viewModel: ForumPostViewModel by viewModels {
        ViewModelFactory(this@ForumActivity)
    }

    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { permissions ->
            if (permissions.all { it.value }) {
                showToast("All permissions granted")
            } else {
                showToast("Permission request denied")
            }
        }

    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(
            this, it
        ) == PackageManager.PERMISSION_GRANTED
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityForumBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (!allPermissionsGranted()) {
            requestPermissionLauncher.launch(REQUIRED_PERMISSIONS)
        }

        binding.galleryButton.setOnClickListener { startGallery() }
        binding.uploadButton.setOnClickListener { uploadForumWithImage() }
    }

    private fun startGallery() {
        launcherGallery.launch("image/*")
    }

    private val launcherGallery = registerForActivityResult(
        ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        if (uri != null) {
            currentImageUri = uri
            showImage()
        } else {
            Log.d("Photo Picker", "No media selected")
        }
    }

    private fun showImage() {
        currentImageUri?.let {
            Log.d("Image URI", "showImage: $it")
            binding.previewImageView.setImageURI(it)
        }
    }

    private fun uploadForum() {
        val title = binding.titleEditText.text.toString()
        val description = binding.descriptionEditText.text.toString()

        if (title.isNotEmpty() && description.isNotEmpty()) {
            binding.progressIndicator.visibility = View.VISIBLE
            lifecycleScope.launch {
                val tokenValue = UserPreference.getToken(this@ForumActivity).firstOrNull()

                if (tokenValue != null) {
                    try {
                        val request = CreateForumRequest(title, description)
                        viewModel.createForum(tokenValue, request)
                        showToast("Forum uploaded successfully")
                    } catch (e: Exception) {
                        Log.e("Upload", "Error uploading Forum", e)
                        showToast("Error uploading Forum")
                    }
                } else {
                    showToast("Token not found")
                }
            }
        } else {
            showToast("Please fill in title and description")
        }
    }

    private fun uploadForumWithImage() {
        val title = binding.titleEditText.text.toString()
        val description = binding.descriptionEditText.text.toString()

        if (title.isNotEmpty() && description.isNotEmpty()) {
            binding.progressIndicator.visibility = View.VISIBLE
            lifecycleScope.launch {
                val tokenValue = UserPreference.getToken(this@ForumActivity).firstOrNull()

                if (tokenValue != null) {
                    currentImageUri?.let { uri ->
                        val file = uriToFile(uri, this@ForumActivity).reduceFileImage()
                        val requestFile = file.asRequestBody("image/*".toMediaTypeOrNull())
                        val imagePart = MultipartBody.Part.createFormData("file", file.name, requestFile)

                        try {
                            // Create article
                            val request = CreateForumRequest(title, description)
                            val forumResponse = viewModel.createForum(tokenValue, request)

                            // Upload article image
                            val articleID = forumResponse.data?.articleID ?: throw Exception("Forum ID not found")
                            viewModel.uploadForumImage(tokenValue, articleID, imagePart)

                            showToastOnMainThread("Forum uploaded successfully")

                            redirectToMainActivity()

                        } catch (e: Exception) {
                            Log.e("Upload", "Error uploading Forum", e)
                            showToastOnMainThread("Error uploading Forum")
                        }
                    } ?: run {
                        showToastOnMainThread("Please select an image")
                    }
                } else {
                    showToastOnMainThread("Token not found")
                }
            }
        } else {
            showToastOnMainThread("Please fill in title and description")
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun showToastOnMainThread(message: String) {
        runOnUiThread { showToast(message) }
    }

    private fun redirectToMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish() // Optional: close the current activity to prevent returning back to it from the MainActivity
    }

    companion object {
        private val REQUIRED_PERMISSIONS = arrayOf(
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        )
    }
}
