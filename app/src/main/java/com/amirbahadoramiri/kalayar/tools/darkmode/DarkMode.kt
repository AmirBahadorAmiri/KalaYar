package com.amirbahadoramiri.kalayar.tools.darkmode

import android.content.Context
import androidx.appcompat.app.AppCompatDelegate
import com.amirbahadoramiri.kalayar.tools.shared_helper.SharedHelper

class DarkMode {

    companion object {
        const val DARK_MODE = "darkmode"
        fun enableDarkMode(context: Context) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            SharedHelper.getInstance(context).insert(DARK_MODE,true)
        }
        fun disableDarkMode(context: Context) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            SharedHelper.getInstance(context).insert(DARK_MODE,false)
        }
        fun requestMode(context: Context) {
            if (checkDarkMode(context)) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }
        fun checkDarkMode(context: Context) : Boolean {
            return SharedHelper.getInstance(context).readBoolean(DARK_MODE)
        }
    }

}