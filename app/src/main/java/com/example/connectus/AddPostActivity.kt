package com.example.connectus

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.connectus.databinding.ActivityAddPostBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class AddPostActivity : AppCompatActivity() {
    private lateinit var binding:ActivityAddPostBinding
    private lateinit var dbReference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddPostBinding.inflate(layoutInflater)
        setContentView(binding.root)

        dbReference=FirebaseDatabase.getInstance().getReference("posts")

        binding.postBtn.setOnClickListener {
            savePost()
        }
        binding.backBtn.setOnClickListener {
            onBackPressed()
        }
    }

    private fun savePost() {
        var caption = binding.captionEt.text.toString()
        //unique id create krne k lie
        var postId = dbReference.push().key!!
        val user = FirebaseAuth.getInstance().currentUser
        val userName = user?.displayName
        var post = PostData(postId,userName,caption)
        dbReference.child(postId).setValue(post).addOnSuccessListener{
            Toast.makeText(this, "Posted Successfully",Toast.LENGTH_SHORT).show()
            var intent = Intent(this , HomeActivity::class.java)
            startActivity(intent)

        }.addOnFailureListener {
                error->
            Toast.makeText(this,"Failed! Try Again ${error.message}",Toast.LENGTH_SHORT).show()
        }
    }
}
