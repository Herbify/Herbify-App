package com.herbify.herbifyapp.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.herbify.herbifyapp.R
import com.herbify.herbifyapp.data.remote.response.article.ArticleData
import com.herbify.herbifyapp.databinding.ItemCarouselBinding

class CarouselRVAdapter(private val carouselDataList: List<ArticleData>) :
    RecyclerView.Adapter<CarouselRVAdapter.CarouselItemViewHolder>() {

    class CarouselItemViewHolder(val binding: ItemCarouselBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarouselItemViewHolder {
        val viewHolder = ItemCarouselBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CarouselItemViewHolder(viewHolder)
    }

    override fun onBindViewHolder(holder: CarouselItemViewHolder, position: Int) {
        Glide.with(holder.itemView).load(carouselDataList[position].article?.photo).into(holder.binding.imageView)
        holder.binding.tvCaruselContent.text = carouselDataList[position].article?.title
    }

    override fun getItemCount(): Int {
        return carouselDataList.size
    }
}