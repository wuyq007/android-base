package com.example.android.base

import android.os.Bundle
import com.example.android.base.databinding.ActivityMainBinding
import com.pers.base.lib.BaseActivity

class MainActivity : BaseActivity() {

    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }

}