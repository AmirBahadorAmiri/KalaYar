package com.amirbahadoramiri.rasa.views.activities.base

import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.OnApplyWindowInsetsListener
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.amirbahadoramiri.rasa.R

open class BaseActivity : AppCompatActivity() {

    fun edge() {
        enableEdgeToEdge()
    }

    fun setViewCompat() {
        ViewCompat.setOnApplyWindowInsetsListener(
            findViewById(R.id.main),
            OnApplyWindowInsetsListener { v: View?, insets: WindowInsetsCompat? ->
                val systemBars = insets!!.getInsets(WindowInsetsCompat.Type.systemBars())
                v!!.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
                insets
            })
    }

}