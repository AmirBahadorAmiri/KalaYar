package com.amirbahadoramiri.kalayar.tools.darkmode

import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import androidx.appcompat.app.AppCompatDelegate
import com.amirbahadoramiri.kalayar.tools.shared_helper.SharedHelper

class DarkMode {

    companion object {
        const val DARK_MODE = "darkmode"

        var isThemeChanged: Boolean = false
        var targetDarkMode: Boolean = false
        var themeBitmap: Bitmap? = null
        var revealX: Int = 0
        var revealY: Int = 0

        fun enableDarkMode(context: Context) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            SharedHelper.getInstance(context).insert(DARK_MODE, true)
        }

        fun disableDarkMode(context: Context) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            SharedHelper.getInstance(context).insert(DARK_MODE, false)
        }

        fun toggleDarkMode(activity: Activity, isDarkMode: Boolean, x: Int = 0, y: Int = 0) {
            isThemeChanged = true
            targetDarkMode = isDarkMode
            revealX = x
            revealY = y
            
            takeScreenshot(activity)

            if (isDarkMode) {
                enableDarkMode(activity)
            } else {
                disableDarkMode(activity)
            }

            activity.recreate()
        }

        private fun takeScreenshot(activity: Activity) {
            val view = activity.window.decorView
            val bitmap = Bitmap.createBitmap(view.width, view.height, Bitmap.Config.ARGB_8888)
            val canvas = Canvas(bitmap)
            view.draw(canvas)
            themeBitmap = bitmap
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