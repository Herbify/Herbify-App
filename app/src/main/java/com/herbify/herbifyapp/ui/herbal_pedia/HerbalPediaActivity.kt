package com.herbify.herbifyapp.ui.herbal_pedia

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.herbify.herbifyapp.R
import com.herbify.herbifyapp.databinding.ActivityHerbalPediaBinding
import com.herbify.herbifyapp.ui.adapter.HerbalListAdapter
import com.herbify.herbifyapp.ui.adapter.LoadingStateAdapter

class HerbalPediaActivity : AppCompatActivity() {
    private lateinit var binding : ActivityHerbalPediaBinding
    private lateinit var  viewModel: HerbalPediaViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHerbalPediaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initBinding()
    }

    private fun initBinding() {
        binding.rvItemHerbalPedia.layoutManager = GridLayoutManager(this, 2)
        setAdapter()
    }

    private fun setAdapter() {
        val adapter =HerbalListAdapter()
        viewModel.herbals().observe(this){
            adapter.submitData(lifecycle, it)
        }
        binding.rvItemHerbalPedia.adapter = adapter.withLoadStateFooter(
            footer = LoadingStateAdapter{
                adapter.retry()
            }
        )
    }
}