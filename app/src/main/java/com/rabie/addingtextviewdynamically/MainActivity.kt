package com.rabie.addingtextviewdynamically

import android.os.Bundle
import android.view.MotionEvent
import android.view.MotionEvent.ACTION_MOVE
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    lateinit var tvContainer: LinearLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        tvContainer = findViewById(R.id.tvsContainer)
        button.setOnClickListener {
            addTv()
        }
    }

    fun addTv() {
        val v: ConstraintLayout = layoutInflater.inflate(R.layout.tv_item, null) as ConstraintLayout
        val tvMinus=v.findViewById<ImageView>(R.id.tvMinus)
        tvMinus.setOnClickListener{
            tvContainer.removeViewAt(tvContainer.indexOfChild(tvMinus.parent as ConstraintLayout))
            tvContainer.invalidate()
        }
        tvContainer.addView(v)
    }
}