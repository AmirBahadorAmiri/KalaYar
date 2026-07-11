package com.amirbahadoramiri.kalayar

import android.app.Application
import com.amirbahadoramiri.kalayar.tools.darkmode.DarkMode

class AppManager: Application() {

    override fun onCreate() {
        super.onCreate()
        DarkMode.requestMode(this)
    }
}