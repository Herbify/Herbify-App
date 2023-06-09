package com.herbify.herbifyapp.ui.auth.login

import android.app.PendingIntent
import android.content.Intent
import android.content.IntentSender
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.GetSignInIntentRequest
import com.google.android.gms.auth.api.identity.Identity
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.google.firebase.ktx.initialize
import com.herbify.herbifyapp.ui.MainActivity
import com.herbify.herbifyapp.R
import com.herbify.herbifyapp.ui.ViewModelFactory
import com.herbify.herbifyapp.databinding.ActivityLoginBinding
import com.herbify.herbifyapp.model.UserModel
import com.herbify.herbifyapp.ui.auth.register.RegisterActivity
import com.herbify.herbifyapp.ui.auth.verification.VerifikasiActivity
import com.herbify.herbifyapp.utils.RepositoryResult

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var viewModel: LoginViewModel
    private lateinit var googleSignInClient: SignInClient
    private lateinit var googleSignInRequest: BeginSignInRequest
    private lateinit var auth: FirebaseAuth
    private val REQ_ONE_TAP = 2
    private var user: UserModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Log.d("LoginActivity: ", "Login page opened")

        window.statusBarColor = ContextCompat.getColor(this, R.color.white)

        supportActionBar?.hide()
        iniViewModel()
        Firebase.initialize(this)

        auth = Firebase.auth
        initGoogleSignIn()
        initBinding()
        val currentUser = auth.currentUser
        if (currentUser == null) {
            oneTapSignIn()
        }
    }

    private fun iniViewModel() {
        viewModel = ViewModelProvider(this, ViewModelFactory(this))[LoginViewModel::class.java]
        viewModel.loginResult.observe(this){result ->
            when(result){
                is RepositoryResult.Success -> {
                    setLoadingDiaog(false)
                    user = result.data
                    if (user != null){
                        if (user?.token != null){
                            movePage(user!!.isVerified)
                        }
                    }
                }
                is RepositoryResult.Loading -> {
                    setLoadingDiaog(true)
                }
                is RepositoryResult.Error ->{
                    setLoadingDiaog(false)
                    Toast.makeText(this, "Error"+result.error, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun movePage(isVerified : Boolean) {
        if(isVerified) goToMain()
        else goToOtpVerification()
    }

    private fun initBinding(){
        binding.tvRegister.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
        binding.btnGoogle.setOnClickListener {
            googleSignIn()
        }
        binding.btnLogin.setOnClickListener {
            emailSignIn()
        }
    }

    private fun emailSignIn() {
        viewModel.login(
            email = binding.tieEmail.text.toString(),
            password = binding.tiePassword.text.toString(),
        )
    }

    private fun goToOtpVerification() {
        val intent = Intent(this@LoginActivity, VerifikasiActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
        startActivity(intent)
        finish()
    }

    private fun goToMain(){
        val intent = Intent(this@LoginActivity, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
        startActivity(intent)
        finish()
    }

    private fun initGoogleSignIn(){
        googleSignInRequest = BeginSignInRequest.builder()
            .setGoogleIdTokenRequestOptions(
                BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                    .setSupported(true)
                    .setServerClientId(getString(R.string.default_web_client_id))
                    .setFilterByAuthorizedAccounts(true)
                    .build()
            ).build()

        googleSignInClient = Identity.getSignInClient(this)
    }

    private fun googleSignIn() {
        val signInRequest = GetSignInIntentRequest.builder()
            .setServerClientId(getString(R.string.default_web_client_id))
            .build()
        googleSignInClient.getSignInIntent(signInRequest)
            .addOnSuccessListener { pendingIntent ->
                launchSignIn(pendingIntent)
            }
            .addOnFailureListener { e ->
                Log.e(TAG, "google sign-in failed: ", e)
            }
    }

    private fun oneTapSignIn(){
        val oneTapRequest = BeginSignInRequest.builder()
            .setGoogleIdTokenRequestOptions(
                BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                    .setSupported(true)
                    .setServerClientId(getString(R.string.default_web_client_id))
                    .setFilterByAuthorizedAccounts(true)
                    .build()
            ).build()

        googleSignInClient.beginSignIn(oneTapRequest)
            .addOnSuccessListener { result ->
                launchSignIn(result.pendingIntent)
            }
            .addOnFailureListener { e ->
                Toast.makeText(this, e.message, Toast.LENGTH_SHORT).show()
            }
    }

    private val signInLauncher = registerForActivityResult(ActivityResultContracts.StartIntentSenderForResult()) { result ->
        handleSignInResult(result.data)
    }

    private fun launchSignIn(pendingIntent: PendingIntent) {
        try {
            val intentSenderRequest = IntentSenderRequest.Builder(pendingIntent)
                .build()
            signInLauncher.launch(intentSenderRequest)

        } catch (e: IntentSender.SendIntentException) {
            Log.e(TAG, "Couldn't start Sign In: ${e.localizedMessage}")
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when(requestCode){
            REQ_ONE_TAP -> {
                try {
                    val credential = googleSignInClient.getSignInCredentialFromIntent(data)
                    val idToken = credential.googleIdToken
                    when{
                        idToken != null ->{
                            val firebaseCredential = GoogleAuthProvider.getCredential(idToken, null)
                            auth.signInWithCredential(firebaseCredential)
                                .addOnCompleteListener(this) { task ->
                                    if (task.isSuccessful) {
                                        // Sign in success, update UI with the signed-in user's information
                                        Log.d(TAG, "signInWithCredential:success")
                                        val user = auth.currentUser
                                        updateUI(user)
                                    } else {
                                        // If sign in fails, display a message to the user.
                                        Log.w(TAG, "signInWithCredential:failure", task.exception)
                                    }
                                }
                        }
                        else -> {
                            Identity.getSignInClient(this)
                        }
                    }
                }catch (e:Exception){
                    Toast.makeText(this, e.message,Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)

        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if(task.isSuccessful){
                    Log.d(TAG, "signInWithGoogleSuccess")
                    val user = auth.currentUser
                    updateUI(user)
                }else{
                    Log.w(TAG, "signInWithCredential:failure", task.exception)
                    Toast.makeText(this, "Authentication Failed.", Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun updateUI(user: FirebaseUser?) {
        if(user != null){
            viewModel.login(user.email!!, user.displayName!!, true)
        }
    }

    private fun handleSignInResult(data: Intent?){
        try{
            val credential = googleSignInClient.getSignInCredentialFromIntent(data)
            val idToken = credential.googleIdToken
            if(idToken != null){
                Log.d(TAG, "firebaseAuthWithGoogle: ${credential.id}")
                firebaseAuthWithGoogle(idToken)
            } else {
                // Shouldn't happen.
                Log.d(TAG, "No ID token!")
            }
        }catch (e: ApiException) {
            // Google Sign In failed, update UI appropriately
            Log.w(TAG, "Google sign in failed", e)
        }
    }

    private fun setLoadingDiaog(isLoading: Boolean){
        binding.loadingdialog.cvLoading.visibility = if(isLoading) {
            View.VISIBLE
        }else{
            View.INVISIBLE
        }
    }



    companion object {
        private const val TAG = "LoginActivity"
    }
}