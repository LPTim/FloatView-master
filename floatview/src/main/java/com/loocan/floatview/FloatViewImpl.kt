package com.loocan.floatview

import android.app.Activity
import android.content.Context
import android.graphics.PixelFormat
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.view.WindowManager
import java.lang.ref.WeakReference

class FloatViewImpl(var builder: FloatViewBuilder) : IFloatWindow {
    // 属性
    private val mWindowParams by lazy { WindowManager.LayoutParams() }

    private var mIsShowing = false

    private var mActivity: Activity? = null

    private var mWindowManager: WindowManager? = null

    private val mActivityLifecycle by lazy { ActivityLifecycle(this) }

    private val handler = MyHandler(WeakReference(this))

    init {
        mWindowParams.type = WindowManager.LayoutParams.TYPE_APPLICATION_ATTACHED_DIALOG
        mWindowParams.height = WindowManager.LayoutParams.WRAP_CONTENT
        mWindowParams.width = WindowManager.LayoutParams.WRAP_CONTENT
        mWindowParams.format = PixelFormat.TRANSLUCENT
        mWindowParams.windowAnimations = android.R.style.Animation_Toast
        mWindowParams.flags =
            (WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL or WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE or WindowManager.LayoutParams.FLAG_DIM_BEHIND)

        builder.contentView?.setOnClickListener {
            builder.listener?.onClick(builder.absoluteXY)
        }
        builder.contentView?.let {
            builder.ui?.onCreate(it, builder)
        }
    }

    override fun show(activity: Activity) {
        this.mActivity = activity

        hide()

        // 设置监听
        activity.application?.registerActivityLifecycleCallbacks(mActivityLifecycle)

        showView()
    }

    private fun showView() {
        if (builder.contentView == null) {
            throw NullPointerException("contentView is must not be null")
        }
        if (mIsShowing) {
            return
        }
        mIsShowing = true

        mWindowManager = mActivity?.getSystemService(Context.WINDOW_SERVICE) as WindowManager?

        mWindowParams.token = mActivity?.window?.decorView?.windowToken

        notifyViewParam()

        handler.sendMessageDelayed(handler.obtainMessage(), 100)
    }

    override fun hide() {
        if (!mIsShowing) {
            return
        }
        mIsShowing = false

        mActivity?.application?.unregisterActivityLifecycleCallbacks(mActivityLifecycle)
        try {
            mWindowManager?.removeView(builder.contentView)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun notifyView() {
        notifyViewParam()

        mWindowManager?.updateViewLayout(builder.contentView, mWindowParams)
    }

    override fun notifyViewParam() {
        builder.run {
            mWindowParams.gravity = gravity
            mWindowParams.dimAmount = dimAmount
            mWindowParams.x = absoluteXY.first
            mWindowParams.y = absoluteXY.second
            val flags =
                WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL or WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
            if (touchable) {
                mWindowParams.flags = mWindowParams.flags or flags
            } else {
                mWindowParams.flags = mWindowParams.flags and flags.inv()
            }
            draggable?.bindTouch(builder, this@FloatViewImpl)
        }
    }

    private class MyHandler(val weakReference: WeakReference<FloatViewImpl>) :
        Handler(Looper.getMainLooper()) {
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            weakReference.get()?.run {
                val token = mActivity?.window?.decorView?.windowToken
                token?.let {
                    mWindowParams.token = mActivity?.window?.decorView?.windowToken

                    try {
                        mWindowManager?.addView(builder.contentView, mWindowParams)
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                } ?: run {
                    handler.sendMessageDelayed(handler.obtainMessage(), 100)
                }
            }
        }
    }
}