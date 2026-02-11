package com.amirbahadoramiri.anbarplus.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "transaction_items")
class TransactionItems {

    @PrimaryKey(autoGenerate = true)
    var transactionItemID: Int? = null

    var transactionID: Int? = null
    var transactionType: Byte

    var productID: Int? = null
    var productName: String
    var productBrand: String
    var productPrice: Long
    var productUnit: String

    constructor(
        transactionID: Int?,
        transactionType: Byte,
        productID: Int?,
        productName: String,
        productBrand: String,
        productPrice: Long,
        productUnit: String
    ) {
        this.transactionID = transactionID
        this.transactionType = transactionType
        this.productID = productID
        this.productName = productName
        this.productBrand = productBrand
        this.productPrice = productPrice
        this.productUnit = productUnit
    }
}