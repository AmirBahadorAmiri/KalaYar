package com.amirbahadoramiri.kalayar.data.repository

import android.content.Context
import com.amirbahadoramiri.kalayar.data.db.PublicDatabase
import com.amirbahadoramiri.kalayar.domain.models.Contact
import com.amirbahadoramiri.kalayar.domain.repository.contact.ContactDataSource

class RoomContactDataSource(context: Context): ContactDataSource {

    val publicDao = PublicDatabase.getPublicDatabase(context).getPublicDAO()

    override suspend fun addContact(contact: Contact) = publicDao.addContact(contact)
    override suspend fun updateContact(contact: Contact) = publicDao.updateContact(contact)
    override suspend fun deleteContact(contact: Contact) = publicDao.deleteContact(contact)
    override suspend fun deleteContacts(contacts: List<Contact>) = publicDao.deleteContacts(contacts)
    override suspend fun getContact(id: Long) = publicDao.getContact(id)
    override suspend fun getContacts() = publicDao.getContacts()

}