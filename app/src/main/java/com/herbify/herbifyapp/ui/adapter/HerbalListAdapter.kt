package com.herbify.herbifyapp.ui.adapter

import android.content.Intent
import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.herbify.herbifyapp.data.remote.response.herbal.HerbalData
import com.herbify.herbifyapp.databinding.ItemCardHerbapediaBinding
import com.herbify.herbifyapp.ui.herbal_pedia.HerbalPediaDetailActivity

class HerbalListAdapter: PagingDataAdapter<HerbalData, HerbalListAdapter.MyViewHolder>(DIFF_CALLBACK) {
    companion object{
        val DIFF_CALLBACK = object: DiffUtil.ItemCallback<HerbalData>(){
            override fun areItemsTheSame(oldItem: HerbalData, newItem: HerbalData): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: HerbalData, newItem: HerbalData): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }
    class MyViewHolder(private val binding: ItemCardHerbapediaBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(data: HerbalData){
            Glide.with(itemView).load(data.image).into(binding.ivHerbal)
            val intent = Intent(itemView.context, HerbalPediaDetailActivity::class.java)
            intent.putExtra(HerbalPediaDetailActivity.HERBAL_DATA, data)
            itemView.context.startActivity(intent)
        }

    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val data = getItem(position)
        if(data != null){
            holder.bind(data)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemCardHerbapediaBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }
}