package com.herbify.herbifyapp.ui.herbal_pedia

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.herbify.herbifyapp.R
import com.herbify.herbifyapp.data.remote.response.herbal.HerbalData
import com.herbify.herbifyapp.databinding.ActivityHerbalPediaDetailBinding

class HerbalPediaDetailActivity : AppCompatActivity() {
    private lateinit var  binding : ActivityHerbalPediaDetailBinding
    companion object{
        const val HERBAL_DATA = "herbaldata"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHerbalPediaDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val herbalData : HerbalData = intent.getParcelableExtra(HERBAL_DATA)!!

        initBinding()
    }

    private fun initBinding() {
    }
}