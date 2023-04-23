package com.loocan.floatview

import android.app.Activity

interface IFloatWindow {
    fun show(activity: Activity)

    fun hide()

    fun notifyViewParam()
}