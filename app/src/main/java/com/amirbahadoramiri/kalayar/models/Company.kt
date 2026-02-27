package com.amirbahadoramiri.kalayar.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "company")
class Company {

    @PrimaryKey
    var companyName: String
    var companyAddress: String
    var companyWebSite: String
    var companyPhoneNumber: String
    var companyMobileNumber: String
    var companyEmail: String
    var companyInstagramID: String
    var companyTelegramID: String
    var companyWhatsappNumber: String

    constructor(
        companyName: String,
        companyAddress: String,
        companyWebSite: String,
        companyPhoneNumber: String,
        companyMobileNumber: String,
        companyEmail: String,
        companyInstagramID: String,
        companyTelegramID: String,
        companyWhatsappNumber: String
    ) {
        this.companyName = companyName
        this.companyAddress = companyAddress
        this.companyWebSite = companyWebSite
        this.companyPhoneNumber = companyPhoneNumber
        this.companyMobileNumber = companyMobileNumber
        this.companyEmail = companyEmail
        this.companyInstagramID = companyInstagramID
        this.companyTelegramID = companyTelegramID
        this.companyWhatsappNumber = companyWhatsappNumber
    }
}
