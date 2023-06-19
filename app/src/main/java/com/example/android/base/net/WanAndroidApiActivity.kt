package com.example.android.base.net

import android.os.Bundle
import com.example.android.base.databinding.ActivityWanandroidApiBinding
import com.example.android.base.net.bean.UserInfo
import com.pers.libs.base.BaseActivity
import kotlinx.coroutines.launch

class WanAndroidApiActivity : BaseActivity() {

    private lateinit var binding: ActivityWanandroidApiBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWanandroidApiBinding.inflate(layoutInflater)
        setContentLayout(binding.root)

        setTitleText("API 测试")

        binding.tvLogin.setOnClickListener {
            LoginViewModel.login("wuyq007", "wyq19930822")
        }

    }


}