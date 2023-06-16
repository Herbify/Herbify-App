package com.herbify.herbifyapp.ui.camera

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.opengl.Visibility
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.herbify.herbifyapp.databinding.ActivityCameraBinding
import com.herbify.herbifyapp.ml.Model
import com.herbify.herbifyapp.ui.PopUpCameraFragment
import com.herbify.herbifyapp.utils.createFile
import com.herbify.herbifyapp.utils.rotateBitmap
import org.tensorflow.lite.DataType
import org.tensorflow.lite.support.image.ImageProcessor
import org.tensorflow.lite.support.image.TensorImage
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer
import java.io.File
import java.io.FileOutputStream
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.IntBuffer
import java.util.*

class CameraActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCameraBinding
    private var cameraSelector: CameraSelector = CameraSelector.DEFAULT_BACK_CAMERA
    private var imageCapture: ImageCapture? = null
    private lateinit var preview: Preview

    private val dataLabel = listOf<String>(
        "Belimbing Wuluh",
        "Bukan Herbal",
        "Daun Jambu Biji",
        "Daun Kari",
        "Daun Kemangi",
        "Daun Kunyit",
        "Daun Mint",
        "Daun Pepaya",
        "Daun Sirih",
        "Daun Sirsak",
        "Jeruk",
        "Katuk",
        "Kemangi",
        "Kencur",
        "Kunyit",
        "Lengkuas",
        "Lidah Buaya",
        "Nangka",
        "Pandan",
        "Seledri",
        "Teh Hijau",
        "Temulawak"
    )

    private val pickImageFromGalleryLauncher =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            uri?.let {
                val photoFile = createFile(application)
                val inputStream = contentResolver.openInputStream(uri)
                inputStream?.use { input ->
                    val outputStream = FileOutputStream(photoFile)
                    outputStream.use { output ->
                        input.copyTo(output)
                    }
                }

                val tfliteResult = runTFLiteInference(photoFile)
                showPopUpFragment(photoFile, tfliteResult)
            }
        }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCameraBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.shutter.setOnClickListener { takePhoto() }
        binding.btnGaleri.setOnClickListener { openGallery() }
        binding.btnBack.setOnClickListener {
            @Suppress("DEPRECATION")
            onBackPressed()
        }
        binding.btnTutorClose.setOnClickListener {
            binding.cvTutorial.visibility = View.GONE
        }
    }

    public override fun onResume() {
        super.onResume()
        hideSystemUI()
        startCamera()
    }

    private fun takePhoto() {
        val imageCapture = imageCapture ?: return

        val photoFile = createFile(application)

        val outputOptions = ImageCapture.OutputFileOptions.Builder(photoFile).build()
        showLoading(true)
        imageCapture.takePicture(
            outputOptions,
            ContextCompat.getMainExecutor(this),
            object : ImageCapture.OnImageSavedCallback {
                override fun onError(exc: ImageCaptureException) {
                    Toast.makeText(
                        this@CameraActivity,
                        "Gagal mengambil gambar.",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                override fun onImageSaved(output: ImageCapture.OutputFileResults) {
                    val tfliteResult = runTFLiteInference(photoFile)
                    showPopUpFragment(photoFile, tfliteResult)
                }
            }
        )
    }

    private fun openGallery() {
        if (isGalleryPermissionGranted()) {
            pickImageFromGalleryLauncher.launch("image/*")
        } else {
            requestGalleryPermission()
        }
    }

    private fun isGalleryPermissionGranted(): Boolean {
        return ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.READ_EXTERNAL_STORAGE
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun requestGalleryPermission() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
            REQUEST_GALLERY_PERMISSION
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_GALLERY_PERMISSION) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                pickImageFromGalleryLauncher.launch("image/*")
            } else {
                Toast.makeText(
                    this,
                    "Izin akses galeri ditolak.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }


    private fun showPopUpFragment(photoFile: File, predictedLabel: String) {
        val fragment = PopUpCameraFragment.newInstance(photoFile, predictedLabel)
        fragment.show(supportFragmentManager, "PopUpFragment")
        showLoading(false)
    }

    private fun showLoading(b: Boolean) {
        binding.loadingdialog.root.visibility = if(b){
            View.VISIBLE
        }else{
            View.INVISIBLE
        }
    }

    private fun startCamera() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(this)

        cameraProviderFuture.addListener({
            val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()
            preview = Preview.Builder()
                .build()
                .also {
                    it.setSurfaceProvider(binding.previewView.surfaceProvider)
                }

            imageCapture = ImageCapture.Builder().build()

            try {
                cameraProvider.unbindAll()
                cameraProvider.bindToLifecycle(
                    this,
                    cameraSelector,
                    preview,
                    imageCapture
                )

            } catch (exc: Exception) {
                Toast.makeText(
                    this@CameraActivity,
                    "Gagal memunculkan kamera.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }, ContextCompat.getMainExecutor(this))
    }

    private fun hideSystemUI() {
        @Suppress("DEPRECATION")
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

    private fun runTFLiteInference(photoFile: File): String {
        val rotatedBitmap = rotateBitmap(BitmapFactory.decodeFile(photoFile.path), true)
        val resizedBitmap = Bitmap.createScaledBitmap(rotatedBitmap, 180, 180, true)

        var tensorImage = TensorImage(DataType.FLOAT32)
        tensorImage.load(resizedBitmap)

        tensorImage = ImageProcessor.Builder().build().process(tensorImage)

        val model = Model.newInstance(application)

        val inputFeature0 = TensorBuffer.createFixedSize(intArrayOf(1, 180, 180, 3), DataType.FLOAT32)
        inputFeature0.loadBuffer(tensorImage.buffer)
//        inputFeature0.loadBuffer(byteBuffer)

        resizedBitmap.getPixels(
            IntBuffer.allocate(180 * 180 * 3).apply {
                resizedBitmap.getPixels(this.array(), 0, resizedBitmap.width, 0, 0, resizedBitmap.width, resizedBitmap.height)
            }.array(),
            0,
            resizedBitmap.width,
            0,
            0,
            resizedBitmap.width,
            resizedBitmap.height
        )

        val outputs = model.process(inputFeature0)
        val outputFeature0 = outputs.outputFeature0AsTensorBuffer

        var maxIdx = 0
        outputFeature0.floatArray.forEachIndexed{ index, fl->
            if(outputFeature0.floatArray[maxIdx] < fl){
                maxIdx = index
            }
        }
        model.close()

        return dataLabel[maxIdx]
    }






    companion object {
        private const val REQUEST_GALLERY_PERMISSION = 101
    }
}
