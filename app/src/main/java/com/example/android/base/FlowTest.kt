package com.example.android.base

import com.google.gson.GsonBuilder
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.*
import java.net.URL
import kotlin.random.Random
import kotlin.system.measureTimeMillis

object FlowTest {

    @JvmStatic
    fun main(args: Array<String>) {
        runBlocking(Dispatchers.Default) {
//            createFlow()
//            receiveFlow()
            launch(Dispatchers.IO){

            }

//            println(1)
//            val jobAtomic = launch(Dispatchers.Default, CoroutineStart.ATOMIC) {
//                println(2)
//                delay(1)
//                println(22)
//            }
//            jobAtomic.cancel()
//            println(3)

//            println(1)
//            val job = launch(Dispatchers.Default, CoroutineStart.DEFAULT) {
//                println(2)
//                println(22)
//            }
//            job.cancel()
//            println(3)

//            println(1)
//            val job = launch(start = CoroutineStart.DEFAULT) {
//                delay(50)
//                println(2)
//            }
//            job.cancel()
//            println(3)


//            flowOf(1, 2, 3, 4, 5).map {
//                it.toString() + "A"
//            }.collectIndexed { index, value ->
//                println("index = $index; value = $value")
//            }

//            val time = measureTimeMillis {
//                getJoined()
//            }
//            println("getJoined 总耗时：$time")
//            getHttp()
        }

//        try {
//
//        } catch (_: Exception) {
//
//        } finally {
//            println("回收资源")
//        }

    }


    /**
     * 接收操作符
     */
    private suspend fun receiveFlow() {
//        //接收数据会阻塞，直到接收到数据为止
//        val startTime = System.currentTimeMillis()
//        flow {
//            for (i in 1..5) {
//                delay(1000)
//                val emitTime = System.currentTimeMillis()
//                println("emit = $i; time:${emitTime - startTime}")
//                emit(i)
//            }
//        }.collect {
//            delay(1000)
//            val endTime = System.currentTimeMillis()
//            println("collect = $it; time:${endTime - startTime}")
//        }
//
//        flowOf(1, 2, 3, 4, 5).collectIndexed { index, value ->
//            delay(1000)
//            println("index = $index; value = $value")
//        }

        //可以实现，在发来的数据你来不及接收的时候，立刻停止你正在忙的工作，马上去从接收数据开始重新走一遍逻辑（相当于重启接收了）
        //
        //数据来不及处理的时候只会保存最新的数据
        //不阻塞，优先接收数据，再处理
//        val startTime = System.currentTimeMillis()
//        flowOf(1, 2, 3, 4, 5).collectLatest {
//            val endTime = System.currentTimeMillis()
//            println("value = $it; time:${endTime - startTime}")
//        }

//        val startTime = System.currentTimeMillis()
//        flow {
//            for (i in 1..5) {
//                delay(1000)
//                val emitTime = System.currentTimeMillis()
//                println("emit = $i; time:${emitTime - startTime}")
//                emit(i)
//            }
//        }.collectLatest {
//            val endTime = System.currentTimeMillis()
//            println("value = $it; time:${endTime - startTime}")
//            delay(1000)
//            val endTime2 = System.currentTimeMillis()
//            println("delay value = $it; time:${endTime2 - startTime}")
//        }

//        val startTime = System.currentTimeMillis()
//        flow {
//            for (i in 1..5) {
//                delay(200)
//                emit(i)
//            }
//        }.collect {
//            val endTime = System.currentTimeMillis()
//            println("it = $it; time:${endTime - startTime}")
//            delay(1000)
//
//            val endTime2 = System.currentTimeMillis()
//            println("value = $it; time:${endTime2 - startTime}")
//        }


//        val single: String = flow {
//            emit("A")
////            emit("B")
//        }.single()
//        println("single = $single")

//
//        val singleOrNull: String? = flow {
//            emit(null)
//        }.singleOrNull()
//        println("singleOrNull = firstOrNull")


        val singleOrNull: String = flow {
            emit("A")
            emit("B")
        }.first()
//        println("singleOrNull = $singleOrNull")

//        val test: Any? = null
//        val first = flow {
//            emit(test)
//            emit("B")
//        }.first()
//        println("first = $first")
//
//        val firstOrNull: String? = flow {
//            emit(null)
//            emit("B")
//        }.firstOrNull()
//        println("firstOrNull = $firstOrNull")


//        val reduce = flow {
//            emit("A")
//            emit("B")
//            emit("C")
//            emit("D")
//        }.reduce { accumulator, value ->
//            println("accumulator = $accumulator; value = $value")
//            accumulator + value
//        }
//        println("reduce = $reduce")


        val reduce = flowOf(1, 2, 3, 4, 5).reduce { accumulator, value ->
            println("accumulator = $accumulator; value = $value")
            accumulator + value
        }
//        println("reduce = $reduce")
//
//        val fold = flowOf(1, 2, 3, 4, 5).fold("A") { accumulator, value ->
//            println("accumulator = $accumulator; value = $value")
//            accumulator + value
//        }
//        println("fold = $fold")


//        val fold = flowOf(null,1,2,3,4,5).fold(100) { accumulator, value ->
//            println("accumulator = $accumulator; value = $value")
//            if (value == null) {
//                accumulator
//            } else {
//                accumulator + value
//            }
//        }
//        println("reduce = $fold")


//        val destination: MutableList<Int> = arrayOf(-1, -2, -3, -4, -5).toList() as MutableList<Int>
//        val toList: MutableList<Int> = flowOf(1, 2, 3, 4, 5).toList(destination) as MutableList<Int>
//        println("toList = $toList")

//        val destination: MutableSet<Int> = arrayOf(-1, -2, -3, -4, -5).toSet() as MutableSet<Int>
//        val toSet: Set<Int> = flowOf(1, 2, 3, 4, 5).toSet(destination)
//        println("toSet = $toSet")


//        val array = arrayListOf(0)
//        flowOf(1, 2, 3, 4, 5).toCollection(array)
//        println("toCollection：$array")

//        //直接触发流的执行，不设置action,入参为coroutineScope 一般不会直接调用，会搭配别的操作符一起使用，如onEach,onCompletion 。返回值是Job
//        val coroutineScope = CoroutineScope( Dispatchers.IO)
//        flowOf(1, 2, 3, 4, 5).launchIn(coroutineScope)
//        coroutineScope.launch {
//
//        }

//        val last = flowOf("A", "B", "C", "D").last()
//        println("first = $last")
//        val lastOrNull = flowOf("A", "B", "C", null).lastOrNull()
//        println("lastOrNull = $lastOrNull")

//        val count = flow {
//            for (i in 1..10) {
//                emit(i)
//            }
//        }.count()
//        println("count = $count")
    }


    /**
     * 创建操作符
     */
    private suspend fun createFlow() {

//        //发送数据会阻塞，直到发送的数据被接收为止
//        val startTime = System.currentTimeMillis()
//        flow {
//            for (i in 1..5) {
//                delay(1000)
//                val emitTime = System.currentTimeMillis()
//                println("emit = $i; time:${emitTime - startTime}")
//                emit(i)
//            }
//        }.collect {
//            val endTime = System.currentTimeMillis()
//            println("collect = $it; time:${endTime - startTime}")
//        }

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


//            emptyFlow<Int>().collectIndexed { index, value ->
//                println("emptyFlow = $index; value = $value")
//            }
//
//            val emptyFlow = emptyFlow<Int>().firstOrNull()
//            println("emptyFlow：$emptyFlow")
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