package com.herbify.herbifyapp.ui.herbal_pedia

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.herbify.herbifyapp.R
import com.herbify.herbifyapp.data.remote.response.herbal.HerbalData
import com.herbify.herbifyapp.databinding.ActivityHerbalPediaDetailBinding
import com.herbify.herbifyapp.ui.ViewModelFactory
import com.herbify.herbifyapp.utils.RepositoryResult
import com.herbify.herbifyapp.utils.vibrate

class HerbalPediaDetailActivity : AppCompatActivity() {
    private lateinit var  binding : ActivityHerbalPediaDetailBinding
    private lateinit var herbalPediaViewModel: HerbalPediaViewModel
    private lateinit var brewViewModel : BrewViewModel
    private var brewHolded = 0
    companion object{
        const val HERBAL_DATA = "herbaldata"
        const val SCANNED_HERBAL = "scannedherbal"
        const val ID_HERBAL = "idherbal"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHerbalPediaDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        initViewModel()
        if(intent.hasExtra(HERBAL_DATA)){
            initBinding(intent.getParcelableExtra(HERBAL_DATA)!!)
        }else if(intent.hasExtra(SCANNED_HERBAL)){
            herbalPediaViewModel.search(intent.getStringExtra(SCANNED_HERBAL)!!).observe(this){result ->
                when(result){
                    is RepositoryResult.Loading -> {}
                    is RepositoryResult.Error -> {Toast.makeText(this, result.error, Toast.LENGTH_SHORT).show()}
                    is RepositoryResult.Success -> {
                        initBinding(result.data)
                    }
                }
            }
        }else if(intent.hasExtra(ID_HERBAL)){
            herbalPediaViewModel.herbal(intent.getIntExtra(ID_HERBAL, 0)).observe(this){
                if(it != null){
                    initBinding(it)
                }
            }
        }

    }

    private fun initViewModel() {
        brewViewModel = ViewModelProvider(this, ViewModelFactory(this))[BrewViewModel::class.java]
        herbalPediaViewModel = ViewModelProvider(this, ViewModelFactory(this))[HerbalPediaViewModel::class.java]

        brewViewModel.herbal.observe(this){herbalList ->
            brewHolded = herbalList.size
            if(herbalList.isEmpty()){
                binding.brewComponent.brewComponent.visibility = View.GONE
            }else{
                binding.brewComponent.brewComponent.visibility = View.VISIBLE
                binding.brewComponent.rvBrewedItem.adapter = BrewAdapter(herbalList){data ->
                    brewViewModel.delete(data)
                    vibrate()
                }
            }
        }
    }

    private fun initBinding(herbalData : HerbalData) {
        binding.btnBack.setOnClickListener {
            @Suppress("DEPRECATION")
            onBackPressed()
        }

        Glide.with(this).load(herbalData.image).into(binding.ivHerbal)
        binding.tvHalaman.text = herbalData.name
        binding.tvIsiDeskripsi.text = herbalData.description
        binding.tvManfaatnya.text = herbalData.benefit

        binding.brewComponent.rvBrewedItem.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        binding.fabRacik.setOnClickListener {
            if(brewHolded <= 3){
                brewViewModel.addBrewedHerbal(herbalData)
            }else{
                Toast.makeText(this, "Kotak racikan penuh, mohon buang beberapa", Toast.LENGTH_SHORT).show()
            }
        }
    }
}