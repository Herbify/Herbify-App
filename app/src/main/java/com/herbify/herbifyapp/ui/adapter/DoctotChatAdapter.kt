package com.herbify.herbifyapp.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.herbify.herbifyapp.databinding.ItemReceiveMessageBinding
import com.herbify.herbifyapp.databinding.ItemSendMessageBinding
import com.herbify.herbifyapp.model.DoctorChat
import com.herbify.herbifyapp.repository.ChatRepository

class DoctotChatAdapter(private val chatList: List<DoctorChat>): RecyclerView.Adapter<DoctotChatAdapter.MyViewHolder>() {
    companion object{
        const val SEND_MESSAGE = 0
        const val RECEIVE_MESSAGE = 1
    }
    abstract class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        abstract fun bind()
    }

    class SendMessageViewholder(val view: View): MyViewHolder(view){
        override fun bind() {
        }
    }

    class ReceiveMessageViewHolder(val view: View): MyViewHolder(view){
        override fun bind() {
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        when(viewType){
            SEND_MESSAGE -> {
                val view = ItemSendMessageBinding.inflate(LayoutInflater.from(parent.context),parent,false)
                return SendMessageViewholder(view.root)
            }
            RECEIVE_MESSAGE -> {
                val view = ItemReceiveMessageBinding.inflate(LayoutInflater.from(parent.context),parent,false)
                return ReceiveMessageViewHolder(view.root)
            }
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun getItemViewType(position: Int): Int {
        return chatList[position].type
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        TODO("Not yet implemented")
    }
}