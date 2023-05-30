package com.herbify.herbifyapp.ui.splash

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.herbify.herbifyapp.ui.MainActivity
import com.herbify.herbifyapp.R
import com.herbify.herbifyapp.model.UserPreferences
import com.herbify.herbifyapp.ui.auth.login.LoginActivity

@SuppressLint("CustomSplashScreen")
class SplashScreenActivity : AppCompatActivity() {

    private val duration: Long = 3000
    private lateinit var userPref: UserPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        supportActionBar?.hide()
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out)

        userPref = UserPreferences.getInstance(this)

        if(userPref.hasSession()){
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
                val intent = Intent(this, MainActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
                startActivity(intent)
                finish()
            }, duration)
        }
    }
}