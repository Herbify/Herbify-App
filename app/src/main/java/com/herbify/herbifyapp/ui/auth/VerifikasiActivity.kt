package com.herbify.herbifyapp.ui.auth

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.herbify.herbifyapp.R

class VerifikasiActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_verifikasi)

        supportActionBar?.hide()
    }
}