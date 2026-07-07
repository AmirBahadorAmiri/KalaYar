package com.amirbahadoramiri.kalayar.presentation.ui.fragments.transaction

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.amirbahadoramiri.kalayar.databinding.TransactionProductRecyclerviewItemBinding
import com.amirbahadoramiri.kalayar.domain.models.Product

class TransactionSearchProductAdapter : RecyclerView.Adapter<TransactionSearchProductAdapter.ProductHolder>() {

    private var onItemClickListener: OnItemClickListener? = null

    fun setOnItemClickListener(onItemClickListener: OnItemClickListener) {
        this.onItemClickListener = onItemClickListener
    }

    private val dataList = mutableListOf<Product>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ProductHolder(TransactionProductRecyclerviewItemBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    override fun onBindViewHolder(holder: ProductHolder, position: Int) = holder.bind()
    override fun getItemCount() = dataList.size

    inner class ProductHolder : RecyclerView.ViewHolder {
        val binding: TransactionProductRecyclerviewItemBinding
        constructor(binding: TransactionProductRecyclerviewItemBinding) : super(binding.root) {
            this.binding = binding
        }
        fun bind() {
            binding.product = dataList[absoluteAdapterPosition]
            itemView.setOnClickListener {
                onItemClickListener?.onClick(dataList.get(absoluteAdapterPosition))
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun addProducts(addList: List<Product>) {
        dataList.clear()
        dataList.addAll(addList)
        notifyDataSetChanged()
    }

    fun getDataList() = dataList

    fun addProduct() {
    }

    fun removeProduct() {
    }

    fun updateProduct() {
    }

    interface OnItemClickListener {
        fun onClick(product: Product)
    }

}