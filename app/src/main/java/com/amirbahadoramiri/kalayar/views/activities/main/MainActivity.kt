package com.amirbahadoramiri.kalayar.views.activities.main

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.amirbahadoramiri.kalayar.R
import com.amirbahadoramiri.kalayar.databinding.MainActivityBinding
import com.amirbahadoramiri.kalayar.views.activities.base.BaseActivity
import com.amirbahadoramiri.kalayar.views.fragments.base.BaseFragment
import com.amirbahadoramiri.kalayar.views.fragments.splash.SplashFragment

class MainActivity : BaseActivity() {

    lateinit var binding: MainActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        edgeColor()
        binding = DataBindingUtil.setContentView(this, R.layout.main_activity)
        setViewCompat()
        findViews()
        setup()

    }

    private fun findViews() {
    }

    private fun setup() {

        replaceFragment(SplashFragment.getInstance())

    }

    fun replaceFragment(fragment: BaseFragment) {
        supportFragmentManager.beginTransaction().replace(R.id.frameLayout, fragment).addToBackStack(null).commit()
    }

    fun addFragment(fragment: BaseFragment) {
        supportFragmentManager.beginTransaction().add(R.id.frameLayout, fragment).addToBackStack(null).commit()
    }

    fun popBackStack() {
        supportFragmentManager.popBackStack()
    }

}