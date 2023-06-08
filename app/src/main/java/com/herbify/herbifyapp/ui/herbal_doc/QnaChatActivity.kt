package com.herbify.herbifyapp.ui.herbal_doc

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.herbify.herbifyapp.R
import com.herbify.herbifyapp.data.dummies.DummyDoctorChat
import com.herbify.herbifyapp.databinding.ActivityQnaChatBinding
import com.herbify.herbifyapp.ui.adapter.DoctotChatAdapter

class QnaChatActivity : AppCompatActivity() {
    private lateinit var binding : ActivityQnaChatBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQnaChatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initBinding()
    }

    private fun initBinding() {
    }
}