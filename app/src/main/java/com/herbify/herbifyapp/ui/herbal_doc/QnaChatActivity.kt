package com.herbify.herbifyapp.ui.herbal_doc

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.herbify.herbifyapp.databinding.ActivityQnaChatBinding
import com.herbify.herbifyapp.ui.adapter.ChatBotAdapter
import com.herbify.herbifyapp.utils.RepositoryResult

class QnaChatActivity : AppCompatActivity() {
    private lateinit var binding : ActivityQnaChatBinding
    private lateinit var viewmodel : ChatBotViewModel
    private val chatHistory = ArrayList<ChatBotModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQnaChatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewmodel = ChatBotViewModel()
        supportActionBar?.hide()
        initBinding()
    }

    private fun initBinding() {
        binding.rvConversation.layoutManager = LinearLayoutManager(this)
        binding.buttonSend.setOnClickListener {
            if(binding.edtChatbotField.text.isNotEmpty()){
                val text = binding.edtChatbotField.text.toString()
                binding.edtChatbotField.text.clear()
                chatHistory.add(ChatBotModel(true, text))
                refreshAdapter()
                viewmodel.sendChat(text).observe(this){result ->
                    if(result != null){
                        chatHistory.add(ChatBotModel(false, result))
                        refreshAdapter()
                    }
                }
            }
        }
    }

    private fun refreshAdapter(){
        val adapter = ChatBotAdapter(chatHistory)
        binding.rvConversation.adapter = adapter
        binding.rvConversation.scrollToPosition(adapter.itemCount-1)
    }
}