package com.amirbahadoramiri.anbarplus.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "transaction")
class Transaction {

    companion object {
        const val DECREASE: Byte = 0
        const val INCREASE: Byte = 1
    }

    @PrimaryKey(autoGenerate = true)
    var transactionID: Int? = null

    var storeID: Int? = null
    var storeName: String

    var storeKeeperID: Int? = null
    var storeKeeperName: String

    var transactionTime: Long
    var transactionDescription: String

    constructor(
        storeID: Int?,
        storeName: String,
        storeKeeperID: Int?,
        storeKeeperName: String,
        transactionTime: Long,
        transactionDescription: String,
    ) {
        this.storeID = storeID
        this.storeName = storeName
        this.storeKeeperID = storeKeeperID
        this.storeKeeperName = storeKeeperName
        this.transactionTime = transactionTime
        this.transactionDescription = transactionDescription
    }
}