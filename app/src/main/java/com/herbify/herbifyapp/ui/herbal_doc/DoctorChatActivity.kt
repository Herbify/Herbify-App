package com.herbify.herbifyapp.ui.herbal_doc

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.herbify.herbifyapp.R
import com.herbify.herbifyapp.SocketHandler
import com.herbify.herbifyapp.databinding.ActivityDoctorChatBinding
import com.herbify.herbifyapp.model.Doctor
import com.herbify.herbifyapp.ui.ViewModelFactory
import com.herbify.herbifyapp.utils.RepositoryResult

class DoctorChatActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDoctorChatBinding
    private lateinit var viewModel: DoctorChatViewModel
    private lateinit var doctor : Doctor
    companion object{
        const val DOCTOR_EXTRA = "doctor_extra"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDoctorChatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        // The following lines connects the Android app to the server.
        SocketHandler.setSocket()
        SocketHandler.establishConnection()

        val mSocket = SocketHandler.getSocket()

        initViewModel()
        initBinding()
    }

    private fun initBinding() {
        val doctorName = intent.getStringExtra("doctor_name")!!

        binding.namaDoctor.text = doctorName

        binding.buttonSend.setOnClickListener {
        }
    }

    private fun initViewModel() {
        val doctorId = intent.getIntExtra("doctor_id", -1)
        viewModel = ViewModelProvider(this, ViewModelFactory(this))[DoctorChatViewModel::class.java]
        viewModel.getDoctor(doctorId).observe(this){result ->
            when(result){
                is RepositoryResult.Success -> {
                    Glide.with(this).load(result.data.photo).into(binding.ivProfilDoctor)
                }
                is RepositoryResult.Loading -> {}
                is RepositoryResult.Error -> {}
            }
        }
    }


}