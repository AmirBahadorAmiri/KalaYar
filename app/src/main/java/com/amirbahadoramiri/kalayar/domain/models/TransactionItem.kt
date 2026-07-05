package com.amirbahadoramiri.kalayar.domain.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "transaction_item")
data class TransactionItem(
    var product_id: Long?,
    var product_name: String,       // String (64)
    var product_unit: String,       // String (32)
    var product_price: Long,
    var previous_value: Long,       // max: 9,223,372,036,854,775,808
    var change_amount: Long,        // max: 9,223,372,036,854,775,808   /* max 9 */
    var final_value: Long,          // max: 9,223,372,036,854,775,808

    @PrimaryKey(autoGenerate = true)
    var item_id: Long? = null,

    var transaction_id: Long? = null

)