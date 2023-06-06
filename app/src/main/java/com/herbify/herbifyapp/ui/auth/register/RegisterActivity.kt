package com.herbify.herbifyapp.ui.auth.register

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.herbify.herbifyapp.data.remote.request.RegisterRequest
import com.herbify.herbifyapp.databinding.ActivityRegisterBinding
import com.herbify.herbifyapp.ui.ViewModelFactory
import com.herbify.herbifyapp.ui.auth.login.LoginActivity
import com.herbify.herbifyapp.ui.auth.verification.VerifikasiActivity
import com.herbify.herbifyapp.ui.auth.verification.VerifikasiViewModel

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private lateinit var viewModel: RegisterViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        supportActionBar?.hide()

        initViewModel()
        initBinding()
    }

    private fun initBinding() {
        binding.tvLogin.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
        binding.btnRegister.setOnClickListener {
            register()
        }
    }

    private fun register(){
        val request = RegisterRequest(
            binding.tieName.text.toString(),
            binding.tieEmail.text.toString(),
            binding.tiePassword.text.toString()
        )
        viewModel.register(
            request,
        onRegisterSuccess = { id, email ->
            val intent = Intent(this, VerifikasiActivity::class.java)
            intent.putExtra("id", id)
            intent.putExtra("email", email)
            startActivity(intent)
        })
    }

    private fun setLoadingDiaog(isLoading: Boolean){
        binding.loadingdialog.cvLoading.visibility = if(isLoading) {
            View.VISIBLE
        }else{
            View.INVISIBLE
        }
    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(this, ViewModelFactory(this))[RegisterViewModel::class.java]
        viewModel.isLoading.observe(this){
            setLoadingDiaog(it)
        }
    }
}