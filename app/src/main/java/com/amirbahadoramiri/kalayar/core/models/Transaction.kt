package com.amirbahadoramiri.kalayar.core.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "transaction")
data class Transaction(
    var transaction_type: Byte,
    var transaction_title: String, // String (32)
    var transaction_create_time: Long,
    var transaction_description: String, // String (256)

    @PrimaryKey(autoGenerate = true)
    var transaction_id: Long? = null,

)