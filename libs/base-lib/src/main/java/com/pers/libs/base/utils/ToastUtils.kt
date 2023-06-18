package com.pers.libs.base.utils

import android.os.SystemClock
import android.view.Gravity
import android.widget.Toast
import com.pers.libs.base.app.AppConfig

object ToastUtils {

    private var mLastToastTimes: Long = 0
    private var mLastLongToastTimes: Long = 0

    fun showToast(msg: String?) {
        if (msg == null || msg.isEmpty()) {
            return
        }
        val startTime = SystemClock.elapsedRealtime()
        if (startTime - mLastToastTimes in 1..1999) {
            return
        }
        mLastToastTimes = startTime
        val toast = Toast.makeText(AppConfig.application, msg, Toast.LENGTH_SHORT)
        toast.duration = Toast.LENGTH_SHORT
        toast.setText(msg)
        toast.show()
    }

    fun showToastCenter(msg: String?) {
        if (msg == null || msg.isEmpty()) {
            return
        }
        val startTime = SystemClock.elapsedRealtime()
        if (startTime - mLastToastTimes in 1..1999) {
            return
        }
        mLastToastTimes = startTime
        val toast = Toast.makeText(AppConfig.application, msg, Toast.LENGTH_SHORT)
        toast.duration = Toast.LENGTH_SHORT
        toast.setGravity(Gravity.CENTER, 0, 0)
        toast.setText(msg)
        toast.show()
    }


    fun showToastLong(msg: String?) {
        if (msg == null || msg.isEmpty()) {
            return
        }
        val startTime = SystemClock.elapsedRealtime()
        if (startTime - mLastLongToastTimes in 1..3499) {
            return
        }
        mLastLongToastTimes = startTime
        val toast = Toast.makeText(AppConfig.application, msg, Toast.LENGTH_LONG)
        toast.duration = Toast.LENGTH_LONG
        toast.setText(msg)
        toast.show()
    }


    fun showToastLongCenter(msg: String?) {
        if (msg == null || msg.isEmpty()) {
            return
        }
        val startTime = SystemClock.elapsedRealtime()
        if (startTime - mLastLongToastTimes in 1..3499) {
            return
        }
        mLastLongToastTimes = startTime
        val toast = Toast.makeText(AppConfig.application, msg, Toast.LENGTH_LONG)
        toast.duration = Toast.LENGTH_LONG
        toast.setGravity(Gravity.CENTER, 0, 0)
        toast.setText(msg)
        toast.show()
    }
}