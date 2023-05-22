package com.herbify.herbifyapp.ui.splash

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.herbify.herbifyapp.MainActivity
import com.herbify.herbifyapp.R
import com.herbify.herbifyapp.ui.auth.login.LoginActivity
import com.herbify.herbifyapp.ui.auth.register.RegisterActivity

@SuppressLint("CustomSplashScreen")
class SplashScreenActivity : AppCompatActivity() {

    private val duration: Long = 3000
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        supportActionBar?.hide()
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out)

        auth = Firebase.auth
        val firebaseUser = auth.currentUser

        if(firebaseUser == null){
            @Suppress("DEPRECATION")
            Handler().postDelayed({
                val intent = Intent(this, LoginActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
                startActivity(intent)
                finish()
            }, duration)
        }else{
            @Suppress("DEPRECATION")
            Handler().postDelayed({
                val intent = Intent(this, RegisterActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
                startActivity(intent)
                finish()
            }, duration)
        }
    }
}