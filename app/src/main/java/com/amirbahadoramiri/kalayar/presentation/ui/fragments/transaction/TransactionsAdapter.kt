package com.amirbahadoramiri.kalayar.presentation.ui.fragments.transaction

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import com.amirbahadoramiri.kalayar.R
import com.amirbahadoramiri.kalayar.databinding.TransactionRecyclerviewItemBinding
import com.amirbahadoramiri.kalayar.domain.models.Transaction
import com.github.amirbahadoramiri.telegramdialog.library.TeleDirection
import com.github.amirbahadoramiri.telegramdialog.two.TeleDialogDouble
import com.github.amirbahadoramiri.telegramdialog.two.TeleDialogDoubleListener

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
                        val dialog = TeleDialogDouble(view.context)
                            .setDirection(TeleDirection.RTL)
                            .setTitle("حذف تراکنش")
                            .setMessage("تمامی موجودی ها ها باقی خواهند ماند\n" +
                                    "با حذف تراکنش موافقید ؟")
                            .setButtonOneText("حذف")
                            .setButtonOneTextColor(R.color.kalayar_red_color)
                            .setButtonOneRippleColor(R.color.kalayar_red_color_tint)
                            .setButtonTwoText("لغو")
                            .setButtonTwoTextColor(R.color.kalayar_blue_color)
                            .setButtonTwoRippleColor(R.color.kalayar_blue_color_tint)

                        dialog.setOnClickListener(object : TeleDialogDoubleListener {
                            override fun onFirstButtonClicked() {
                                transactionEventListener.onRemoveTransaction(dataList[absoluteAdapterPosition],absoluteAdapterPosition)
                                dialog.dismiss()
                            }
                            override fun onSecondButtonClicked() {
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