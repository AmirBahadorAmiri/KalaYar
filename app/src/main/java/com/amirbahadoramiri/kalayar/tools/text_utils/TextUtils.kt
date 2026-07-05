package com.amirbahadoramiri.kalayar.tools.text_utils

import java.text.DecimalFormat

class TextUtils {

    companion object {

        fun formatMoney(value: Int): String? {
            return formatMoney(value.toLong())
        }

        fun formatMoney(string: String): String? {
            return formatMoney(string.toLong())
        }

        fun formatMoney(value: Long): String? {
            return DecimalFormat("#,###").format(value)
        }

    }

}