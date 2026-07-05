package com.amirbahadoramiri.kalayar.presentation.base

import android.content.Intent
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.SystemBarStyle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.amirbahadoramiri.kalayar.R

open class BaseActivity : AppCompatActivity() {

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