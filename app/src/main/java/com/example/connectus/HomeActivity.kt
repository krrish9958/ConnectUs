package com.example.connectus

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.connectus.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {
    private  lateinit var binding : ActivityHomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        replaceFragment(HomeFragment())

        binding.bottomNavigationView.setOnItemSelectedListener {
            when(it.itemId){
                R.id.home -> replaceFragment(HomeFragment())
                R.id.profile -> replaceFragment(ProfileFragment())
                else -> {}
            }
            true
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        // ek variable bnaya fragmentManager name se jo equal to hoga supportFragmentMANAGER KE
        val fragmentManager = supportFragmentManager
        // ek or variable bnaya transaction ko start krne k liye
        val fragmentTransaction = fragmentManager.beginTransaction()
        // framelayout ko fragment se replace krdiya
        fragmentTransaction.replace(R.id.frameLayout,fragment)
        // changes ko save krdiya
        fragmentTransaction.commit()
    }
}