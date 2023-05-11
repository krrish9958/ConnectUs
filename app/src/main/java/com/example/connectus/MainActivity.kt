package com.example.connectus

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.example.connectus.databinding.ActivityMainBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.tasks.Task
import com.google.firebase.FirebaseError
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    //firebase se authentication krne k liye ek variable declare kia
    private lateinit var auth : FirebaseAuth

    // creating var to instantiate google sign in client
    private lateinit var googleSignInClient : GoogleSignInClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // uss variable ko intialize krdia
        auth = FirebaseAuth.getInstance()

        // Get the current user object
        val user = FirebaseAuth.getInstance().currentUser

//// Check if the user is not null
        if (user != null) {
            var intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
        } else {
            Toast.makeText(this, "you must login first!",Toast.LENGTH_SHORT).show()
        }

        binding.loginSignUpBtn.setOnClickListener {
            signInUser()
        }
        // we are creating a variable for Google Sign in options
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id)) // -> it may show error on default_web_client_id, it will disappear automatically
            .requestEmail()
            .build()

// initializing google sign in client
        googleSignInClient = GoogleSignIn.getClient(this, gso)

// adding on click listener to the google button
        binding.googleBtnSignup.setOnClickListener {
            // also creating a separate method for google sign in
            googleSignIn()
        }

    }

    private fun googleSignIn() {
        // creating a signInIntent which we can use later while signingIn
        val signInIntent = googleSignInClient.signInIntent
        launcher.launch(signInIntent)
    }

    // this will get signed in account from the intent and handle the task in between
    private val launcher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
                handleResults(task)
            }
        }

    // this method will handle the task after the sign in with user is completed
    private fun handleResults(task: Task<GoogleSignInAccount>) {
        if (task.isSuccessful) {
            val account: GoogleSignInAccount? = task.result
            if (account!=null){
                updateUI(account)
            }

        } else {
            Toast.makeText(this, task.exception.toString(), Toast.LENGTH_SHORT).show()
        }
    }

    // this will update the UI according to the tasks
    private fun updateUI(account: GoogleSignInAccount) {
        val credential = GoogleAuthProvider.getCredential(account.idToken, null)
        auth.signInWithCredential(credential).addOnCompleteListener {
            if (it.isSuccessful) {
                val intent: Intent = Intent(this, HomeActivity::class.java)
                intent.putExtra("gmail", account.email)
                intent.putExtra("gName", account.displayName)
                intent.putExtra("gProfile", account.photoUrl)
                startActivity(intent)
            } else {
                Toast.makeText(this, it.exception.toString(), Toast.LENGTH_LONG).show()
            }
        }

    }


    private fun signInUser() {
        val email = binding.emailSignUp.text.toString()
        val password = binding.passwordSignUp.text.toString()

        if(email.isNotEmpty() && password.isNotEmpty()){
            auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener {
                if (it.isSuccessful){
                    auth.signInWithEmailAndPassword(email,password).addOnCompleteListener {
                        if (it.isSuccessful){
                            Toast.makeText(this, "you are successfully logged in",Toast.LENGTH_SHORT).show()
                            var intent = Intent(this, HomeActivity::class.java)
                            startActivity(intent)
                        }
                        else{
                            Toast.makeText(this, "Try Again  ${it.exception.toString()} " , Toast.LENGTH_SHORT).show()
                        }
                    }
                } else{
                    Toast.makeText(this, "Try Again  ${it.exception.toString()} " , Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}