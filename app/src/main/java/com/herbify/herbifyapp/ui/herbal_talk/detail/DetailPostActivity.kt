package com.herbify.herbifyapp.ui.herbal_talk.detail

import android.os.Binder
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.herbify.herbifyapp.R
import com.herbify.herbifyapp.databinding.ActivityDetailPostBinding
import com.herbify.herbifyapp.ui.ViewModelFactory
import com.herbify.herbifyapp.utils.RepositoryResult
import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class DetailPostActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailPostBinding
    private lateinit var viewModel: DetailPostViewModel
    private var id: Int = -1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailPostBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

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
                    binding.tvHalaman.text = data.data?.title
                    binding.tvDescription.text = data.data?.content
                    data.like

                    val date = data.data?.createdAt?.substring(0,9)
                    val time = data.data?.createdAt?.substring(11, 18)

                    binding.tvTanggal.text = date.toString()
                    binding.tvWaktu.text = time.toString()

                    Glide.with(this).load(data.data?.photo).into(binding.ivPicturePost)

                    id = data.data?.idUser!!
                    viewModel.getUserData(id).observe(this){userData ->
                        when(userData){
                            is RepositoryResult.Success -> {
                                binding.tvName.text = userData.data.name
                            }
                            is RepositoryResult.Loading -> {}
                            is RepositoryResult.Error -> {}
                            else -> {}
                        }
                    }
                }
                is RepositoryResult.Loading -> {}
                is RepositoryResult.Error -> {Toast.makeText(this, result.error, Toast.LENGTH_SHORT).show()}
                else -> {}
            }
        }
    }

    private fun initBinding() {
        binding.btnBack.setOnClickListener {
            @Suppress("DEPRECATION")
            onBackPressed()
        }
        binding.fabLike.setOnClickListener {
            viewModel.likeArticle(id).observe(this){
                when(it){
                    is RepositoryResult.Success -> {
                        if(it.data.data?.status == 1) binding.fabLike.setImageDrawable(ActivityCompat.getDrawable(this, R.drawable.ic_baseline_favorite_24))
                        else binding.fabLike.setImageDrawable(ActivityCompat.getDrawable(this, R.drawable.ic_baseline_favorite_border_24))
                    }
                    else -> {}
                }
            }
        }
    }
}