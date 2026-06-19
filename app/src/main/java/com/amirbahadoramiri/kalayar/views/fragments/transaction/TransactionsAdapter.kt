package com.amirbahadoramiri.kalayar.views.fragments.transaction

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.amirbahadoramiri.kalayar.R
import com.amirbahadoramiri.kalayar.core.models.Transaction
import com.amirbahadoramiri.kalayar.tools.database.PublicDatabase
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.SingleObserver
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers

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
            this.transaction_recyclerview_title =
                itemView.findViewById(R.id.transaction_recyclerview_title)
            this.transaction_recyclerview_time =
                itemView.findViewById(R.id.transaction_recyclerview_time)
        }

        fun bind(position: Int) {
            transaction_recyclerview_title.setText(dataList.get(absoluteAdapterPosition).transaction_title)
            transaction_recyclerview_time.setText(dataList.get(absoluteAdapterPosition).transaction_create_time.toString())
        }

    }

    fun addTransactions(addList: List<Transaction>) {
        dataList.addAll(addList)
        notifyDataSetChanged()
    }

    fun addTransaction() {

    }

    fun removeTransaction() {

    }

    fun updateTransaction() {

    }

    fun reloadDatabase(context: Context) {
        PublicDatabase.getPublicDatabase(context)?.getPublicDAO()
            ?.getAllTransaction()
            ?.subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe(object : SingleObserver<List<Transaction>> {
                override fun onSubscribe(d: Disposable) {}
                override fun onSuccess(list: List<Transaction>) {
                    dataList.clear()
                    addTransactions(list)
                }
                override fun onError(e: Throwable) {}
            })
    }

}