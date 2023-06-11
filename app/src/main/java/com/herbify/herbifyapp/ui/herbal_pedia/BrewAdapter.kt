package com.herbify.herbifyapp.ui.herbal_pedia

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.herbify.herbifyapp.data.remote.response.herbal.HerbalData
import com.herbify.herbifyapp.databinding.ItemBrewBinding
import com.herbify.herbifyapp.model.Brewed

class BrewAdapter(private val herbalList: List<HerbalData>): RecyclerView.Adapter<BrewAdapter.MyViewHolder>() {
    class MyViewHolder(val binding: ItemBrewBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = ItemBrewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return herbalList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        Glide.with(holder.itemView).load(herbalList[position].image).into(holder.binding.ivBrewedHerbal)
        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context, HerbalPediaDetailActivity::class.java)
            intent.putExtra(HerbalPediaDetailActivity.HERBAL_DATA, herbalList[position])
            holder.itemView.context.startActivity(intent)
        }
    }
}