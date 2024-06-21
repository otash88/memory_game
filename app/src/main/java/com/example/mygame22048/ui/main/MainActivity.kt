package com.example.mygame22048.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.mygame22048.R
import com.example.mygame22048.databinding.ActivityMainBinding
import com.example.mygame22048.ui.screen.start.StartScreen

class MainActivity : AppCompatActivity() {
    private val binding :ActivityMainBinding by viewBinding(ActivityMainBinding::bind)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


    }
}