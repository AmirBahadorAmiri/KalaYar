package com.amirbahadoramiri.kalayar.views.fragments.transaction

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.amirbahadoramiri.kalayar.R
import com.amirbahadoramiri.kalayar.core.models.Product

class TransactionProductAdapter :
    RecyclerView.Adapter<TransactionProductAdapter.TransactionItemsHolder>() {

    private val dataList = mutableListOf<Product>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = TransactionItemsHolder(
        LayoutInflater.from(parent.context)
            .inflate(R.layout.product_recyclerview_item, parent, false)
    )

    override fun onBindViewHolder(holder: TransactionItemsHolder, position: Int) =
        holder.bind(position)

    override fun getItemCount() = dataList.size


    inner class TransactionItemsHolder : RecyclerView.ViewHolder {

        val menu_icon: AppCompatImageView
        val product_recyclerview_name: AppCompatTextView
        val product_recyclerview_price: AppCompatTextView

        constructor(itemView: View) : super(itemView) {
            menu_icon = itemView.findViewById(R.id.menu_icon)
            product_recyclerview_name = itemView.findViewById(R.id.product_recyclerview_name)
            product_recyclerview_price = itemView.findViewById(R.id.product_recyclerview_price)
        }

        fun bind(position: Int) {
            product_recyclerview_name.text = dataList[absoluteAdapterPosition].product_name
            product_recyclerview_price.text =
                dataList[absoluteAdapterPosition].product_price.toString()
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
    }

    fun getList() = dataList

}