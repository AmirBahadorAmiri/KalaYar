package com.amirbahadoramiri.kalayar.views.activities.register

import android.os.Bundle
import com.amirbahadoramiri.kalayar.R
import com.amirbahadoramiri.kalayar.views.activities.base.BaseActivity

class RegisterActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        edge()
        setContentView(R.layout.activity_register)
        setViewCompatForMultiEditText()

    }

}