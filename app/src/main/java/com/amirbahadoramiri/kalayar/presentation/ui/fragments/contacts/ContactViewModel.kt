package com.amirbahadoramiri.kalayar.presentation.ui.fragments.contacts

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.amirbahadoramiri.kalayar.data.repository.RoomContactDataSource
import com.amirbahadoramiri.kalayar.domain.models.Contact
import com.amirbahadoramiri.kalayar.domain.repository.contact.ContactRepository
import kotlinx.coroutines.launch

class ContactViewModel(application: Application) : AndroidViewModel(application) {

    val repository = ContactRepository(RoomContactDataSource(application))
    val allContactsLiveData = MutableLiveData<MutableList<Contact>>()

    fun addContact(contact: Contact) {
        viewModelScope.launch {
            repository.addContact(contact).let {
                contact.contact_id = it
            }
        }
    }

    fun deleteContact(contact: Contact) {
        viewModelScope.launch {
            repository.deleteContact(contact)
        }
    }

    fun updateContact(contact: Contact) {
        viewModelScope.launch {
            repository.updateContact(contact)
        }
    }

    fun getAllContacts() {
        viewModelScope.launch {
            allContactsLiveData.postValue(repository.getContacts().asReversed().toMutableList())
        }
    }

}