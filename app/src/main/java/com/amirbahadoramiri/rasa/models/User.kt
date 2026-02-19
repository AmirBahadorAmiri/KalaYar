package com.amirbahadoramiri.rasa.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user")
class User {
    @PrimaryKey
    var name: String

    var ANDROID_ID: String
    var isActivated: Boolean = false

    constructor(
        name: String,
        ANDROID_ID: String
    ) {
        this.name = name
        this.ANDROID_ID = ANDROID_ID
    }

}