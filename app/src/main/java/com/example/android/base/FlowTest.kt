package com.example.android.base

import android.util.Log
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.channels.trySendBlocking
import kotlinx.coroutines.flow.*

object FlowTest {

    @JvmStatic
    fun main(args: Array<String>) {
        GlobalScope.launch {
        }
        runBlocking {
            createFlow()
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

        //指定发送类型，必须包含 awaitClose 代码块，做资源释放操作。必须手动 close()
        callbackFlow<String> {

            //类似于 send，但是它是阻塞而不是挂起的，所以它可以用于非挂起的函数
            trySendBlocking("123456")

            //cancel:闭 channel 并向 flow 发送一个异常
            cancel("404")

            // 关闭此 channel,callbackFlow 必须执行 close()，否则 awaitClose 不会执行，当前协程不会结束
            close()

            awaitClose {
                //携程被取消或者flow被关闭，都会执行这个代码块
                //可以在这个代码块里面进行一些资源释放等
                println("awaitClose")
            }
        }.catch {
            println("catch = ${it.message}")
        }.onCompletion {
            println("onCompletion")
        }.collect {
            println("it = $it")
        }

    }

    class Bean(){

    }

}