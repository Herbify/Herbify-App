package com.herbify.herbifyapp.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.herbify.herbifyapp.data.remote.response.chat.ConversationData
import com.herbify.herbifyapp.data.remote.response.chat.MessagesItem
import com.herbify.herbifyapp.databinding.ItemReceiveMessageBinding
import com.herbify.herbifyapp.databinding.ItemSendMessageBinding
import com.herbify.herbifyapp.model.DoctorChat

class DoctorChatAdapter(private val chatList: List<MessagesItem>): RecyclerView.Adapter<DoctorChatAdapter.MyViewHolder>() {
    companion object{
        const val SEND_MESSAGE = 0
        const val RECEIVE_MESSAGE = 1
    }
    abstract class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        abstract fun bind(message: MessagesItem)
    }

    class SendMessageViewholder(val view: View): MyViewHolder(view){
        override fun bind(message: MessagesItem) {
        }
    }

    class ReceiveMessageViewHolder(val view: View): MyViewHolder(view){
        override fun bind(message: MessagesItem) {
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
        return if(chatList[position].fromUser){
            SEND_MESSAGE
        }else{
            RECEIVE_MESSAGE
        }
    }

    override fun getItemCount(): Int {
        return  chatList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(chatList[position])
    }
}