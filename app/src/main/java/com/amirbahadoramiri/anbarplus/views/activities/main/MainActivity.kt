package com.amirbahadoramiri.anbarplus.views.activities.main

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import com.amirbahadoramiri.anbarplus.R
import com.amirbahadoramiri.anbarplus.views.activities.base.BaseActivity

class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        setViewCompat()
    }

}