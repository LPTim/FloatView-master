package com.loocan.floatview_master.ex

import android.content.Context
import android.content.res.Resources
import android.util.TypedValue
import com.loocan.floatview_master.App

val Float.dp
    get() = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        this,
        Resources.getSystem().displayMetrics,
    )

fun Int.dp(): Int {
    return (this * App.instance.resources.displayMetrics.density + 0.5f).toInt()
}

fun Float.dp(): Int {
    return (this * App.instance.resources.displayMetrics.density + 0.5f).toInt()
}

// 根据手机的分辨率从 px(像素) 的单位 转成为 dp
fun Int.px2dip(context: Context): Int {
    return (this / context.resources.displayMetrics.density + 0.5f).toInt()
}

fun Float.px2dip(context: Context): Int {
    return (this / context.resources.displayMetrics.density + 0.5f).toInt()
}
