package com.amirbahadoramiri.kalayar.presentation.ui.fragments.add_transaction

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import com.amirbahadoramiri.kalayar.R
import com.amirbahadoramiri.kalayar.databinding.ProductRecyclerviewItemBinding
import com.amirbahadoramiri.kalayar.domain.models.Product

class TransactionProductAdapter(val listener: TransactionProductAdapterListener) : RecyclerView.Adapter<TransactionProductAdapter.TransactionItemsHolder>() {

    private val dataList = mutableListOf<Product>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = TransactionItemsHolder(ProductRecyclerviewItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    override fun getItemCount() = dataList.size
    override fun onBindViewHolder(holder: TransactionItemsHolder, position: Int) = holder.bind()

    inner class TransactionItemsHolder : RecyclerView.ViewHolder {
        val binding: ProductRecyclerviewItemBinding
        constructor(binding: ProductRecyclerviewItemBinding) : super(binding.root) {
            this.binding = binding
        }
        fun bind() {
            binding.product = dataList[absoluteAdapterPosition]
            itemView.setOnClickListener {
                listener.onClickListener(dataList[absoluteAdapterPosition], absoluteAdapterPosition)
            }
            itemView.setOnLongClickListener {
                listener.onLongClickListener(dataList[absoluteAdapterPosition], absoluteAdapterPosition)
                true
            }
            binding.menuIcon.setOnClickListener {
                showPopupMenu(it)
            }
        }

        fun showPopupMenu(view: View) {
            val popupMenu = PopupMenu(view.context, view)
            popupMenu.inflate(R.menu.transaction_add_product_recyclerview_popup_menu)
            popupMenu.setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.delete -> {
                        listener.onRemoveClickListener(dataList[absoluteAdapterPosition], absoluteAdapterPosition)
                    }
                    R.id.edit -> {
                        listener.onClickListener(dataList[absoluteAdapterPosition], absoluteAdapterPosition)
                    }
                }
                true
            }
            popupMenu.show()
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
    fun removeItems(list: List<Product>) {
        dataList.removeAll(list)
        notifyDataSetChanged()
    }

    fun reloadItems(list: List<Product>) {
        dataList.clear()
        dataList.addAll(list)
        notifyDataSetChanged()
    }

    fun clearList() {
        dataList.clear()
        notifyDataSetChanged()
    }

    fun getList() = dataList

}