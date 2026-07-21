package com.amirbahadoramiri.kalayar.domain.models

data class PriceItem(
    val id: String,
    val name: String,
    val price: String,
    val change: String,
    val isPositive: Boolean
)
