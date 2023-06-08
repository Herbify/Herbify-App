package com.herbify.herbifyapp.ui.herbal_talk.add

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.herbify.herbifyapp.databinding.ActivityAddNewPostBinding

class AddNewPostActivity : AppCompatActivity() {

    private lateinit var binding : ActivityAddNewPostBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddNewPostBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        binding.btnPosting.setOnClickListener {postNewArticle()}
        binding.btnBack.setOnClickListener {
            @Suppress("DEPRECATION")
            onBackPressed()
        }


    }

    private fun postNewArticle() {
        TODO("Not yet implemented")
    }
}