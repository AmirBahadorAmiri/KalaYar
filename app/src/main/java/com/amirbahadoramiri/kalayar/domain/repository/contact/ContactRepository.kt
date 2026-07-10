package com.amirbahadoramiri.kalayar.domain.repository.contact

import com.amirbahadoramiri.kalayar.domain.models.Contact

class ContactRepository(private val contactDataSource: ContactDataSource) {

    suspend fun addContact(contact: Contact) = contactDataSource.addContact(contact)
    suspend fun updateContact(contact: Contact) = contactDataSource.updateContact(contact)
    suspend fun deleteContact(contact: Contact) = contactDataSource.deleteContact(contact)
    suspend fun deleteContacts(contacts: List<Contact>) = contactDataSource.deleteContacts(contacts)
    suspend fun getContact(id: Long) = contactDataSource.getContact(id)
    suspend fun getContacts() = contactDataSource.getContacts()

}