package com.amirbahadoramiri.rasa.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "store")
class Store {

    @PrimaryKey(autoGenerate = true)
    var storeID: Int? = null

    var storeName: String
    var storeKeeperID: Int? = null
    var storeDescription: String

    constructor(storeName: String, storeKeeperID: Int?, storeDescription: String) {
        this.storeName = storeName
        this.storeKeeperID = storeKeeperID
        this.storeDescription = storeDescription
    }
}