package com.herbify.herbifyapp.ui.herbal_pedia

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.herbify.herbifyapp.R
import com.herbify.herbifyapp.data.remote.response.herbal.HerbalData
import com.herbify.herbifyapp.databinding.ActivityHerbalPediaDetailBinding
import com.herbify.herbifyapp.ui.ViewModelFactory

class HerbalPediaDetailActivity : AppCompatActivity() {
    private lateinit var  binding : ActivityHerbalPediaDetailBinding
    private lateinit var brewViewModel : BrewViewModel
    companion object{
        const val HERBAL_DATA = "herbaldata"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHerbalPediaDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        initBinding()
        initViewModel()
    }

    private fun initViewModel() {
        brewViewModel = ViewModelProvider(this, ViewModelFactory(this))[BrewViewModel::class.java]

        brewViewModel.herbal.observe(this){herbalList ->
            if(herbalList.isEmpty()){
                binding.brewComponent.brewComponent.visibility = View.GONE
            }else{
                binding.brewComponent.brewComponent.visibility = View.VISIBLE
                binding.brewComponent.rvBrewedItem.adapter = BrewAdapter(herbalList)
            }
        }
    }

    private fun initBinding() {
        binding.btnBack.setOnClickListener {
            @Suppress("DEPRECATION")
            onBackPressed()
        }

        val herbalData : HerbalData = intent.getParcelableExtra(HERBAL_DATA)!!
        Glide.with(this).load(herbalData.image).into(binding.ivHerbal)
        binding.tvHalaman.text = herbalData.name
        binding.tvIsiDeskripsi.text = herbalData.description
        binding.tvManfaatnya.text = herbalData.benefit

        binding.brewComponent.rvBrewedItem.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        binding.fabRacik.setOnClickListener {
            brewViewModel.addBrewedHerbal(herbalData)
        }
    }
}