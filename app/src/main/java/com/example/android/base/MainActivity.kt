package com.example.android.base

import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Bundle
import com.example.android.base.databinding.ActivityMainBinding
import com.pers.libs.base.BaseActivity
import kotlinx.coroutines.launch

class MainActivity : BaseActivity() {

    private lateinit var binding: ActivityMainBinding


    private fun getApplicationChannel(): String? {
        try {
            val packageManager: PackageManager = packageManager
            val applicationInfo = packageManager
                .getApplicationInfo(packageName, PackageManager.GET_META_DATA)
            return applicationInfo.metaData.getString("UMENG_CHANNEL")
        } catch (_: PackageManager.NameNotFoundException) {
        }
        return ""
    }


    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        enabledAppLifecycleObserver()
        launch {

        }

        binding.textView.text = "渠道号："+getApplicationChannel()


//        val filePath = "/storage/emulated/.../7ac389230d004e2dac6a7c6bb484c017.jpg"
//        val suffix = FileUtils.getSuffixName(filePath)
//        val suffix2 = filePath.suffixName(false)
//
//        Log.e("BBB", "suffix:$suffix")
//        Log.e("BBB", "suffix2:$suffix2")
//        val isDebug = AppConfig.isDebug
//        Log.e("BBB", "isDebug:$isDebug")
//        val packageName = AppConfig.packageName
//        Log.e("BBB", "packageName:$packageName")
//        val versionName = AppConfig.versionName
//        Log.e("BBB", "versionName:$versionName")
//        val versionCode = AppConfig.versionCode
//        Log.e("BBB", "versionCode:$versionCode")
//        val statusBarHeight = AppConfig.statusBarHeight
//        Log.e("BBB", "statusBarHeight:$statusBarHeight")
//        val screenWidth = AppConfig.screenWidth
//        Log.e("BBB", "screenWidth:$screenWidth")
//        val screenHeight = AppConfig.screenHeight
//        Log.e("BBB", "screenHeight:$screenHeight")



//        launch {
//            DataStoreUtils.apply {
//                saveInt("testInt", 18)
//                saveFloat("testFloat", 18F)
//                saveDouble("testDouble", 18.0)
//                saveLong("testLong", 18L)
//                saveBoolean("testBoolean", true)
//                saveString("testString", "哈哈哈哈")
//            }
//
//            DataStoreUtils.apply {
//                Log.e("AAA", "is testInt:${contains("testInt")}")
//                Log.e("AAA", "is testFloat:${contains("testFloat")}")
//                Log.e("AAA", "is testDouble:${contains("testDouble")}")
//                Log.e("AAA", "is testLong:${contains("testLong")}")
//                Log.e("AAA", "is testBoolean:${contains("testBoolean")}")
//                Log.e("AAA", "is testString:${contains("testString")}")
//
//                val testInt = getInt("testInt")
//                val testFloat = getFloat("testFloat")
//                val testDouble = getDouble("testDouble")
//                val testLong = getLong("testLong")
//                val testBoolean = getBoolean("testBoolean")
//                val testString = getString("testString")
////
//                Log.e("AAA", "testInt:$testInt")
//                Log.e("AAA", "testFloat:$testFloat")
//                Log.e("AAA", "testDouble:$testDouble")
//                Log.e("AAA", "testLong:$testLong")
//                Log.e("AAA", "testBoolean:$testBoolean")
//                Log.e("AAA", "testString:$testString")
//            }
//        }

    }

}