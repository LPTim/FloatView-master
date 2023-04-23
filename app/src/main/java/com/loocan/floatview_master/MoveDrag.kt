package com.loocan.floatview_master

import android.animation.ValueAnimator
import android.view.MotionEvent
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import com.loocan.floatview.base.BaseDrag
import kotlin.math.sqrt

class MoveDrag : BaseDrag() {

    private var downX = 0f
    private var downY = 0f

    // 是否需要自动吸边
    private var mCustomIsAttach = true

    private var isDrug = false

    private var mScreenWidth = 0

    private var mStatusBarHeight = 0

    init {
        mScreenWidth = SystemUtils.getScreenWidth(App.instance)
        mStatusBarHeight = SystemUtils.getStatusBarHeight(App.instance)
    }

    override fun onTouch(v: View?, event: MotionEvent?): Boolean {
        when (event?.action) {
            MotionEvent.ACTION_DOWN -> {
                isDrug = false
                downX = event.x
                downY = event.y
            }

            MotionEvent.ACTION_MOVE -> {
                val moveX = event.rawX
                val moveY = event.rawY

                // 手指X轴滑动距离
                val differenceValueX = moveX - downX
                // 手指Y轴滑动距离
                val differenceValueY = moveY - downY
                // 判断是否为拖动操作
                if (!isDrug) {
                    isDrug =
                        sqrt(((differenceValueX * differenceValueX) + (differenceValueY * differenceValueY)).toDouble()) >= 2
                }
                updateLocation(
                    differenceValueX.toInt(),
                    (differenceValueY - mStatusBarHeight).toInt()
                )
            }

            MotionEvent.ACTION_CANCEL,
            MotionEvent.ACTION_UP,
            -> {
                // 根据自定义属性判断是否需要贴边           // 判断是否为点击事件
                if (mCustomIsAttach && isDrug) {
                    val moveX = event.rawX
                    val moveY = event.rawY
                    autoMoveView(moveX, moveY)
                }
            }
        }
        return isDrug
    }

    private fun autoMoveView(moveX: Float, moveY: Float) {
        val changeY = moveY - downY - mStatusBarHeight
        val center = mScreenWidth / 2
        if ((moveX - downX + getViewWidth() / 2) <= center) {
            startAnimal(moveX - downX, 0f, changeY)
        } else {
            startAnimal(
                moveX - downX,
                mScreenWidth - getViewWidth(),
                changeY
            )
        }
    }

    private fun getViewWidth(): Float {
        return mBuilder?.contentView?.width?.toFloat() ?: 0f
    }

    private fun startAnimal(start: Float, end: Float, changeY: Float) {
        ValueAnimator.ofFloat(start, end).apply {
            duration = 500
            interpolator = AccelerateDecelerateInterpolator()
            addUpdateListener { animation ->
                updateLocation(
                    (animation.animatedValue as Float).toInt(),
                    changeY.toInt()
                )
            }
            start()
        }
    }
}