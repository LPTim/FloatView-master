package com.loocan.floatview.base

import android.view.View
import com.loocan.floatview.FloatViewBuilder

abstract class BaseUI {
    abstract fun onCreate(view: View,builder: FloatViewBuilder)
}