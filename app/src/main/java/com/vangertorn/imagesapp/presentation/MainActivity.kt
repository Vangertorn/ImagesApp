package com.vangertorn.imagesapp.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.vangertorn.imagesapp.R
import com.vangertorn.imagesapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}
