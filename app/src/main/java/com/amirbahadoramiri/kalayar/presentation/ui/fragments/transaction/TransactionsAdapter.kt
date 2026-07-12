package com.amirbahadoramiri.kalayar.presentation.ui.fragments.transaction

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import com.amirbahadoramiri.kalayar.R
import com.amirbahadoramiri.kalayar.databinding.TransactionRecyclerviewItemBinding
import com.amirbahadoramiri.kalayar.domain.models.Transaction
import com.github.amirbahadoramiri.telegramdialog.TelegramConfirmDialog
import com.github.amirbahadoramiri.telegramdialog.direction.DialogDirection
import com.github.amirbahadoramiri.telegramdialog.listeners.OnConfirmListener

class TransactionsAdapter(val transactionEventListener: TransactionEventListener) : RecyclerView.Adapter<TransactionsAdapter.TransactionHolder>() {

    private var dataList: MutableList<Transaction> = mutableListOf()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = TransactionHolder(TransactionRecyclerviewItemBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    override fun getItemCount() = dataList.size
    override fun onBindViewHolder(holder: TransactionHolder, position: Int) = holder.bind()


    inner class TransactionHolder :
        RecyclerView.ViewHolder {
        val binding: TransactionRecyclerviewItemBinding
        constructor(binding: TransactionRecyclerviewItemBinding) : super(binding.root) {
            this.binding = binding
        }
        fun bind() {
            binding.transaction = dataList[absoluteAdapterPosition]
            binding.menuIcon.setOnClickListener {
                showPopupMenu(it)
            }
            itemView.setOnClickListener {
                transactionEventListener.onShowTransaction(dataList[absoluteAdapterPosition],absoluteAdapterPosition)
            }
            itemView.setOnLongClickListener {
                showPopupMenu(it)
                true
            }
        }

        private fun showPopupMenu(view: View) {
            val popupMenu = PopupMenu(view.context,view)
            popupMenu.menuInflater.inflate(R.menu.transaction_recyclerview_popup_menu,popupMenu.menu)
            popupMenu.setOnMenuItemClickListener {
                when(it.itemId) {
                    R.id.transaction_show -> {
                        transactionEventListener.onShowTransaction(dataList[absoluteAdapterPosition],absoluteAdapterPosition)
                    }
                    R.id.print -> {
                        transactionEventListener.onPrintTransaction(dataList[absoluteAdapterPosition],absoluteAdapterPosition)
                    }
                    R.id.delete -> {
                        val dialog = TelegramConfirmDialog(view.context)
                            .setDirection(DialogDirection.RTL)
                            .setTitle(view.context.getString(R.string.transaction_delete))
                            .setMessage(view.context.getString(R.string.transaction_delete_message))
                            .setCardBackgroundColor(itemView.context.getColor(R.color.kalayar_page_background_color))
                            .setNegativeButtonText(view.context.getString(R.string.delete))
                            .setNegativeButtonTextColor(itemView.context.getColor(R.color.kalayar_dialog_red_color))
                            .setNegativeButtonRippleColor(itemView.context.getColor(R.color.kalayar_dialog_red_color_tint))
                            .setNegativeButtonBackgroundColor(itemView.context.getColor(R.color.kalayar_page_background_color))
                            .setPositiveButtonText(view.context.getString(R.string.cancel))
                            .setPositiveButtonTextColor(itemView.context.getColor(R.color.kalayar_dialog_blue_color))
                            .setPositiveButtonRippleColor(itemView.context.getColor(R.color.kalayar_dialog_blue_color_tint))
                            .setPositiveButtonBackgroundColor(itemView.context.getColor(R.color.kalayar_page_background_color))

                        dialog.setOnClickListener(object : OnConfirmListener {
                            override fun onPositiveButtonClicked() {
                                dialog.dismiss()
                            }
                            override fun onNegativeButtonClicked() {
                                transactionEventListener.onRemoveTransaction(dataList[absoluteAdapterPosition],absoluteAdapterPosition)
                                dialog.dismiss()
                            }
                        })
                        dialog.show()
                    }
                }
                true
            }
            popupMenu.show()
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun reloadTransactions(addList: List<Transaction>) {
        dataList.clear()
        dataList.addAll(addList)
        notifyDataSetChanged()
    }

    fun addTransaction() {
    }

    fun removeTransaction(position: Int) {
        dataList.removeAt(position)
        notifyItemRemoved(position)
    }

    fun updateTransaction() {
    }

}