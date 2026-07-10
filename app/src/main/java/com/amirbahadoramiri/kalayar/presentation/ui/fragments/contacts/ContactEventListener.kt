package com.amirbahadoramiri.kalayar.presentation.ui.fragments.contacts

import com.amirbahadoramiri.kalayar.domain.models.Contact

interface ContactEventListener {

    fun onContactClick(contact: Contact?, position: Int)
    fun onContactLongClick(contact: Contact, position: Int)
    fun onContactCall(contact: Contact, position: Int)
    fun onContactSms(contact: Contact, position: Int)
    fun onContactDelete(contact: Contact, position: Int)
    fun onContactEdit(contact: Contact, position: Int)
    fun onContactAdd(contact: Contact, position: Int)

}