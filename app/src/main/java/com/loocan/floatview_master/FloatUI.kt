package com.loocan.floatview_master

import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.view.View
import android.view.animation.LinearInterpolator
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.transition.DrawableCrossFadeFactory
import com.google.android.material.imageview.ShapeableImageView
import com.loocan.floatview.FloatViewBuilder
import com.loocan.floatview.base.BaseUI

class FloatUI : BaseUI() {
    private var mIvLogo: ShapeableImageView? = null

    override fun onCreate(view: View, builder: FloatViewBuilder) {
        mIvLogo = view.findViewById(R.id.iv_logo)

        initView(view, builder)
    }

    @SuppressLint("ObjectAnimatorBinding")
    private fun initView(view: View, builder: FloatViewBuilder) {
        Glide.with(view.context)
            .load(builder.url)
            .transition(
                DrawableTransitionOptions.with(
                    DrawableCrossFadeFactory.Builder(
                        300
                    ).setCrossFadeEnabled(true).build()
                )
            )
            .into(mIvLogo!!)

        ObjectAnimator.ofFloat(mIvLogo, "rotation", 0f, 360f).apply {
            repeatMode = ValueAnimator.RESTART
            repeatCount = ValueAnimator.INFINITE
            interpolator = LinearInterpolator()
            start()
        }
    }
}