package com.herbify.herbifyapp.ui.herbal_doc

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.herbify.herbifyapp.SocketHandler
import com.herbify.herbifyapp.data.remote.response.chat.Conversation
import com.herbify.herbifyapp.databinding.ActivityDoctorChatBinding
import com.herbify.herbifyapp.model.Doctor
import com.herbify.herbifyapp.ui.ViewModelFactory
import com.herbify.herbifyapp.ui.adapter.DoctorChatAdapter
import com.herbify.herbifyapp.utils.RepositoryResult
import io.socket.client.Socket

class DoctorChatActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDoctorChatBinding
    private lateinit var viewModel: DoctorChatViewModel
    private lateinit var doctor : Doctor
    private lateinit var conversation: Conversation
    private lateinit var mSocket: Socket
    companion object{
        const val DOCTOR_EXTRA = "doctor_extra"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDoctorChatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()


        initSocket()
        initViewModel()
        initBinding()
    }

    private fun initSocket() {
        // The following lines connects the Android app to the server.
        SocketHandler.setSocket()
        SocketHandler.establishConnection()

        mSocket = SocketHandler.getSocket()
        mSocket.connect()

        mSocket.on("send_message"){args ->
            if(args[0] != null){
                if(args[0] == conversation.userId && args[1] == false){
                    viewModel.getChat(conversation)
                }
            }
        }
    }

    private fun initBinding() {
        val doctorName = intent.getStringExtra("doctor_name")!!
        binding.buttonSend.isEnabled = false

        binding.namaDoctor.text = doctorName
        binding.rvConversation.layoutManager = LinearLayoutManager(this)

        binding.buttonSend.setOnClickListener {
            if(binding.editTextMessage.text.isNotEmpty()){
                viewModel.sendChat(binding.editTextMessage.text.toString(), conversation).observe(this){result ->
                    when(result){
                        is RepositoryResult.Success -> {
                            mSocket.emit("send_message", result.data.recipient, result.data.fromUser)
                            viewModel.getChat(conversation)
                        }
                        else -> {}
                    }
                }
            }
        }
    }

    private fun initViewModel() {
        val doctorId = intent.getIntExtra("doctor_id", -1)
        viewModel = ViewModelProvider(this, ViewModelFactory(this))[DoctorChatViewModel::class.java]

        viewModel.getDoctor(doctorId).observe(this){result ->
            when(result){
                is RepositoryResult.Success -> {
                    doctor = result.data
                    Glide.with(this).load(result.data.photo).into(binding.ivProfilDoctor)
                }
                is RepositoryResult.Loading -> {}
                is RepositoryResult.Error -> {}
            }
        }
        viewModel.createConversation(doctorId).observe(this){conversationResult ->
            when(conversationResult){
                is RepositoryResult.Success -> {
                    viewModel.getConversationByUserId(conversationResult.data.userId).observe(this){conversationData ->
                        when(conversationData){
                            is RepositoryResult.Success -> {
                                conversation = conversationData.data
                                viewModel.getChat(conversation)
                                binding.buttonSend.isEnabled = true
                                binding.loadingdialog.root.visibility = View.INVISIBLE
                            }
                            is RepositoryResult.Error -> {
                                binding.loadingdialog.root.visibility = View.INVISIBLE
                                Toast.makeText(this, conversationData.error, Toast.LENGTH_SHORT).show()
                            }
                            is RepositoryResult.Loading -> {
                                binding.loadingdialog.root.visibility = View.VISIBLE
                            }
                        }
                    }
                }
                is RepositoryResult.Error -> {
                    binding.loadingdialog.root.visibility = View.INVISIBLE
                    Toast.makeText(this, conversationResult.error, Toast.LENGTH_SHORT).show()
                }
                is RepositoryResult.Loading -> {
                    binding.loadingdialog.root.visibility = View.VISIBLE
                }
            }
        }
        viewModel.currentChat.observe(this){conversationData ->
            binding.rvConversation.adapter = DoctorChatAdapter(conversationData?.messages!!)
        }
    }


}