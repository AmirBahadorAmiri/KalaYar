package com.amirbahadoramiri.kalayar.domain.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "store")
data class Store(
    @PrimaryKey
    var store_name: String,          // String (64)
    var store_address: String,       // String (256)
    var store_phonenumber: String,   // String (64)
    var store_website: String        // String (64)
)