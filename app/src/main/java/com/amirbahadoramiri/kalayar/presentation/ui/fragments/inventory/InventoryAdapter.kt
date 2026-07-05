package com.amirbahadoramiri.kalayar.presentation.ui.fragments.inventory

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.amirbahadoramiri.kalayar.R
import com.amirbahadoramiri.kalayar.domain.models.Product

class InventoryAdapter : RecyclerView.Adapter<InventoryAdapter.InventoryHolder>() {

    private var dataList: MutableList<Product> = mutableListOf()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = InventoryHolder(LayoutInflater.from(parent.context).inflate(R.layout.inventory_recyclerview_item, parent, false))
    override fun getItemCount() = dataList.size
    override fun onBindViewHolder(holder: InventoryHolder, position: Int) = holder.bind(dataList[position])

    inner class InventoryHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val name: AppCompatTextView = itemView.findViewById(R.id.product_name)
        private val count: AppCompatTextView = itemView.findViewById(R.id.product_count)
        private val unit: AppCompatTextView = itemView.findViewById(R.id.product_unit)

        fun bind(product: Product) {
            name.text = product.product_name
            count.text = product.product_count.toString()
            unit.text = product.product_unit
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun reloadProduct(t: List<Product>) {
        dataList.clear()
        dataList.addAll(t)
        notifyDataSetChanged()
    }
}
