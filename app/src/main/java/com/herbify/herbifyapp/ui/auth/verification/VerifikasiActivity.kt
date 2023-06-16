package com.herbify.herbifyapp.ui.auth.verification

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.herbify.herbifyapp.R
import com.herbify.herbifyapp.databinding.ActivityVerifikasiBinding
import com.herbify.herbifyapp.ui.MainActivity
import com.herbify.herbifyapp.ui.ViewModelFactory

class VerifikasiActivity : AppCompatActivity() {
    private lateinit var binding: ActivityVerifikasiBinding
    private lateinit var viewModel: VerifikasiViewModel
    private var otp: Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVerifikasiBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window.statusBarColor = ContextCompat.getColor(this, R.color.white)
        initViewModel()
        initBinding()
        setEditTextListener()

        supportActionBar?.hide()

    }

    private fun initBinding() {
        binding.tvOtpWrong.visibility = View.INVISIBLE
        var email = viewModel.getUser().email
        val atPos = email?.indexOf("@")
        if (email != null) {
            email = email.replaceRange(1, atPos!!, "*******")
        }
        binding.tvSendEmail.text = "The verification code has been sent via email $email"
        binding.tvResendOtp.setOnClickListener{
            viewModel.resendOtp { Toast.makeText(this, it, Toast.LENGTH_SHORT).show() }
        }
    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(this, ViewModelFactory(this))[VerifikasiViewModel::class.java]
        viewModel.otp.observe(this){it ->
            this.otp = it
        }
        viewModel.isLoading.observe(this){
            setLoadingDiaog(it)
        }
        val id = intent.getIntExtra("id", -1)
        if(id == -1){
            viewModel.refreshOtp()
        }else{
            viewModel.refreshOtp(id)
        }
    }

    private fun setLoadingDiaog(isLoading: Boolean){
        binding.loadingdialog.cvLoading.visibility = if(isLoading) {
            View.VISIBLE
        }else{
            View.INVISIBLE
        }
    }

    private fun setEditTextListener() {
        binding.etDigitSatu.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun afterTextChanged(editable: Editable?) {
                if(editable != null){
                    if(editable.length == 1){
                        binding.etDigitDua.requestFocus()
                    }
                }
            }

        })
        binding.etDigitDua.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun afterTextChanged(editable: Editable?) {
                if(editable != null){
                    if(editable.length == 1){
                        binding.etDigitTiga.requestFocus()
                    }else if(editable.isEmpty()){
                        binding.etDigitSatu.requestFocus()
                    }
                }
            }

        })
        binding.etDigitTiga.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun afterTextChanged(editable: Editable?) {
                if(editable != null){
                    if(editable.length == 1){
                        binding.etDigitEmpat.requestFocus()
                    }else if(editable.isEmpty()){
                        binding.etDigitDua.requestFocus()
                    }
                }
            }

        })
        binding.etDigitEmpat.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun afterTextChanged(editable: Editable?) {
                if(editable != null){
                    if(editable.length == 1){
                        val typedOtp = checkAndGetOTP()
                        if(typedOtp != 0){
                            val email = intent.getStringExtra("email")
                            if(email != null){
                                viewModel.verify(
                                    email,
                                    typedOtp = typedOtp,
                                    onFailureEvent = {text -> Toast.makeText(this@VerifikasiActivity, text, Toast.LENGTH_SHORT).show()},
                                    onSuccessEvent = {
                                        val intent = Intent(this@VerifikasiActivity, MainActivity::class.java)
                                        intent.flags = Intent.FLAG_ACTIVITY_SINGLE_TOP or Intent.FLAG_ACTIVITY_CLEAR_TOP
                                        startActivity(intent)
                                    }
                                )
                            }else{
                                viewModel.verify(
                                    typedOtp = typedOtp,
                                    onFailureEvent = {text -> Toast.makeText(this@VerifikasiActivity, text, Toast.LENGTH_SHORT).show()},
                                    onSuccessEvent = {
                                        val intent = Intent(this@VerifikasiActivity, MainActivity::class.java)
                                        intent.flags = Intent.FLAG_ACTIVITY_SINGLE_TOP or Intent.FLAG_ACTIVITY_CLEAR_TOP
                                        startActivity(intent)
                                    }
                                )
                            }
                        }
                    }else if(editable.isEmpty()){
                        binding.etDigitTiga.requestFocus()
                    }
                }
            }

        })
    }

    private fun checkAndGetOTP(): Int {
        val typedOTP: String =
            binding.etDigitSatu.text.toString()+
                    binding.etDigitDua.text.toString()+
                    binding.etDigitTiga.text.toString()+
                    binding.etDigitEmpat.text.toString()

        if(typedOTP.toInt() == otp){
            return typedOTP.toInt()
        }else{
            binding.tvOtpWrong.visibility = View.VISIBLE
            return  0
        }
    }
}