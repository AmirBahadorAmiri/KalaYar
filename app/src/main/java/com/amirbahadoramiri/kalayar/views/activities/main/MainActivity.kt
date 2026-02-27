package com.amirbahadoramiri.kalayar.views.activities.main

import android.os.Bundle
import com.amirbahadoramiri.kalayar.R
import com.amirbahadoramiri.kalayar.views.activities.base.BaseActivity

class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        edgeColor()
        setContentView(R.layout.activity_main)
        setViewCompat()
    }
}