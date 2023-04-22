package com.pers.libs.base.utils

import android.graphics.Color
import android.os.Build
import android.view.View
import android.view.Window
import android.view.WindowManager
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.annotation.Size
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.pers.libs.base.app.AppConfig


object ScreenUtils {

    fun dp2px(dp: Float): Float {
        val density = AppConfig.application.resources.displayMetrics.density
        return dp * density
    }

    fun dp2px(dp: Int): Int {
        val density = AppConfig.application.resources.displayMetrics.density
        return (dp * density).toInt()
    }

    fun px2dp(px: Float): Float {
        val density = AppConfig.application.resources.displayMetrics.density
        return px / density
    }

    fun px2dp(px: Int): Int {
        val density = AppConfig.application.resources.displayMetrics.density
        return (px / density).toInt()
    }

    fun sp2px(sp: Float): Float {
        val metrics = AppConfig.application.resources.displayMetrics
        val scaledDensity = metrics.scaledDensity
        return sp * scaledDensity
    }

    fun sp2px(sp: Int): Int {
        val metrics = AppConfig.application.resources.displayMetrics
        val scaledDensity = metrics.scaledDensity
        return (sp * scaledDensity).toInt()
    }

    fun px2sp(px: Float): Float {
        val metrics = AppConfig.application.resources.displayMetrics
        val scaledDensity = metrics.scaledDensity
        return px / scaledDensity
    }

    fun px2sp(px: Int): Int {
        val metrics = AppConfig.application.resources.displayMetrics
        val scaledDensity = metrics.scaledDensity
        return (px / scaledDensity).toInt()
    }

    /**
     * 启用沉浸式状态栏
     */
    fun enableStartBarImmersive(activity: AppCompatActivity) {
        val window: Window = activity.window
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.statusBarColor = Color.TRANSPARENT;
            window.attributes.layoutInDisplayCutoutMode = WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES;
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            window.statusBarColor = Color.TRANSPARENT
            val decorView = window.decorView
            decorView.systemUiVisibility =
                (View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR)
        }
    }

    /**
     * 设置状态栏深色模式
     *  @param isDark 是否深色模式
     */
    fun setStartBarModule(activity: AppCompatActivity, isDark: Boolean = false) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            val controller = ViewCompat.getWindowInsetsController(activity.window.decorView)
            if (controller != null) {
                controller.isAppearanceLightStatusBars = isDark
                return
            }
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (isDark) {
                activity.window.decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR)
            } else {
                activity.window.decorView.setSystemUiVisibility(0)
            }
        }
    }

    /**
     * 设置状态栏背景颜色
     */
    fun setStartBarBackgroundColorResource(activity: AppCompatActivity, @ColorRes colorId: Int) {
        setStartBarBackgroundColor(activity, ContextCompat.getColor(activity.applicationContext, colorId))
    }

    fun setStartBarBackgroundColoString(activity: AppCompatActivity, @Size(min = 1) colorString: String) {
        setStartBarBackgroundColor(activity, Color.parseColor(colorString))
    }

    fun setStartBarBackgroundColor(activity: AppCompatActivity, @ColorInt colorInt: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            activity.window.statusBarColor = colorInt
        }
    }

    /**
     * 隐藏导航栏
     */
    fun hideNavigationBar(activity: AppCompatActivity, isHide: Boolean = false) {
        val decorView: View = activity.window.decorView
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            val controller = ViewCompat.getWindowInsetsController(decorView)
            if (controller != null) {
                if (isHide) {
                    // 隐藏导航栏
                    controller.hide(WindowInsetsCompat.Type.navigationBars())
                } else {
                    // 显示导航栏
                    controller.show(WindowInsetsCompat.Type.navigationBars())
                }
                return
            }
        }
        if (isHide) {
            //隐藏导航栏
            decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_FULLSCREEN
        } else {
            //显示导航栏
            decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_VISIBLE
        }
    }

    /**
     * 隐藏状态栏
     */
    fun hideStartBar(activity: AppCompatActivity, isHide: Boolean = false) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            val controller = ViewCompat.getWindowInsetsController(activity.window.decorView)
            if (controller != null) {
                if (isHide) {
                    // 隐藏状态栏
                    controller.hide(WindowInsetsCompat.Type.statusBars())
                } else {
                    // 显示状态栏
                    controller.show(WindowInsetsCompat.Type.statusBars())
                }
                return
            }
        }
        if (isHide) {
            //移除状态栏
            activity.window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
        } else {
            //显示状态栏
            activity.window.clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
        }
    }


}