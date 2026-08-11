package com.amirbahadoramiri.kalayar.domain.models

import com.google.gson.annotations.SerializedName

data class PriceItem(
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("price")
    val price: String,
    @SerializedName("change")
    val change: String,
    @SerializedName("isPositive")
    val isPositive: Boolean
)
