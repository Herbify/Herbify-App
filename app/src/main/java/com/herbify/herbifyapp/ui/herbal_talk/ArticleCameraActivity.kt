package com.herbify.herbifyapp.ui.herbal_talk

import android.content.Intent
import android.content.Intent.ACTION_GET_CONTENT
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import com.herbify.herbifyapp.R
import com.herbify.herbifyapp.createFile
import com.herbify.herbifyapp.databinding.ActivityArticleCameraBinding
import com.herbify.herbifyapp.model.UserPreferences
import com.herbify.herbifyapp.ui.camera.CameraActivity
import com.herbify.herbifyapp.uriToFile
import java.io.File

class ArticleCameraActivity : AppCompatActivity() {

    private lateinit var binding: ActivityArticleCameraBinding
    private var imageCapture: ImageCapture? = null
    private var cameraSelector: CameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityArticleCameraBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initBinding()
    }

    private fun initBinding() {
        binding.shutter.setOnClickListener { takePhoto() }
        binding.ivGetGallery.setOnClickListener {
            val intent = Intent()
            intent.action = ACTION_GET_CONTENT
            intent.type = "image/*"
            val chooser = Intent.createChooser(intent, "choose a picture")
            launcherIntentGallery.launch(chooser)
        }
    }


    override fun onResume() {
        super.onResume()
        hideSystemUI()
        startCamera()
    }

    private fun takePhoto() {
        val imageCapture = imageCapture ?: return
        val photoFile = createFile(application)

        val outputOptions = ImageCapture.OutputFileOptions.Builder(photoFile).build()
        imageCapture.takePicture(
            outputOptions,
            ContextCompat.getMainExecutor(this),
            object : ImageCapture.OnImageSavedCallback{
                override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                    val intent = Intent()
                    intent.putExtra("picture", photoFile)
                    intent.putExtra("isBackCamera", cameraSelector == CameraSelector.DEFAULT_BACK_CAMERA)
                    setResult(CameraActivity.CAMERA_X_RESULT, intent)
                    finish()
                }

                override fun onError(exception: ImageCaptureException) {
                    Toast.makeText(this@ArticleCameraActivity, "Gagal mengambil gambar", Toast.LENGTH_SHORT).show()
                }

            }
        )
    }

    private val launcherIntentGallery = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            val selectedImg = result.data?.data as Uri
            selectedImg.let { uri ->
                val myFile = uriToFile(uri, this@ArticleCameraActivity)
                val intent = Intent()
                intent.putExtra("picture", myFile)
                intent.putExtra("isBackCamera", cameraSelector == CameraSelector.DEFAULT_BACK_CAMERA)
                setResult(CameraActivity.CAMERA_X_RESULT, intent)
                finish()
            }
        }
    }

    private fun startCamera(){
        val cameraProviderFuture = ProcessCameraProvider.getInstance(this)
        cameraProviderFuture.addListener({
            val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()
            val preview = Preview.Builder()
                .build()
                .also {
                    it.setSurfaceProvider(binding.previewView.surfaceProvider)
                }
            imageCapture = ImageCapture.Builder().build()
            try {
                cameraProvider.unbindAll()
                cameraProvider.bindToLifecycle(this, cameraSelector, preview, imageCapture)
            }catch (e:Exception){
                Toast.makeText(this@ArticleCameraActivity, "Gagal memasukkan camera", Toast.LENGTH_SHORT).show()
            }
        }, ContextCompat.getMainExecutor(this))
    }

    private fun hideSystemUI(){
        @Suppress("DEPRECATION")
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.R){
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        }else{
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
        supportActionBar?.hide()
    }
}