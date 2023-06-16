package com.example.android.base

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import com.example.android.base.databinding.ActivityMainBinding
import com.pers.libs.base.BaseActivity
import com.pers.libs.base.app.AppConfig
import com.pers.libs.base.app.AppSystemInfo
import com.pers.libs.base.app.AppSystemInfo.buildId
import com.pers.libs.base.app.AppSystemInfo.systemVersion
import com.pers.libs.base.utils.hideNavigationBar
import kotlinx.coroutines.Dispatchers
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
            launch(Dispatchers.IO) {
                fetchData()
                Log.e("AAA", "launch 1")
            }
            launch(Dispatchers.IO) {
                Log.e("AAA", "launch 2")
            }
            launch(Dispatchers.IO) {
                Log.e("AAA", "launch 3")
            }
            launch(Dispatchers.IO) {
                Log.e("AAA", "launch 4")
            }

            Log.e("AAA", "launch 5")
        }

        binding.tvContent.setOnClickListener {
            startActivity(Intent(this, SmsActivity2::class.java))
        }

    }

    private suspend inline fun fetchData(): String {
        delay(3000L)
        Log.e("AAA", "fetchData")
        return "fetchData"
    }

    private fun setContent() {
        val stringBuilder = StringBuilder()
        stringBuilder.append("API 版本：").append(Build.VERSION.SDK_INT).append("\n")
        stringBuilder.append("systemVersion：").append(systemVersion).append("\n")
        stringBuilder.append("buildId：").append(buildId).append("\n")
        stringBuilder.append("screenWidth：").append(screenWidth).append("\n")
        stringBuilder.append("screenHeight：").append(screenHeight).append("\n")
        stringBuilder.append("statusBarHeight：").append(AppConfig.statusBarHeight).append("\n")
        binding.tvContent.text = stringBuilder.toString()
    }

}