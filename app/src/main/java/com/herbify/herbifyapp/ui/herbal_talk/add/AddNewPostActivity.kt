package com.herbify.herbifyapp.ui.herbal_talk.add

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.herbify.herbifyapp.databinding.ActivityAddNewPostBinding
import com.herbify.herbifyapp.utils.reduceFileImage
import com.herbify.herbifyapp.utils.rotateFile
import com.herbify.herbifyapp.ui.ViewModelFactory
import com.herbify.herbifyapp.ui.camera.CameraActivity
import com.herbify.herbifyapp.ui.herbal_talk.HerbaTalkFragment
import com.herbify.herbifyapp.utils.RepositoryResult
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File

class AddNewPostActivity : AppCompatActivity() {

    private lateinit var binding : ActivityAddNewPostBinding
    private lateinit var viewModel: AddNewArticleViewModel

    private var getFile: File? = null
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (!allPermissionsGranted()) {
                Toast.makeText(
                    this,
                    "Tidak mendapatkan permission.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(baseContext, it) == PackageManager.PERMISSION_GRANTED
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddNewPostBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        if (!allPermissionsGranted()) {
            ActivityCompat.requestPermissions(
                this,
                REQUIRED_PERMISSIONS,
                REQUEST_CODE_PERMISSIONS
            )
        }

        initViewModel()

        binding.ivPhotoArtikel.setOnClickListener { startCameraX() }
        binding.btnPosting.setOnClickListener { postNewArticle() }
        binding.btnBack.setOnClickListener {
            @Suppress("DEPRECATION")
            onBackPressed()
        }


    }

    private fun startCameraX() {
        val intent = Intent(this, CameraActivity::class.java)
        launcherIntentCameraX.launch(intent)
    }

    private val launcherIntentCameraX = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == CAMERA_X_RESULT) {
            val myFile = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                it.data?.getSerializableExtra("picture", File::class.java)
            } else {
                @Suppress("DEPRECATION")
                it.data?.getSerializableExtra("picture")
            } as? File

            val isBackCamera = it.data?.getBooleanExtra("isBackCamera", true) as Boolean

            myFile?.let { file ->
                rotateFile(file, isBackCamera)
                getFile = file
                binding.ivPhotoArtikel.setImageBitmap(BitmapFactory.decodeFile(file.path))
            }
        }
    }




    private fun initViewModel() {
        viewModel = ViewModelProvider(this, ViewModelFactory(this))[AddNewArticleViewModel::class.java]

    }

    private fun postNewArticle() {
        if (getFile != null){
            val file = reduceFileImage(getFile as File, MAXIMAL_SIZE)

            val title = binding.edJudul.text.toString()
            val requestImageFile = file.asRequestBody("image/*".toMediaType())
            val imageMultipart: MultipartBody.Part = MultipartBody.Part.createFormData(
                "photo",
                file.name,
                requestImageFile
            )
            val content = binding.edAddDescription.text.toString()
            val tag1 = binding.itemTag1.text.toString()
            val tag2 = binding.itemTag2.text.toString()

            viewModel.addNewArticle(title, imageMultipart, content, tag1, tag2).observe(this){ result ->
                when (result){
                    is RepositoryResult.Loading -> {
                        // Handle loading state

                    }
                    is RepositoryResult.Success -> {
                        handleSuccess()
                    }
                    is RepositoryResult.Error -> {
                        val error = result.error
                        showErrorDialog(error)
                    }
                }
            }

        }else{
            Toast.makeText(this, "Please select photo", Toast.LENGTH_SHORT).show()
        }


    }

    private fun showErrorDialog(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun handleSuccess() {
        Toast.makeText(this, "Article added Succesfully", Toast.LENGTH_SHORT).show()
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
        val intent = Intent(this, HerbaTalkFragment::class.java)
        startActivity(intent)
    }

    companion object {
        const val CAMERA_X_RESULT = 200
        const val REQUEST_CODE_PERMISSIONS = 10

        private val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.CAMERA)
        private const val MAXIMAL_SIZE = 1000000
    }
}