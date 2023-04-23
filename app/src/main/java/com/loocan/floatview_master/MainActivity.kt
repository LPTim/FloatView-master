package com.loocan.floatview_master

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import com.loocan.floatview.FloatViewBuilder
import com.loocan.floatview.listener.OnFloatClickListener

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        FloatViewBuilder()
            .setAbsoluteXY(200, 600)
            .setDrag(MoveDrag())
            .setUi(FloatUI())
            .setUrl("https://y.qq.com/music/photo_new/T002R300x300M0000005lhkZ1vGQ9G_1.jpg?max_age=2592000")
            .setContentView(
                LayoutInflater.from(this).inflate(R.layout.view_float, null)
            )

            .setOnClickListener(object : OnFloatClickListener {
                override fun onClick(absoluteXY: Pair<Int, Int>) {
                    val intent = Intent(this@MainActivity, NextActivity::class.java)
                    intent.putExtra("x", absoluteXY.first)
                    intent.putExtra("y", absoluteXY.second)
                    startActivity(intent)
                }
            })
            .build()
            .show(this)
    }
}