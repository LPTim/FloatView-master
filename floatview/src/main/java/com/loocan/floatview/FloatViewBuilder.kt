package com.loocan.floatview

import android.view.Gravity
import android.view.View
import com.loocan.floatview.base.BaseDrag
import com.loocan.floatview.base.BaseUI
import com.loocan.floatview.listener.OnFloatClickListener

class FloatViewBuilder {
    // view
    var contentView: View? = null

    // 相对位置
    internal var gravity: Int = Gravity.TOP or Gravity.START

    // 绝对位置xy坐标
    internal var absoluteXY: Pair<Int, Int> = Pair(0, 0)

    // 外部阴影0-1
    internal var dimAmount: Float = 0.0f

    // 是否外部可以点击
    internal var touchable: Boolean = true

    //是否可以拖拽
    internal var draggable: BaseDrag? = null

    internal var ui: BaseUI? = null

    var url: String = ""

    internal var listener: OnFloatClickListener? = null

    fun setContentView(view: View): FloatViewBuilder {
        this.contentView = view
        return this
    }

    fun setOnClickListener(listener: OnFloatClickListener): FloatViewBuilder {
        this.listener = listener
        return this
    }

    fun setAbsoluteXY(x: Int, y: Int): FloatViewBuilder {
        absoluteXY = Pair(x, y)
        return this
    }

    fun setDrag(drag: BaseDrag): FloatViewBuilder {
        this.draggable = drag
        return this
    }

    fun setUi(ui: BaseUI): FloatViewBuilder {
        this.ui = ui
        return this
    }

    fun setUrl(url: String): FloatViewBuilder {
        this.url = url
        return this
    }

    fun build(): FloatViewImpl = if (contentView != null) {
        FloatViewImpl(this)
    } else {
        throw NullPointerException("contentView is must not be null")
    }
}