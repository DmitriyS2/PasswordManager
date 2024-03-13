package com.sd.passwordmanager.ui

import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.sd.passwordmanager.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(R.layout.activity_main)

fun View.animTouch() {
    ObjectAnimator.ofPropertyValuesHolder(
        this,
        PropertyValuesHolder.ofFloat(View.SCALE_X, 1.0F, 1.2F, 1.0F),
        PropertyValuesHolder.ofFloat(View.SCALE_Y, 1.0F, 1.2F, 1.0F)
    ).start()
}