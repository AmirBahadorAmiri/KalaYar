package com.amirbahadoramiri.kalayar.presentation.ui.fragments.contacts

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.amirbahadoramiri.kalayar.databinding.ContactItemBinding
import com.amirbahadoramiri.kalayar.domain.models.Contact

class ContactAdapter(val contactEventListener: ContactEventListener) : RecyclerView.Adapter<ContactAdapter.ContactHolder>() {

    private val dataList = mutableListOf<Contact>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ContactHolder(ContactItemBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    override fun onBindViewHolder(holder: ContactHolder, position: Int) = holder.bind()
    override fun getItemCount() = dataList.size

    inner class ContactHolder : RecyclerView.ViewHolder {
        val binding: ContactItemBinding
        constructor(binding: ContactItemBinding) : super(binding.root) {
            this.binding = binding
        }
        fun bind() {
            binding.contact = dataList[absoluteAdapterPosition]
            binding.contactSms.setOnClickListener {
                contactEventListener.onContactSms(dataList[absoluteAdapterPosition],absoluteAdapterPosition)
            }
            binding.contactCall.setOnClickListener {
                contactEventListener.onContactCall(dataList[absoluteAdapterPosition],absoluteAdapterPosition)
            }
            itemView.setOnClickListener {
                contactEventListener.onContactClick(dataList[absoluteAdapterPosition],absoluteAdapterPosition)
            }
            itemView.setOnLongClickListener {
                contactEventListener.onContactLongClick(dataList[absoluteAdapterPosition],absoluteAdapterPosition)
                true
            }
        }
    }

    fun addContact(contact: Contact) {
        dataList.add(contact)
        notifyItemInserted(dataList.size-1)
    }

    fun addContact(contact: Contact,position: Int) {
        dataList.add(position,contact)
        notifyItemInserted(position)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun addContacts(ccontacts: List<Contact>) {
        dataList.addAll(ccontacts)
        notifyDataSetChanged()
    }

    fun removeContact(position: Int) {
        dataList.removeAt(position)
        notifyItemRemoved(position)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun reloadContacts(ccontacts: List<Contact>) {
        dataList.clear()
        dataList.addAll(ccontacts)
        notifyDataSetChanged()
    }

}