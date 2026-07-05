package com.amirbahadoramiri.kalayar.domain.models

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import java.text.DecimalFormat

@Entity(tableName = "product")
data class Product(
    var product_name: String, // String (64)
    var product_unit: String, // String (32)
    var product_price: Long,

    @PrimaryKey(autoGenerate = true)
    var product_id: Long? = null,

    var product_count: Long = 0
) {

    @Ignore
    fun getProductPrice() = product_price.toString()

    @Ignore
    fun getProductCount() = product_count.toString()

    @Ignore
    fun formatMoney(): String {
        return DecimalFormat("#,###").format(product_price)
    }

    @Ignore
    var change_value : Long = 0
    @Ignore
    var new_value : Long = 0

}