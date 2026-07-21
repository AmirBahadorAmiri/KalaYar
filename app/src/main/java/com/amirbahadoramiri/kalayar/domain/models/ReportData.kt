package com.amirbahadoramiri.kalayar.domain.models

data class ReportData(
    val totalInventoryValue: Long = 0,
    val totalProductsCount: Long = 0,
    val productTypesCount: Int = 0,
    val salesToday: Long = 0,
    val sales1Month: Long = 0,
    val sales3Months: Long = 0,
    val sales6Months: Long = 0,
    val sales1Year: Long = 0,
    val totalSales: Long = 0
)