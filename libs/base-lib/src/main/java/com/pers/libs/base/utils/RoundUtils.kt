package com.pers.libs.base.utils

import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.view.View
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.annotation.Size
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import com.google.android.material.shape.MaterialShapeDrawable
import com.google.android.material.shape.ShapeAppearanceModel
import com.noober.background.drawable.DrawableCreator


/**
 * 设置背景-圆角
 */
fun View.setCornerRadiusResource(@ColorRes colorId: Int, cornerRadius: Float = 0F) {
    RoundUtils.setCornerRadius(this, ContextCompat.getColor(this.context, colorId), cornerRadius)
}

fun View.setCornerRadius(@Size(min = 1) colorString: String, cornerRadius: Float = 0F) {
    RoundUtils.setCornerRadius(this, colorString, cornerRadius)
}

fun View.setCornerRadius(@ColorInt colorInt: Int, cornerRadius: Float = 0F) {
    RoundUtils.setCornerRadius(this, colorInt, cornerRadius)
}

fun View.setCornerTopRadiusResource(@ColorRes colorId: Int, cornerRadius: Float = 0F) {
    RoundUtils.setCornerTopRadius(this, ContextCompat.getColor(this.context, colorId), cornerRadius)
}

fun View.setCornerTopRadius(@Size(min = 1) colorString: String, cornerRadius: Float = 0F) {
    RoundUtils.setCornerTopRadius(this, colorString, cornerRadius)
}

fun View.setCornerTopRadius(@ColorInt colorInt: Int, cornerRadius: Float = 0F) {
    RoundUtils.setCornerTopRadius(this, colorInt, cornerRadius)
}

fun View.setCornerBottomRadiusResource(@ColorRes colorId: Int, cornerRadius: Float = 0F) {
    RoundUtils.setCornerBottomRadius(this, ContextCompat.getColor(this.context, colorId), cornerRadius)
}

fun View.setCornerBottomRadius(@Size(min = 1) colorString: String, cornerRadius: Float = 0F) {
    RoundUtils.setCornerBottomRadius(this, colorString, cornerRadius)
}

fun View.setCornerBottomRadius(@ColorInt colorInt: Int, cornerRadius: Float = 0F) {
    RoundUtils.setCornerBottomRadius(this, colorInt, cornerRadius)
}

fun View.setCornerLeftRadiusResource(@ColorRes colorId: Int, cornerRadius: Float = 0F) {
    RoundUtils.setCornerLeftRadius(this, ContextCompat.getColor(this.context, colorId), cornerRadius)
}

fun View.setCornerLeftRadius(@Size(min = 1) colorString: String, cornerRadius: Float = 0F) {
    RoundUtils.setCornerLeftRadius(this, colorString, cornerRadius)
}

fun View.setCornerLeftRadius(@ColorInt colorInt: Int, cornerRadius: Float = 0F) {
    RoundUtils.setCornerLeftRadius(this, colorInt, cornerRadius)
}

fun View.setCornerRightRadiusResource(@ColorRes colorId: Int, cornerRadius: Float = 0F) {
    RoundUtils.setCornerRightRadius(this, ContextCompat.getColor(this.context, colorId), cornerRadius)
}

fun View.setCornerRightRadius(@Size(min = 1) colorString: String, cornerRadius: Float = 0F) {
    RoundUtils.setCornerRightRadius(this, colorString, cornerRadius)
}

fun View.setCornerRightRadius(@ColorInt colorInt: Int, cornerRadius: Float = 0F) {
    RoundUtils.setCornerRightRadius(this, colorInt, cornerRadius)
}


/**
 * 设置背景边框
 */
fun View.setStrokeResource(@ColorRes colorId: Int, strokeWidth: Float, @ColorRes strokeColorId: Int) {
    RoundUtils.setStroke(
        this, ContextCompat.getColor(this.context, colorId), strokeWidth, ContextCompat.getColor(this.context, strokeColorId)
    )
}

fun View.setStroke(@ColorInt colorInt: Int, strokeWidth: Float, @ColorInt strokeColor: Int) {
    RoundUtils.setStroke(this, colorInt, strokeWidth, strokeColor)
}


/**
 * 设置背景-圆角-边框
 */
fun View.setCornerRadiusStrokeResource(@ColorRes colorId: Int, cornerRadius: Float, strokeWidth: Float, @ColorRes strokeColorId: Int) {
    RoundUtils.setCornerRadiusStroke(
        this, ContextCompat.getColor(this.context, colorId), cornerRadius, strokeWidth, ContextCompat.getColor(this.context, strokeColorId)
    )
}

fun View.setCornerRadiusStroke(@ColorInt colorInt: Int, cornerRadius: Float, strokeWidth: Float, @ColorInt strokeColor: Int) {
    RoundUtils.setCornerRadiusStroke(this, colorInt, cornerRadius, strokeWidth, strokeColor)
}


object RoundUtils {

    /**
     * 设置View的圆角及背景色
     */
    fun setCornerRadius(view: View, @Size(min = 1) colorString: String, cornerRadius: Float = 0F) {
        setCornerRadius(view, Color.parseColor(colorString), cornerRadius)
    }

    fun setCornerRadius(view: View, @ColorInt colorInt: Int, cornerRadius: Float = 0F) {
        setCornerRadius(view, colorInt, cornerRadius, cornerRadius, cornerRadius, cornerRadius)
    }

    /**
     * 设置上边圆角
     */
    fun setCornerTopRadius(view: View, @Size(min = 1) colorString: String, cornerRadius: Float = 0F) {
        setCornerTopRadius(view, Color.parseColor(colorString), cornerRadius)
    }

    fun setCornerTopRadius(view: View, @ColorInt colorInt: Int, cornerRadius: Float = 0F) {
        setCornerRadius(view, colorInt, cornerRadius, cornerRadius, 0F, 0F)
    }

    /**
     * 设置底部圆角
     */
    fun setCornerBottomRadius(view: View, @Size(min = 1) colorString: String, cornerRadius: Float = 0F) {
        setCornerBottomRadius(view, Color.parseColor(colorString), cornerRadius)
    }

    fun setCornerBottomRadius(view: View, @ColorInt colorInt: Int, cornerRadius: Float = 0F) {
        setCornerRadius(view, colorInt, 0F, 0F, cornerRadius, cornerRadius)
    }

    /**
     * 设置左边圆角
     */
    fun setCornerLeftRadius(view: View, @Size(min = 1) colorString: String, cornerRadius: Float = 0F) {
        setCornerLeftRadius(view, Color.parseColor(colorString), cornerRadius)
    }

    fun setCornerLeftRadius(view: View, @ColorInt colorInt: Int, cornerRadius: Float = 0F) {
        setCornerRadius(view, colorInt, cornerRadius, 0F, cornerRadius, 0F)
    }


    /**
     * 设置右边圆角
     */
    fun setCornerRightRadius(view: View, @Size(min = 1) colorString: String, cornerRadius: Float = 0F) {
        setCornerRightRadius(view, Color.parseColor(colorString), cornerRadius)
    }

    fun setCornerRightRadius(view: View, @ColorInt colorInt: Int, cornerRadius: Float = 0F) {
        setCornerRadius(view, colorInt, 0F, cornerRadius, 0F, cornerRadius)
    }


    /**
     * 设置背景+边框
     */
    fun setStroke(
        view: View, @ColorInt colorInt: Int, strokeWidth: Float, @Size(min = 1) strokeColorString: String
    ) {
        setStroke(view, colorInt, strokeWidth, Color.parseColor(strokeColorString))
    }

    fun setStroke(
        view: View, @ColorInt colorInt: Int, strokeWidth: Float, @ColorInt strokeColor: Int
    ) {
        setCornerRadius(view, colorInt, 0F, 0F, 0F, 0F, strokeWidth, strokeColor)
    }


    /**
     * 设置背景+圆角+边框
     */
    fun setCornerRadiusStroke(
        view: View, @ColorInt colorInt: Int, cornerRadius: Float, strokeWidth: Float, @ColorInt strokeColor: Int
    ) {
        setCornerRadius(view, colorInt, cornerRadius, cornerRadius, cornerRadius, cornerRadius, strokeWidth, strokeColor)
    }


    fun setCornerRadius(
        view: View,
        @ColorInt colorInt: Int,
        topLeftCorner: Float,
        topRightCorner: Float,
        bottomLeftCorner: Float,
        bottomRightCorner: Float,
        strokeWidth: Float = 0F,
        @ColorInt strokeColor: Int = Color.TRANSPARENT
    ) {
        val shapeAppearanceModel = ShapeAppearanceModel().toBuilder()
            // 左上角圆角大小
            .setTopLeftCornerSize(topLeftCorner)
            // 右上角圆角大小
            .setTopRightCornerSize(topRightCorner)
            // 左下角圆角
            .setBottomLeftCornerSize(bottomRightCorner)
            // 右下角圆角
            .setBottomRightCornerSize(bottomLeftCorner).build()

        val shapeDrawable = MaterialShapeDrawable()
        shapeDrawable.shapeAppearanceModel = shapeAppearanceModel
        shapeDrawable.fillColor = ColorStateList.valueOf(colorInt)
        shapeDrawable.setStroke(strokeWidth, strokeColor);
        ViewCompat.setBackground(view, shapeDrawable)
    }


    /**
     * 设置渐变色
     */
    fun setGradientBackground(
        view: View,
        orientation: GradientDrawable.Orientation,
        @ColorInt colors: IntArray,
        cornerRadius: Float,
    ) {
        val gradientDrawable = GradientDrawable(orientation, colors)
        gradientDrawable.cornerRadius = cornerRadius
        val drawable: Drawable = DrawableCreator.Builder().setBaseGradientDrawable(gradientDrawable).build()
        ViewCompat.setBackground(view, drawable)
//        val shapeAppearanceModel = ShapeAppearanceModel().toBuilder()
//            // 左上角圆角大小
//            .setTopLeftCornerSize(topLeftCorner)
//            // 右上角圆角大小
//            .setTopRightCornerSize(topRightCorner)
//            // 左下角圆角
//            .setBottomLeftCornerSize(bottomRightCorner)
//            // 右下角圆角
//            .setBottomRightCornerSize(bottomLeftCorner).build()
//
//        // 渐变色数组，包括起始颜色和结束颜色
//        val gradientDrawable = GradientDrawable(orientation, colors)
//        // 设置渐变类型为线性渐变
//        gradientDrawable.gradientType = GradientDrawable.LINEAR_GRADIENT;
//
//        val shapeDrawable = MaterialShapeDrawable()
//
//        //设置填充色透明
//        shapeDrawable.fillColor = ColorStateList.valueOf(Color.TRANSPARENT)
//        //设置圆角
//        shapeDrawable.shapeAppearanceModel = shapeAppearanceModel
//        // 设置渐变颜色覆盖在边框上
//        shapeDrawable.setTintMode(PorterDuff.Mode.SRC_ATOP)
//        shapeDrawable.tintList = ColorStateList.valueOf(Color.TRANSPARENT)
//        // 设置填充样式为渐变
//        shapeDrawable.paintStyle = Paint.Style.FILL;
//        shapeDrawable.setShaderFactory(ShapeAppearanceShaderFactory(gradientDrawable, shapeAppearanceModel))
//        shapeDrawable.setDrawable(gradientDrawable);
    }

}