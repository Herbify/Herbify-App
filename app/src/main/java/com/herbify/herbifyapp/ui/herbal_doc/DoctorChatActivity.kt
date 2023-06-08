package com.herbify.herbifyapp.ui.herbal_doc

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.herbify.herbifyapp.R
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

        initViewModel()
    }

    private fun initViewModel() {
        val doctorId: Doctor = intent.getParcelableExtra(DOCTOR_EXTRA)!!
        viewModel = ViewModelProvider(this, ViewModelFactory(this))[DoctorChatViewModel::class.java]
    }
}