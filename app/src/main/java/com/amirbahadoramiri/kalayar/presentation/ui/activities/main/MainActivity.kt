package com.amirbahadoramiri.kalayar.presentation.ui.activities.main

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.amirbahadoramiri.kalayar.R
import com.amirbahadoramiri.kalayar.databinding.MainActivityBinding
import com.amirbahadoramiri.kalayar.presentation.base.BaseActivity

class MainActivity : BaseActivity() {

    lateinit var binding: MainActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        edgeColor()
        binding = DataBindingUtil.setContentView(this, R.layout.main_activity)
        setViewCompat()
    }

}