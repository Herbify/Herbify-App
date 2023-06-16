package com.herbify.herbifyapp.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.herbify.herbifyapp.data.remote.response.chat.MessagesItem
import com.herbify.herbifyapp.databinding.ItemReceiveMessageBinding
import com.herbify.herbifyapp.databinding.ItemSendMessageBinding
import com.herbify.herbifyapp.ui.herbal_doc.ChatBotModel

class ChatBotAdapter(private val chatList: List<ChatBotModel>): RecyclerView.Adapter<ChatBotAdapter.MyViewHolder>() {
    companion object {
        const val SEND_MESSAGE = 0
        const val RECEIVE_MESSAGE = 1
    }

    abstract class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        abstract fun bind(message: ChatBotModel)
    }

    class SendMessageViewholder(val binding: ItemSendMessageBinding) : MyViewHolder(binding.root) {
        override fun bind(message: ChatBotModel) {
            binding.tvSendMessageContent.text = message.content
        }
    }

    class ReceiveMessageViewHolder(val binding: ItemReceiveMessageBinding) :
        MyViewHolder(binding.root) {
        override fun bind(message: ChatBotModel) {
            binding.tvReceiveMessageContent.text = message.content
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        when (viewType) {
            SEND_MESSAGE -> {
                val view = ItemSendMessageBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                return SendMessageViewholder(view)
            }
            RECEIVE_MESSAGE -> {
                val view = ItemReceiveMessageBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                return ReceiveMessageViewHolder(view)
            }
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (chatList[position].fromUser) {
            SEND_MESSAGE
        } else {
            RECEIVE_MESSAGE
        }
    }

    override fun getItemCount(): Int {
        return chatList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(chatList[position])
    }
}