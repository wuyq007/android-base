package com.example.android.base

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.view.View
import android.view.WindowInsets
import android.view.WindowInsetsController
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.example.android.base.databinding.ActivityMainBinding
import com.pers.libs.base.BaseActivity
import com.pers.libs.base.app.AppConfig
import com.pers.libs.base.utils.ScreenUtils
import com.pers.libs.base.utils.hideNavigationBar
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class MainActivity : BaseActivity() {

    private lateinit var binding: ActivityMainBinding

    var screenWidth = AppConfig.screenWidth
    var screenHeight = AppConfig.screenHeight

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentLayout(binding.root)
        setStartBarModule(true)

        setContent()

        hideNavigationBar()

        launch {

        }

        binding.tvContent.setOnClickListener {
            startActivity(Intent(this,RoundedActivity::class.java))
        }

    }

    private fun setContent() {
        val stringBuilder = StringBuilder()
        stringBuilder.append("API 版本：").append(Build.VERSION.SDK_INT).append("\n")
        stringBuilder.append("screenWidth：").append(screenWidth).append("\n")
        stringBuilder.append("screenHeight：").append(screenHeight).append("\n")
        stringBuilder.append("statusBarHeight：").append(AppConfig.statusBarHeight).append("\n")
        binding.tvContent.text = stringBuilder.toString()
    }

}