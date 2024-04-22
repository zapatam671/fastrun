@file:Suppress("DEPRECATION")

package com.examples.fastrun

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.fastrun.databinding.ActivityLoginBinding
import com.examples.fastrun.model.UserModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.database

class LoginActivity : AppCompatActivity() {
    private var userName:String ?= null
    private lateinit var email:String
    private lateinit var password:String
    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference
    private lateinit var googleSignInclient: GoogleSignInClient
    private val binding: ActivityLoginBinding by lazy {
        ActivityLoginBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)

        val googleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken("273812913552-u7dfjm254dncudebujgoqqii6lkgjddd.apps.googleusercontent.com").requestEmail().build()
        //initialize Firebase Auth
        auth = Firebase.auth
        //initialize Firebase database
        database = Firebase.database.reference
        // initialize google Sign in
        googleSignInclient = GoogleSignIn.getClient(this,googleSignInOptions)

        binding.loginButton.setOnClickListener {
            // get text from edit text
            email = binding.emailAddress.text.toString().trim()
            password = binding.password.text.toString().trim()

            if (email.isBlank() || password.isBlank()){
                Toast.makeText(this, "Please Fill All Details", Toast.LENGTH_SHORT).show()
            } else {
                createUserAccount(email, password)
                Toast.makeText(this, "Login Successful", Toast.LENGTH_SHORT).show()
            }
        }
        binding.donthavebutton.setOnClickListener {
            val intent = Intent(this,SignActivity::class.java)
            startActivity(intent)
        }

        //google signing
        binding.googleButton.setOnClickListener {
            val signIntent = googleSignInclient.signInIntent
            launcher.launch(signIntent)
        }

    }

    private fun createUserAccount(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful){
                val user = auth.currentUser
                updateUi(user)
            } else{
                auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener { task ->
                    if(task.isSuccessful){
                        saveUserData()
                        val user= auth.currentUser
                        updateUi(user)
                    } else{
                        Toast.makeText(this,"Authentication Failed", Toast.LENGTH_SHORT).show()
                        Log.d("Account", "createUserAccount: Authentication failed", task.exception)
                    }
                }
            }
        }

    }

    private fun saveUserData() {
        // get text from edit text
        email = binding.emailAddress.text.toString().trim()
        password = binding.password.text.toString().trim()

        val user = UserModel(userName,email, password)
        val userId = FirebaseAuth.getInstance().currentUser?.uid
        userId?.let {
            database.child("user").child(it).setValue(user)
        }
    }
    //launcher for google signin
    private val launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            result ->
        Log.d("LauncherCallback", "Launcher callback triggered")
        if (result.resultCode == Activity.RESULT_OK){
            Log.d("GoogleSignIn", "Google sign-in result is OK")
            val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
            if (task.isSuccessful){
                Log.d("GoogleSignIn", "Google sign-in successful")
                val account : GoogleSignInAccount? = task.result
                val credential = GoogleAuthProvider.getCredential(account?.idToken, null)
                auth.signInWithCredential(credential).addOnCompleteListener { authTask ->
                    if(authTask.isSuccessful){
                        Log.d("FirebaseAuth", "Firebase authentication successful")

                        //successfully sign in with Google
                        Toast.makeText(this,"Successfully Sign In With Google", Toast.LENGTH_SHORT).show()
                        updateUi(authTask.result?.user)
                        finish()
                    } else{
                        Log.d("FirebaseAuth", "Firebase authentication failed", authTask.exception)
                        //successfully sign in with Google
                        Toast.makeText(this,"Google Sign In Failed", Toast.LENGTH_SHORT).show()
                    }
                }
            }
            else{
                Log.d("GoogleSignIn", "Google sign-in failed", task.exception)
                //successfully sign in with Google
                Toast.makeText(this,"Google Sign In Failed", Toast.LENGTH_SHORT).show()
            }
        }  else {
            Log.d("GoogleSignIn", "Google sign-in result is not OK")
        }
    }
    //check if user is already logged in
    override fun onStart() {
        super.onStart()
        val currentUser :FirebaseUser? = auth.currentUser
        if (currentUser!=null){
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }


    private fun updateUi(user: FirebaseUser?) {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}


