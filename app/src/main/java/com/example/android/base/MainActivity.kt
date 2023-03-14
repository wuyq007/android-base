package com.example.android.base

import android.os.Bundle
import android.util.Log
import com.example.android.base.databinding.ActivityMainBinding
import com.pers.base.lib.BaseActivity
import com.pers.base.lib.utils.DataStoreUtils
import kotlinx.coroutines.launch

class MainActivity : BaseActivity() {

    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        launch {
            DataStoreUtils.apply {
                saveInt("testInt", 18)
                saveFloat("testFloat", 18F)
                saveDouble("testDouble", 18.0)
                saveLong("testLong", 18L)
                saveBoolean("testBoolean", true)
                saveString("testString", "哈哈哈哈")
            }

            DataStoreUtils.apply {
                Log.e("AAA", "is testInt:${contains("testInt")}")
                Log.e("AAA", "is testFloat:${contains("testFloat")}")
                Log.e("AAA", "is testDouble:${contains("testDouble")}")
                Log.e("AAA", "is testLong:${contains("testLong")}")
                Log.e("AAA", "is testBoolean:${contains("testBoolean")}")
                Log.e("AAA", "is testString:${contains("testString")}")

                val testInt =getInt("testInt")
                val testFloat = getFloat("testFloat")
                val testDouble = getDouble("testDouble")
                val testLong = getLong("testLong")
                val testBoolean = getBoolean("testBoolean")
                val testString = getString("testString")
//
                Log.e("AAA", "testInt:$testInt")
                Log.e("AAA", "testFloat:$testFloat")
                Log.e("AAA", "testDouble:$testDouble")
                Log.e("AAA", "testLong:$testLong")
                Log.e("AAA", "testBoolean:$testBoolean")
                Log.e("AAA", "testString:$testString")
            }

        }

    }

}