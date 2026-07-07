package com.amirbahadoramiri.kalayar.presentation.ui.fragments.inventory

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.amirbahadoramiri.kalayar.databinding.InventoryRecyclerviewItemBinding
import com.amirbahadoramiri.kalayar.domain.models.Product

class InventoryAdapter : RecyclerView.Adapter<InventoryAdapter.InventoryHolder>() {

    private var dataList: MutableList<Product> = mutableListOf()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = InventoryHolder(InventoryRecyclerviewItemBinding.inflate(LayoutInflater.from(parent.context),parent, false))
    override fun getItemCount() = dataList.size
    override fun onBindViewHolder(holder: InventoryHolder, position: Int) = holder.bind()

    inner class InventoryHolder : RecyclerView.ViewHolder {
        val binding: InventoryRecyclerviewItemBinding
        constructor(binding: InventoryRecyclerviewItemBinding) : super(binding.root) {
            this.binding = binding
        }
        fun bind() {
            binding.product = dataList[absoluteAdapterPosition]
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun reloadProduct(t: List<Product>) {
        dataList.clear()
        dataList.addAll(t)
        notifyDataSetChanged()
    }
}
