package com.amirbahadoramiri.kalayar.presentation.ui.fragments.transaction

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.amirbahadoramiri.kalayar.databinding.TransactionRecyclerviewItemBinding
import com.amirbahadoramiri.kalayar.domain.models.Transaction

class TransactionsAdapter : RecyclerView.Adapter<TransactionsAdapter.TransactionHolder>() {

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
                Toast.makeText(it.context, "clicked", Toast.LENGTH_SHORT).show()
            }
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