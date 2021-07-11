package com.example.practicefornike1.ui.auth

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.practicefornike1.R
import com.example.practicefornike1.databinding.ActivityAuthenticationBinding

class AuthenticationActivity : AppCompatActivity() {

    lateinit var binding:ActivityAuthenticationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityAuthenticationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fragment_container,LoginFragment())
        }.commit()
    }
}