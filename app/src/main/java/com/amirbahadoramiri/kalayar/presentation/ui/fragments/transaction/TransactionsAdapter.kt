package com.amirbahadoramiri.kalayar.presentation.ui.fragments.transaction

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.amirbahadoramiri.kalayar.R
import com.amirbahadoramiri.kalayar.domain.models.Transaction

class TransactionsAdapter : RecyclerView.Adapter<TransactionsAdapter.TransactionHolder>() {

    private var dataList: MutableList<Transaction> = mutableListOf()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = TransactionHolder(LayoutInflater.from(parent.context).inflate(R.layout.transaction_recyclerview_item, parent, false))
    override fun getItemCount() = dataList.size
    override fun onBindViewHolder(holder: TransactionHolder, position: Int) = holder.bind(position)


    inner class TransactionHolder :
        RecyclerView.ViewHolder {

        val transaction_recyclerview_title: AppCompatTextView
        val transaction_recyclerview_time: AppCompatTextView

        constructor(itemView: View) : super(itemView) {
            transaction_recyclerview_title = itemView.findViewById(R.id.transaction_recyclerview_title)
            transaction_recyclerview_time = itemView.findViewById(R.id.transaction_recyclerview_time)
        }

        fun bind(position: Int) {
            transaction_recyclerview_title.text = dataList.get(absoluteAdapterPosition).transaction_title
//            val date = Date(dataList.get(absoluteAdapterPosition).transaction_create_time)
//            transaction_recyclerview_time.setText(SimpleDateFormat("YYYY/MM/dd h:m:s").format(date))
            transaction_recyclerview_time.text = dataList.get(absoluteAdapterPosition).getTransactionPersianDate()
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

    fun removeTransaction() {
    }

    fun updateTransaction() {
    }

}