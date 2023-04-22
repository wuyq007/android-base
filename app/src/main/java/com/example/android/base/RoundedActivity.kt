package com.example.android.base

import android.os.Bundle
import com.example.android.base.databinding.ActivityRoundedBinding
import com.pers.libs.base.BaseActivity

class RoundedActivity : BaseActivity() {


    private lateinit var binding: ActivityRoundedBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRoundedBinding.inflate(layoutInflater)
        setContentLayout(binding.root)

    }

}