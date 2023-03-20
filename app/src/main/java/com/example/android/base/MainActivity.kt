package com.example.android.base

import android.os.Bundle
import android.view.View
import com.example.android.base.databinding.ActivityMainBinding
import com.pers.libs.base.BaseActivity
import com.pers.libs.base.app.AppLifecycleCallbacks.addAppLifecycleListener
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.*
import java.net.URL

class MainActivity : BaseActivity() {

    private lateinit var binding: ActivityMainBinding


    private suspend fun getHttp() {
        //1.创建Flow，可指定发射数据类型
        callbackFlow<String?> {
            println("Flow On：" + Thread.currentThread().name)
            //2.网络请求
            var text = URL("https://static.paiyaapp.com/music/songs.json").readText()
            send(text)
            //5.关闭Flow,可以在 awaitClose{} 中执行资源回收, close() awaitClose{} 是成对出现
            close() //执行 close()，才会调用 awaitClose{}
            awaitClose { //协程被取消或者flow被关闭，都会执行这个代码块，可以在这个代码块里面进行一些资源释放等
                println("awaitClose：" + Thread.currentThread().name)
            }
        }.flowOn(Dispatchers.IO)//6.flowOn：指定上游的线程，flowOn允许出现多次，每次执行切换，都会改变前面的线程
            .onStart {
                println("onStart：" + Thread.currentThread().name)
            }.catch {
                //7.处理异常 catch 到 Flow上游的异常，这里会切换到调用Flow前的线程
                println("catch：${it.message}  " + Thread.currentThread().name)
            }.onCompletion {
                //8.最后执行，与 finally 作用一致，这里会切换到调用Flow前的线程
                println("onCompletion：" + Thread.currentThread().name)
            }.collect {
                //9.获取到数据，这里会切换到调用Flow前的线程
                println("collect：" + Thread.currentThread().name)
            }
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        addAppLifecycleObserver{ view ->

        }

        fun setOnClickListener(l: View.OnClickListener?) {
            if (!isClickable()) {
                setClickable(true)
            }
            getListenerInfo().mOnClickListener = l
        }

//        Log.e("CCC", "deviceId1:${AppConfig.deviceId}")
//        Log.e("CCC", "deviceId2:${AppConfig.deviceId}")
//        Log.e("CCC", "deviceId3:${AppConfig.deviceId}")
//        Log.e("CCC", "deviceId4:${AppConfig.deviceId}")
//        Log.e("CCC", "deviceId5:${AppConfig.deviceId}")

//        launch {
//            getHttp()
//        }


//        Log.e("CCC", "serial:${SystemInfo.serial}")
//        Log.e("CCC", "fingerprint:${SystemInfo.fingerprint}")
//        Log.e("CCC", "board:${SystemInfo.board}")
//        Log.e("CCC", "hardware:${SystemInfo.hardware}")
//        Log.e("CCC", "systemLanguage:${SystemInfo.systemLanguage}")
//        Log.e("CCC", "systemLanguageList:${SystemInfo.systemLanguageList}")
//        Log.e("CCC", "systemVersion:${SystemInfo.systemVersion}")
//        Log.e("CCC", "systemModel:${SystemInfo.systemModel}")
//        Log.e("CCC", "deviceBrand:${SystemInfo.deviceBrand}")
//        Log.e("CCC", "deviceManufacturer:${SystemInfo.deviceManufacturer}")

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
//            val testFloat = DataStoreUtils.getFloat("testFlow")
//            Log.e("flow", "testFloat: $testFloat")
//
//        }


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