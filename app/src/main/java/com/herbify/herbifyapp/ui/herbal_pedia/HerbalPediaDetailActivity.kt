package com.herbify.herbifyapp.ui.herbal_pedia

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
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

        supportActionBar?.hide()
        binding.btnBack.setOnClickListener {
            @Suppress("DEPRECATION")
            onBackPressed()
        }

        initBinding()

    }

    private fun initBinding() {
        val herbalData : HerbalData = intent.getParcelableExtra(HERBAL_DATA)!!
        Glide.with(this).load(herbalData.image).into(binding.ivHerbal)
        binding.tvHalaman.text = herbalData.name
        binding.tvIsiDeskripsi.text = herbalData.description
        binding.tvManfaatnya.text = herbalData.benefit
    }
}