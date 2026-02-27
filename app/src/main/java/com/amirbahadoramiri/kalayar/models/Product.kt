package com.amirbahadoramiri.kalayar.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "product")
class Product {

    @PrimaryKey(autoGenerate = true)
    var productID: Int? = null

    var productName: String
    var productBrand: String
    var productPrice: Long
    var productUnit: String

    constructor(
        productName: String,
        productBrand: String,
        productPrice: Long,
        productUnit: String
    ) {
        this.productName = productName
        this.productBrand = productBrand
        this.productPrice = productPrice
        this.productUnit = productUnit
    }

}