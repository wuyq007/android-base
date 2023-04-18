package com.example.android.base

import android.annotation.SuppressLint
import android.os.Bundle
import com.example.android.base.databinding.ActivityMainBinding
import com.pers.libs.base.BaseActivity
import kotlinx.coroutines.launch

class MainActivity : BaseActivity() {

    private lateinit var binding: ActivityMainBinding

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        enabledAppLifecycleObserver()

        launch {

        }
        setFitsSystemWindows(binding.titleLayout)
        binding.tvContent.text = "渠道号："
    }

}