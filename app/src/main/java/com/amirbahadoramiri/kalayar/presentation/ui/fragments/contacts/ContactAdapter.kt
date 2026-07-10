package com.amirbahadoramiri.kalayar.presentation.ui.fragments.contacts

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import com.amirbahadoramiri.kalayar.R
import com.amirbahadoramiri.kalayar.databinding.ContactItemBinding
import com.amirbahadoramiri.kalayar.domain.models.Contact
import com.github.amirbahadoramiri.telegramdialog.library.TeleDirection
import com.github.amirbahadoramiri.telegramdialog.two.TeleDialogDouble
import com.github.amirbahadoramiri.telegramdialog.two.TeleDialogDoubleListener
import kotlin.text.get

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
                val popupMenu = PopupMenu(itemView.context,itemView)
                popupMenu.menuInflater.inflate(R.menu.contact_recyclerview_popup_menu,popupMenu.menu)
                popupMenu.setOnMenuItemClickListener {
                    when(it.itemId) {
                        R.id.delete -> {
                            val dialog = TeleDialogDouble(itemView.context)
                                .setDirection(TeleDirection.RTL)
                                .setTitle(itemView.context.getString(R.string.contact_delete))
                                .setMessage(itemView.context.getString(R.string.contact_delete_message))
                                .setButtonOneText(itemView.context.getString(R.string.delete))
                                .setButtonOneTextColor(R.color.kalayar_red_color)
                                .setButtonOneRippleColor(R.color.kalayar_red_color_tint)
                                .setButtonTwoText(itemView.context.getString(R.string.cancel))
                                .setButtonTwoTextColor(R.color.kalayar_blue_color)
                                .setButtonTwoRippleColor(R.color.kalayar_blue_color_tint)

                            dialog.setOnClickListener(object : TeleDialogDoubleListener {
                                override fun onFirstButtonClicked() {
                                    contactEventListener.onContactDelete(dataList[absoluteAdapterPosition],absoluteAdapterPosition)
                                    dialog.dismiss()
                                }
                                override fun onSecondButtonClicked() {
                                    dialog.dismiss()
                                }
                            })
                            dialog.show()
                        }
                        R.id.edit -> {
                            contactEventListener.onContactClick(dataList[absoluteAdapterPosition],absoluteAdapterPosition)
                        }
                    }
                    true
                }
                popupMenu.show()
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