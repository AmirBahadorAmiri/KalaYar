package com.amirbahadoramiri.kalayar.tools.text_utils

import java.text.DecimalFormat

class TextUtils {

    companion object {

        fun formatMoney(string: String): String {
            return DecimalFormat("#,###").format(string)
        }
        fun formatMoney(value: Int): String {
            return formatMoney(value.toString())
        }
        fun formatMoney(value: Long): String {
            return formatMoney(value.toString())
        }

    }

}