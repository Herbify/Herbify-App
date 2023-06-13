package com.herbify.herbifyapp.ui.herbal_talk.add

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
import com.herbify.herbifyapp.ui.ViewModelFactory
import com.herbify.herbifyapp.ui.herbal_talk.ArticleCameraActivity
import com.herbify.herbifyapp.utils.reduceFileImage
import com.herbify.herbifyapp.utils.rotateFile
import com.herbify.herbifyapp.ui.ViewModelFactory
import com.herbify.herbifyapp.ui.camera.CameraActivity
import com.herbify.herbifyapp.ui.herbal_talk.ArticleCameraActivity
import com.herbify.herbifyapp.ui.herbal_talk.HerbaTalkFragment
import com.herbify.herbifyapp.utils.RepositoryResult
import java.io.File

class AddNewPostActivity : AppCompatActivity() {

    private lateinit var binding : ActivityAddNewPostBinding
    private lateinit var viewModel: AddNewArticleViewModel
    companion object {
        const val CAMERA_X_RESULT = 200
        private var REQUIRED_PERMISSIONS = arrayOf(android.Manifest.permission.CAMERA)
        private const val REQUEST_CODE_PERMISSIONS = 10
    }

    private var getFile: File? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddNewPostBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        initViewModel()
        initBinding()
    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(this, ViewModelFactory(this))[AddNewArticleViewModel::class.java]
    }

    private fun initBinding() {
        binding.btnPosting.setOnClickListener {postNewArticle()}
        if (!allPermissionsGranted()) {
            ActivityCompat.requestPermissions(
                this,
                REQUIRED_PERMISSIONS,
                REQUEST_CODE_PERMISSIONS
            )
        }

        initViewModel()

        binding.btnPosting.setOnClickListener { postNewArticle() }
        binding.btnBack.setOnClickListener {
            @Suppress("DEPRECATION")
            onBackPressed()
        }
        binding.ivPhotoArtikel.setOnClickListener {
            if (!allPermissionsGranted()) {
                ActivityCompat.requestPermissions(this, REQUIRED_PERMISSIONS, REQUEST_CODE_PERMISSIONS)
            }else{
                startCamera()
            }
        }
    }


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

    private fun startCameraX() {
        val intent = Intent(this, ArticleCameraActivity::class.java)
        launcherIntentCameraX.launch(intent)

    }

    private val launcherIntentCamera = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ){
        if(it.resultCode == CAMERA_X_RESULT){
            val myfile = if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU){
                it.data?.getSerializableExtra("picture", File::class.java)
            }else {
                @Suppress("Deprecation")
                it.data?.getSerializableExtra("picture")
            } as? File

            myfile?.let { file ->
                getFile = file
                binding.ivPhotoArtikel.setImageBitmap(BitmapFactory.decodeFile(file.path))
            }
        }
    }

    private fun postNewArticle() {
        var canPost = true
        if(binding.edJudul.text?.isEmpty() == true){
            canPost = false
        }
        if(binding.edAddDescription.text?.isEmpty() == true){
            canPost = false
        }
        if(getFile == null){
            canPost = false
        }
        if(canPost){
            viewModel.addNewArticle(
                binding.edJudul.text.toString(),
                getFile!!,
                binding.edAddDescription.text.toString(),
                ArrayList(listOf("1", "2"))
            ).observe(this){result -> 
              when(result){
                is RepositoryResult.Success -> {
                  handleSuccess()
                }
                else -> {}
              }
            }
        }
    }

    private fun handleSuccess() {
        Toast.makeText(this, "Article added Succesfully", Toast.LENGTH_SHORT).show()
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
        val intent = Intent(this, HerbaTalkFragment::class.java)
        startActivity(intent)
        finish()
    }
}