package com.example.android.base

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

object FlowTest {

    @JvmStatic
    fun main(args: Array<String>) {

        GlobalScope.launch {
            createFlow()
        }
    }

    private suspend fun createFlow() {
        flow {
            repeat(10) {
                delay(10)
                emit(it)
            }
        }.collect {
            println("it = $it")
        }
    }
}