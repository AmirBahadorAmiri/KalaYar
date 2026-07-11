package com.amirbahadoramiri.kalayar.presentation.ui.fragments.add_transaction

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.amirbahadoramiri.kalayar.databinding.ProductRecyclerviewItemBinding
import com.amirbahadoramiri.kalayar.domain.models.Product

class TransactionProductAdapter : RecyclerView.Adapter<TransactionProductAdapter.TransactionItemsHolder>() {

    private val dataList = mutableListOf<Product>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = TransactionItemsHolder(
        ProductRecyclerviewItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    override fun getItemCount() = dataList.size
    override fun onBindViewHolder(holder: TransactionItemsHolder, position: Int) = holder.bind()

    inner class TransactionItemsHolder : RecyclerView.ViewHolder {
        val binding: ProductRecyclerviewItemBinding
        constructor(binding: ProductRecyclerviewItemBinding) : super(binding.root) {
            this.binding = binding
        }
        fun bind() {
            binding.product = dataList[absoluteAdapterPosition]
        }
    }

    fun addItem(product: Product) {
        dataList.add(product)
        notifyItemInserted(dataList.size - 1)
    }

    fun addItem(product: Product, position: Int) {
        dataList.add(position, product)
        notifyItemInserted(position)
    }

    fun removeItem(position: Int) {
        dataList.removeAt(position)
        notifyItemRemoved(position)
    }

    fun clearList() {
        dataList.clear()
        notifyDataSetChanged()
    }

    fun getList() = dataList

}