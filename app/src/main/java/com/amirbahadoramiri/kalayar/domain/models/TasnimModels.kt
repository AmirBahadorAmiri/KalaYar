package com.amirbahadoramiri.kalayar.domain.models

import com.google.gson.annotations.SerializedName

data class TasnimResponse(
    @SerializedName("updatedate")
    val updatedate: String,
    @SerializedName("currency")
    val currency: List<TasnimCurrency>
)

data class TasnimCurrency(
    @SerializedName("title")
    val title: String,
    @SerializedName("desc")
    val desc: String,
    @SerializedName("p")
    val p: String, // Price
    @SerializedName("d")
    val d: String, // Change amount
    @SerializedName("dp")
    val dp: Double, // Change percentage
    @SerializedName("dts")
    val dt: String // Direction (high/low)
)
