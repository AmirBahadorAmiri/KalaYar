package com.amirbahadoramiri.kalayar.domain.models

data class TasnimResponse(
    val updatedate: String,
    val currency: List<TasnimCurrency>
)

data class TasnimCurrency(
    val title: String,
    val desc: String,
    val p: String, // Price
    val d: String, // Change amount
    val dp: Double, // Change percentage
    val dt: String // Direction (high/low)
)
