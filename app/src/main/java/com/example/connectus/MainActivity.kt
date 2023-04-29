package com.example.connectus

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.connectus.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.in
        setContentView(R.layout.activity_main)
    }
}