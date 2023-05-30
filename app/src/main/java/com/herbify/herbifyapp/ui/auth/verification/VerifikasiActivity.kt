package com.herbify.herbifyapp.ui.auth.verification

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import com.herbify.herbifyapp.R
import com.herbify.herbifyapp.databinding.ActivityVerifikasiBinding

class VerifikasiActivity : AppCompatActivity() {
    private lateinit var binding: ActivityVerifikasiBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVerifikasiBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setEditTextListener()

        supportActionBar?.hide()

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
                        binding.etDigitDua.requestFocus()
                    }
                }
            }

        })
    }
}