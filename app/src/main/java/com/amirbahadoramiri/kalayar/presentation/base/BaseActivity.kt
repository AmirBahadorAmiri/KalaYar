package com.amirbahadoramiri.kalayar.presentation.base

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.content.Intent
import android.view.View
import android.view.ViewAnimationUtils
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.SystemBarStyle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.amirbahadoramiri.kalayar.R
import com.amirbahadoramiri.kalayar.tools.darkmode.DarkMode
import kotlin.math.hypot

open class BaseActivity : AppCompatActivity() {

    fun handleCircularReveal() {
        val bitmap = DarkMode.themeBitmap
        if (DarkMode.isThemeChanged && bitmap != null) {
            val rootLayout = findViewById<ViewGroup>(android.R.id.content)
            val cx = DarkMode.revealX
            val cy = DarkMode.revealY

            if (DarkMode.targetDarkMode) {
                // انیمیشن برای تم تیره: لایه قبلی (روشن) روی تم جدید است و کوچک می‌شود
                val overlay = ImageView(this)
                overlay.setImageBitmap(bitmap)
                overlay.scaleType = ImageView.ScaleType.FIT_XY
                overlay.layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
                rootLayout.addView(overlay)

                overlay.post {
                    val finalRadius = hypot(rootLayout.width.toDouble(), rootLayout.height.toDouble()).toFloat()
                    val anim = ViewAnimationUtils.createCircularReveal(overlay, cx, cy, finalRadius, 0f)
                    anim.duration = 700
                    anim.addListener(object : AnimatorListenerAdapter() {
                        override fun onAnimationEnd(animation: Animator) {
                            rootLayout.removeView(overlay)
                            DarkMode.themeBitmap = null
                            DarkMode.isThemeChanged = false
                        }
                    })
                    anim.start()
                }
            } else {
                // انیمیشن برای تم روشن: لایه قبلی (تیره) زیر است و تم جدید روی آن بزرگ می‌شود
                val background = ImageView(this)
                background.setImageBitmap(bitmap)
                background.scaleType = ImageView.ScaleType.FIT_XY
                background.layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
                
                // حقه: لایه تیره را به DecorView اضافه می‌کنیم تا زیر کل اکتیویتی باشد
                val decorView = window.decorView as ViewGroup
                decorView.addView(background, 0)

                rootLayout.post {
                    val finalRadius = hypot(rootLayout.width.toDouble(), rootLayout.height.toDouble()).toFloat()
                    val anim = ViewAnimationUtils.createCircularReveal(rootLayout, cx, cy, 0f, finalRadius)
                    anim.duration = 700
                    anim.addListener(object : AnimatorListenerAdapter() {
                        override fun onAnimationEnd(animation: Animator) {
                            decorView.removeView(background)
                            DarkMode.themeBitmap = null
                            DarkMode.isThemeChanged = false
                        }
                    })
                    anim.start()
                }
            }
        }
    }

    fun edge() {
        enableEdgeToEdge()
    }

    fun edgeColor() {
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.light(
                scrim = ContextCompat.getColor(this, R.color.kalayar_primary_color),
                darkScrim = ContextCompat.getColor(this, R.color.kalayar_primary_color)
            )
        )
    }

    fun setViewCompat() {
//        android.R.id.content
        ViewCompat.setOnApplyWindowInsetsListener(
            findViewById(R.id.main)
        ) { v: View?, insets: WindowInsetsCompat? ->
            val systemBars = insets!!.getInsets(WindowInsetsCompat.Type.systemBars())
            v!!.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    fun setViewCompatForMultiEditText() {
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(android.R.id.content)) { view, insets ->
            val imeVisible = insets.isVisible(WindowInsetsCompat.Type.ime())
            val imeHeight = insets.getInsets(WindowInsetsCompat.Type.ime()).bottom

            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())

            view.setPadding(
                systemBars.left,
                systemBars.top,
                systemBars.right,
                if (imeVisible) imeHeight else 0
            )
            insets
        }

        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)

    }

    fun openActivity(cls: Class<*>?) {
        startActivity(Intent(this, cls))
    }

    fun toast(str: String) {
        Toast.makeText(this, str, Toast.LENGTH_SHORT).show()
    }

}