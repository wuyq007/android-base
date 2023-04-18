package com.pers.libs.base.utils

import android.graphics.Color
import android.os.Build
import android.view.View
import android.view.WindowInsets
import android.view.WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.annotation.Size
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat


object ScreenUtils {

    /**
     * 设置状态栏深色模式
     *  @param isDark 是否深色模式
     */
    fun setStartBarModule(activity: AppCompatActivity, isDark: Boolean = false) {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
//            val controller = activity.window.insetsController
//            // 设置状态栏浅色
//            controller?.setSystemBarsAppearance(
//                APPEARANCE_LIGHT_STATUS_BARS, APPEARANCE_LIGHT_STATUS_BARS
//            )
//            // 取消状态栏深色
//            controller?.setSystemBarsAppearance(0, APPEARANCE_LIGHT_STATUS_BARS)
//
//            // 设置导航栏浅色
//            controller?.setSystemBarsAppearance(
//                APPEARANCE_LIGHT_NAVIGATION_BARS, APPEARANCE_LIGHT_NAVIGATION_BARS
//            )
//            // 取消导航栏深色
//            controller?.setSystemBarsAppearance(0, APPEARANCE_LIGHT_NAVIGATION_BARS)
//            // 同时设置状态栏和导航栏深色
//            controller?.setSystemBarsAppearance(
//                (APPEARANCE_LIGHT_STATUS_BARS or APPEARANCE_LIGHT_NAVIGATION_BARS),
//                (APPEARANCE_LIGHT_STATUS_BARS or APPEARANCE_LIGHT_NAVIGATION_BARS)
//            )
//            // 同时取消状态栏和导航栏浅色
//            controller?.setSystemBarsAppearance(
//                0, (APPEARANCE_LIGHT_STATUS_BARS or APPEARANCE_LIGHT_NAVIGATION_BARS)
//            )
//            // 隐藏状态栏
//            controller?.hide(WindowInsets.Type.statusBars())
//            // 显示状态栏
//            controller?.show(WindowInsets.Type.statusBars())
//            // 隐藏导航栏
//            controller?.hide(WindowInsets.Type.navigationBars())
//            // 显示导航栏
//            controller?.show(WindowInsets.Type.navigationBars())
//            // 同时隐藏状态栏和导航栏
//            controller?.hide(WindowInsets.Type.systemBars())
//            // 同时隐藏状态栏和导航栏
//            controller?.show(WindowInsets.Type.systemBars())
//        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            val controller = activity.window.insetsController
            if (controller != null) {
                val lightModel = APPEARANCE_LIGHT_STATUS_BARS
                if (isDark) { //设置状态栏颜色深色
                    controller.setSystemBarsAppearance(0, lightModel)
                } else {  //设置状态栏颜色浅色
                    controller.setSystemBarsAppearance(lightModel, lightModel)
                }
                return
            }
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (isDark) {
                activity.window.decorView.setSystemUiVisibility(0)
            } else {
                activity.window.decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR)
            }
        }

    }

    /**
     * 设置状态栏颜色
     */
    fun setStartBarColorId(activity: AppCompatActivity, @ColorRes colorId: Int) {
        setStartBarColor(activity, ContextCompat.getColor(activity.applicationContext, colorId))
    }

    fun setStartBarColoString(activity: AppCompatActivity, @Size(min = 1) colorString: String) {
        setStartBarColor(activity, Color.parseColor(colorString))
    }

    fun setStartBarColor(activity: AppCompatActivity, @ColorInt colorInt: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            activity.window.statusBarColor = colorInt
        }
    }

    /**
     * 隐藏导航栏
     */
    fun hideStartBar(activity: AppCompatActivity, isHide: Boolean = false) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            val controller = activity.window.insetsController
            if (controller != null) {
                if (isHide) {
                    // 隐藏状态栏
                    controller.hide(WindowInsets.Type.statusBars())
                } else {
                    // 显示状态栏
                    controller.show(WindowInsets.Type.statusBars())
                }
                return
            }
        }
        val decorView: View = activity.window.decorView
        if (isHide) {
            decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
        } else {
            //显示状态栏和导航栏 or 隐藏 导航栏
            decorView.systemUiVisibility =
                (View.SYSTEM_UI_FLAG_VISIBLE or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION)
        }

    }

}