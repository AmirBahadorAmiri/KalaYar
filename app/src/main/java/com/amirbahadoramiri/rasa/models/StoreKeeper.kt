package com.amirbahadoramiri.rasa.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "storekeeper")
class StoreKeeper {

    @PrimaryKey(autoGenerate = true)
    var storeKeeperID: Int? = null

    var storeKeeperName: String
    var storeKeeperPhoneNumber: String
    var storeKeeperEmail: String

    constructor(
        storeKeeperName: String,
        storeKeeperPhoneNumber: String,
        storeKeeperEmail: String,
    ) {
        this.storeKeeperName = storeKeeperName
        this.storeKeeperPhoneNumber = storeKeeperPhoneNumber
        this.storeKeeperEmail = storeKeeperEmail
    }
}