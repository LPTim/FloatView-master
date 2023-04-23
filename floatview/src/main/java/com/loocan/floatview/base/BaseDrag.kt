package com.loocan.floatview.base

import android.view.View
import com.loocan.floatview.FloatViewBuilder
import com.loocan.floatview.FloatViewImpl

/**
 * @Data：2023/4/21 10:45
 * @author liupeng
 * @Description：
 */

abstract class BaseDrag : View.OnTouchListener {

    var mBuilder: FloatViewBuilder? = null
    var mFloatViewImpl: FloatViewImpl? = null

    fun bindTouch(builder: FloatViewBuilder, floatViewImpl: FloatViewImpl) {
        mBuilder = builder
        mFloatViewImpl = floatViewImpl
        builder.contentView?.setOnTouchListener(this)
    }

    /**
     * 更新位置信息
     */
    protected fun updateLocation(x: Int, y: Int) {
        mBuilder?.setAbsoluteXY(x, y)

        mFloatViewImpl?.notifyView()
    }
}