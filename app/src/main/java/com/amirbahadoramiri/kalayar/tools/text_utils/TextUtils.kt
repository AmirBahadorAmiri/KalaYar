package com.amirbahadoramiri.kalayar.tools.text_utils

import java.text.NumberFormat
import java.util.Locale

class TextUtils {

    companion object {

        fun numberFormat(value: Int): String {
            return numberFormat(value.toLong())
        }

        fun numberFormat(string: String): String {
            val cleanString = string.replace(",", "")
            return if (cleanString.isEmpty()) "" else numberFormat(cleanString.toLong())
        }

        fun numberFormat(value: Long): String {
//            return DecimalFormat("#,###").format(value)
            return NumberFormat.getNumberInstance(Locale.US).format(value)
        }

    }

}