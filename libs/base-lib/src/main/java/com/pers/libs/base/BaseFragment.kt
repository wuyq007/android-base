package com.pers.libs.base

import android.os.Bundle
import androidx.fragment.app.Fragment
import kotlinx.coroutines.CompletableJob
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlin.coroutines.CoroutineContext

open class BaseFragment : Fragment(), CoroutineScope {

    private lateinit var job: CompletableJob
    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Main


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        job = SupervisorJob()
    }

    override fun onDestroy() {
        job.cancel()
        super.onDestroy()
    }

}