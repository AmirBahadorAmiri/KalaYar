package com.amirbahadoramiri.kalayar.domain.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "contacts")
data class Contact(
    var contact_name: String, // 24
    var contact_number: String, // 11

    @PrimaryKey(autoGenerate = true)
    var contact_id: Long? = null
)