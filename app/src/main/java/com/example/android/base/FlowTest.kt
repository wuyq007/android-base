package com.example.android.base

import com.google.gson.GsonBuilder
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.*
import java.net.URL
import kotlin.random.Random

object FlowTest {

    @JvmStatic
    fun main(args: Array<String>) {
        runBlocking(Dispatchers.Default) {
//            createFlow()

//            val time = measureTimeMillis {
//                getJoined()
//            }
//            println("getJoined 总耗时：$time")

            getHttp()
        }
    }

    private suspend fun createFlow() {
//        flow {
//            for (i in 1..5) {
//                emit(i)
//            }
//        }.collect {
//            println("it = $it")
//        }

//        flowOf(1, 2, 3, 4, 5).collect {
//            println("it = $it")
//        }

//        listOf(1, 2, 3, 4, 5).asFlow().collect {
//            println("it = $it")
//        }

//        channelFlow {
//            for (i in 1..5) {
//                send(i)
//            }
//            close()
//            send("123456")
//            awaitClose {
//                //携程被取消或者flow被关闭，都会执行这个代码块
//                //可以在这个代码块里面进行一些资源释放的操作等等
//                println("awaitClose")
//            }
//        }.collect {
//            println("it = $it")
//        }

//        //指定发送类型，必须包含 awaitClose 代码块，做资源释放操作。必须手动 close()
//        callbackFlow<String> {
//            //类似于 send，但是它是阻塞而不是挂起的，所以它可以用于非挂起的函数
//            trySendBlocking("123456")
//
//            //cancel:闭 channel 并向 flow 发送一个异常
//            cancel("404")
//
//            // 关闭此 channel,callbackFlow 必须执行 close()，否则 awaitClose 不会执行，当前协程不会结束
//            close()
//
//            awaitClose {
//                //携程被取消或者flow被关闭，都会执行这个代码块
//                //可以在这个代码块里面进行一些资源释放等
//                println("awaitClose")
//            }
//        }.catch {
//            println("catch = ${it.message}")
//        }.onCompletion {
//            println("onCompletion")
//        }.collect {
//            println("it = $it")
//        }
    }


    private suspend fun getHttp() {
        //1.创建Flow，可指定发射数据类型
        callbackFlow<InfoBean?> {
            println("Flow On：" + Thread.currentThread().name)
            //2.网络请求
            var url: URL = URL("https://static.paiyaapp.com/music/songs.json")
            var text = ""
//            if (text == "") {
//                // 3.主动抛出异常，会回调到 catch{} 中,会结束协程，但不会执行awaitClose{}代码，如果需要释放资源，需要在onCompletion{}中
//                cancel("数据为空")
//            }
            val infoBean = GsonBuilder().create().fromJson(text, InfoBean::class.java)
            //4.发送数据,在 collect{} 中接收
            send(infoBean)
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

    class InfoBean {
        var songs: MutableList<ItemBean>? = null

        object ItemBean {
            var songID: String? = null
            var name: String? = null
            var artist: String? = null
            var isDbContain = 0
            var duration = 0
            var localPath: String? = null
            var cover: String? = null
            var url: String? = null
        }
    }


    private suspend fun getJoined() = withContext(Dispatchers.IO) {
        val jobList: MutableList<Deferred<String>> = ArrayList()
        val stringList: MutableList<String> = ArrayList()
        for (i in 1..10) {
            jobList.add(async { getJoined(i) })
        }
        jobList.forEach {
            stringList.add(it.await())
        }
        println("stringList = $stringList")
    }

    /**
     * 耗时操作
     */
    private suspend fun getJoined(index: Int): String {
        //挂起时间随机
        val randomTime = Random.nextInt(1, 5) * 1000L
        delay(randomTime)
        println("getJoined 协程：$index；耗时：${randomTime}")
        return "respond:$index"
    }


}