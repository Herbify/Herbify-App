package com.herbify.herbifyapp.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.herbify.herbifyapp.databinding.ItemDoctorBinding
import com.herbify.herbifyapp.model.Doctor

class DoctorListAdapter : ListAdapter<Doctor, DoctorListAdapter.MyViewHolder>(DIFF_CALLBACK) {
    class MyViewHolder(val binding : ItemDoctorBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(doctor: Doctor){
            binding.tvNameDoctor.text = doctor.name
            binding.tvTahun.text = doctor.verifiedAt
            Glide.with(itemView.context).load(doctor.photo).into(binding.ivProfilDoctor)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemDoctorBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
    companion object {
        val DIFF_CALLBACK: DiffUtil.ItemCallback<Doctor> =
            object : DiffUtil.ItemCallback<Doctor>() {
                override fun areItemsTheSame(oldUser: Doctor, newUser: Doctor): Boolean {
                    return oldUser.email == newUser.email
                }

                @SuppressLint("DiffUtilEquals")
                override fun areContentsTheSame(oldUser: Doctor, newUser: Doctor): Boolean {
                    return oldUser == newUser
                }
            }
    }
}