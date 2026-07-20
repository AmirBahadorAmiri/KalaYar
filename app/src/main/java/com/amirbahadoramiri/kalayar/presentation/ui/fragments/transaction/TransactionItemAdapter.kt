package com.amirbahadoramiri.kalayar.presentation.ui.fragments.transaction

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.amirbahadoramiri.kalayar.databinding.TransactionItemRecyclerviewItemBinding
import com.amirbahadoramiri.kalayar.domain.models.TransactionItem

class TransactionItemAdapter : RecyclerView.Adapter<TransactionItemAdapter.TransactionItemHolder>() {

    private var dataList: List<TransactionItem> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        TransactionItemHolder(
            TransactionItemRecyclerviewItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun getItemCount() = dataList.size

    override fun onBindViewHolder(holder: TransactionItemHolder, position: Int) {
        holder.binding.item = dataList[position]
    }

    class TransactionItemHolder(val binding: TransactionItemRecyclerviewItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    fun setData(newList: List<TransactionItem>) {
        dataList = newList
        notifyDataSetChanged()
    }
}