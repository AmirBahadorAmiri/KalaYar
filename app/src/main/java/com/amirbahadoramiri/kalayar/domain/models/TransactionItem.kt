package com.amirbahadoramiri.kalayar.domain.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "transaction_item")
data class TransactionItem(
    var product_id: Long?,
    var product_name: String, // String (64)
    var product_unit: String, // String (32)
    var product_price: Long,
    var last_value: Long,
    var change_value: Long,
    var new_value: Long,

    @PrimaryKey(autoGenerate = true)
    var item_id: Long? = null,

    var transaction_id: Long? = null

)