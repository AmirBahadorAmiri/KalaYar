package com.amirbahadoramiri.kalayar.presentation.ui.fragments.contacts

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.widget.PopupMenu
import androidx.core.net.toUri
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.amirbahadoramiri.kalayar.R
import com.amirbahadoramiri.kalayar.databinding.AddContactBottomSheetBinding
import com.amirbahadoramiri.kalayar.databinding.ContactsFragmentBinding
import com.amirbahadoramiri.kalayar.domain.models.Contact
import com.amirbahadoramiri.kalayar.presentation.base.BaseFragment
import com.github.amirbahadoramiri.telegramdialog.library.TeleDirection
import com.github.amirbahadoramiri.telegramdialog.one.TeleDialogSingle
import com.github.amirbahadoramiri.telegramdialog.two.TeleDialogDouble
import com.github.amirbahadoramiri.telegramdialog.two.TeleDialogDoubleListener
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.milliseconds

class ContactsFragment: BaseFragment(), ContactEventListener {

    lateinit var binding: ContactsFragmentBinding
    lateinit var contactViewModel: ContactViewModel
    val contactAdapter = ContactAdapter(this)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = ContactsFragmentBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setup()
    }

    private fun setup() {
        contactViewModel = ViewModelProvider(this).get(ContactViewModel::class)
        contactViewModel.allContactsLiveData.observe(viewLifecycleOwner) {
            contactAdapter.reloadContacts(it)
        }

        binding.contactRecyclerview.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = contactAdapter
            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    when {
                        dy > 0 && binding.addContact.isVisible -> binding.addContact.hide()
                        dy < 0 && !binding.addContact.isVisible -> binding.addContact.show()
                    }
                }
            })
        }
        contactViewModel.getAllContacts()

        binding.addContact.setOnClickListener {
            onContactClick(null,0)
        }

        binding.contactSearch.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                val searchText = s.toString()
                if (searchText.isEmpty()) {
                    contactViewModel.allContactsLiveData.value?.let {
                        contactAdapter.reloadContacts(it)
                    }
                } else {
                    contactViewModel.allContactsLiveData.value?.filter {
                        if (it.contact_name.contains(searchText) || it.contact_number.contains(searchText)) true else false
                    }?.let {
                        contactAdapter.reloadContacts(it)
                    }
                }
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        customOnBackPressed()
    }

    private fun customOnBackPressed() {
        val backPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this, backPressedCallback)
    }

    override fun onContactClick(contact: Contact?, position: Int) {
        val bottomSheetDialog = BottomSheetDialog(requireContext())
        val addContactBottomSheetBinding = AddContactBottomSheetBinding.inflate(layoutInflater)
        bottomSheetDialog.setContentView(addContactBottomSheetBinding.root)

        if ( contact!= null ) addContactBottomSheetBinding.contact = contact

        addContactBottomSheetBinding.confirmButton.setOnClickListener {

            val contact_name = addContactBottomSheetBinding.contactName.text.toString()
            val contact_number = addContactBottomSheetBinding.contactNumber.text.toString()

            addContactBottomSheetBinding.contactNameLayout.isErrorEnabled = false
            addContactBottomSheetBinding.contactNumberLayout.isErrorEnabled = false

            if (contact_name.isEmpty()) {
                addContactBottomSheetBinding.contactNameLayout.error = getString(R.string.is_necessary)
                toast(getString(R.string.fill_necessary_field))
            } else if ( contact_number.isEmpty()) {
                addContactBottomSheetBinding.contactNumberLayout.error = getString(R.string.is_necessary)
                toast(getString(R.string.fill_necessary_field))
            } else {
                if ( contact == null ) {
                    val newContact = Contact(contact_name,contact_number)
                    onContactAdd(newContact,0)
                } else {
                    contact.contact_name = contact_name
                    contact.contact_number = contact_number
                    onContactEdit(contact,position)
                }
                bottomSheetDialog.dismiss()
            }
        }

        bottomSheetDialog.show()
    }

    override fun onContactCall(contact: Contact, position: Int) {
        val intent = Intent(Intent.ACTION_DIAL).apply {
            data = "tel:${contact.contact_number}".toUri()
        }
        startActivity(intent)
    }

    override fun onContactSms(contact: Contact, position: Int) {
        val intent = Intent(Intent.ACTION_SENDTO).apply {
            data = "smsto:${contact.contact_number}".toUri()
        }
        startActivity(intent)
    }

    override fun onContactDelete(contact: Contact, position: Int) {
        lifecycleScope.launch {
            delay(500.milliseconds)
            contactAdapter.removeContact(position)
            contactViewModel.deleteContact(contact)
        }
    }

    override fun onContactEdit(contact: Contact, position: Int) {
        lifecycleScope.launch {
            delay(500.milliseconds)
            contactAdapter.removeContact(position)
            delay(500.milliseconds)
            contactAdapter.addContact(contact,position)
            if (position == 0)
                binding.contactRecyclerview.scrollToPosition(0)
            contactViewModel.updateContact(contact)
        }
    }

    override fun onContactAdd(contact: Contact, position: Int) {
        lifecycleScope.launch {
            delay(500.milliseconds)
            contactAdapter.addContact(contact,position)
            binding.contactRecyclerview.scrollToPosition(0)
            contactViewModel.addContact(contact)
        }
    }

}