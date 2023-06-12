package com.herbify.herbifyapp.ui.herbal_talk.detail

import android.os.Binder
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.herbify.herbifyapp.R
import com.herbify.herbifyapp.databinding.ActivityDetailPostBinding
import com.herbify.herbifyapp.ui.ViewModelFactory
import com.herbify.herbifyapp.utils.RepositoryResult

class DetailPostActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailPostBinding
    private lateinit var viewModel: DetailPostViewModel
    private var id: Int = -1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailPostBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initBinding()
        initViewModel()
    }

    private fun initViewModel() {
        id = intent.getIntExtra("id", -1)
        viewModel = ViewModelProvider(this, ViewModelFactory(this))[DetailPostViewModel::class.java]
        viewModel.getArticle(id).observe(this){result ->
            when(result){
                is RepositoryResult.Success -> {
                    val data = result.data
                    binding.tvName.text = data.data?.title
                    binding.tvDescription.text = data.data?.content
                    binding.tvTanggal.text = data.data?.updatedAt
                    Glide.with(this).load(data.data?.photo).into(binding.ivPicturePost)

                    id = data.data?.idUser!!
                    viewModel.getUserData(id).observe(this){userData ->
                        when(userData){
                            is RepositoryResult.Success -> {
                                Glide.with(this).load(userData.data.photo).into(binding.ivProfil)
                            }
                            is RepositoryResult.Loading -> {}
                            is RepositoryResult.Error -> {}
                        }
                    }
                }
                is RepositoryResult.Loading -> {}
                is RepositoryResult.Error -> {Toast.makeText(this, result.error, Toast.LENGTH_SHORT).show()}
            }
        }
    }

    private fun initBinding() {

    }
}