package com.amirbahadoramiri.kalayar.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "inventory")
class Inventory {
    @PrimaryKey(autoGenerate = true)
    var inventoryID: Int? = null

    var storeID: Int? = null
    var productID: Int? = null
    var productCount: Int? = null
    var lastTransactionTime: Long? = null

    constructor(
        storeID: Int,
        productID: Int,
        productCount: Int,
        lastTransactionTime: Long
    ) {
        this.storeID = storeID
        this.productID = productID
        this.productCount = productCount
        this.lastTransactionTime = lastTransactionTime
    }
}