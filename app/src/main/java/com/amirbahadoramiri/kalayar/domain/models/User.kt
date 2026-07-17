package com.amirbahadoramiri.kalayar.domain.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user")
data class User(

    @PrimaryKey
    var android_id: String,

    var is_activated: Boolean = false,

    var user_password: String = ""

)