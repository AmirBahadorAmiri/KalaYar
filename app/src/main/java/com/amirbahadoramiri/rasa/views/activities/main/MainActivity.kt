package com.amirbahadoramiri.rasa.views.activities.main

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import com.amirbahadoramiri.rasa.R
import com.amirbahadoramiri.rasa.views.activities.base.BaseActivity

class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        setViewCompat()
    }
}