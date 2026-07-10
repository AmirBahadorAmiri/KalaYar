package com.amirbahadoramiri.kalayar.domain.repository.contact

import com.amirbahadoramiri.kalayar.domain.models.Contact

interface ContactDataSource {

    suspend fun addContact(contact: Contact): Long?
    suspend fun updateContact(contact: Contact)
    suspend fun deleteContact(contact: Contact)
    suspend fun deleteContacts(contacts: List<Contact>)
    suspend fun getContact(id: Long): Contact?
    suspend fun getContacts(): List<Contact>

}