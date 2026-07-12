package com.amirbahadoramiri.kalayar.presentation.ui.fragments.product

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import com.amirbahadoramiri.kalayar.R
import com.amirbahadoramiri.kalayar.databinding.ProductRecyclerviewItemBinding
import com.amirbahadoramiri.kalayar.domain.models.Product
import com.github.amirbahadoramiri.telegramdialog.TelegramConfirmDialog
import com.github.amirbahadoramiri.telegramdialog.direction.DialogDirection
import com.github.amirbahadoramiri.telegramdialog.listeners.OnConfirmListener

class ProductAdapter(val productEventListener: ProductEventListener) : RecyclerView.Adapter<ProductAdapter.ProductHolder>() {

    private var dataList: MutableList<Product> = mutableListOf()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ProductHolder(ProductRecyclerviewItemBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    override fun getItemCount() = dataList.size
    override fun onBindViewHolder(holder: ProductHolder, position: Int) { holder.bind() }

    inner class ProductHolder : RecyclerView.ViewHolder {
        val binding: ProductRecyclerviewItemBinding
        constructor(binding: ProductRecyclerviewItemBinding) : super(binding.root) {
            this.binding = binding
        }

        fun bind() {
            binding.product = dataList[absoluteAdapterPosition]
            binding.menuIcon.setOnClickListener {
                showPopupMenu(it)
            }
            itemView.setOnClickListener {
                productEventListener.onShowProduct(dataList[absoluteAdapterPosition],absoluteAdapterPosition)
            }
            itemView.setOnLongClickListener {
                showPopupMenu(it)
                true
            }
        }

        private fun showPopupMenu(view: View) {
            val popupMenu = PopupMenu(view.context,view)
            popupMenu.menuInflater.inflate(R.menu.product_recyclerview_popup_menu,popupMenu.menu)
            popupMenu.setOnMenuItemClickListener {

                when(it.itemId) {
                    R.id.delete -> {

                        val dialog = TelegramConfirmDialog(itemView.context)
                            .setDirection(DialogDirection.RTL)
                            .setTitle(view.context.getString(R.string.product_delete))
                            .setMessage(view.context.getString(R.string.product_delete_message))
                            .setCardBackgroundColor(itemView.context.getColor(R.color.kalayar_page_background_color))
                            .setNegativeButtonText(view.context.getString(R.string.delete))
                            .setNegativeButtonTextColor(itemView.context.getColor(R.color.kalayar_dialog_red_color))
                            .setNegativeButtonRippleColor(itemView.context.getColor(R.color.kalayar_dialog_red_color_tint))
                            .setNegativeButtonBackgroundColor(itemView.context.getColor(R.color.kalayar_page_background_color))
                            .setPositiveButtonText(view.context.getString(R.string.cancel))
                            .setPositiveButtonTextColor(itemView.context.getColor(R.color.kalayar_dialog_blue_color))
                            .setPositiveButtonRippleColor(itemView.context.getColor(R.color.kalayar_dialog_blue_color_tint))
                            .setPositiveButtonBackgroundColor(itemView.context.getColor(R.color.kalayar_page_background_color))

                        dialog.setOnClickListener(object : OnConfirmListener {
                            override fun onPositiveButtonClicked() {
                                dialog.dismiss()
                            }
                            override fun onNegativeButtonClicked() {
                                productEventListener.onRemoveProduct(dataList[absoluteAdapterPosition],absoluteAdapterPosition)
                                dialog.dismiss()
                            }
                        })

                        dialog.show()

                    }
                    R.id.edit -> {
                        productEventListener.onShowProduct(dataList[absoluteAdapterPosition],absoluteAdapterPosition)
                    }
                }

                true
            }
            popupMenu.show()
        }

    }

    fun addProduct(product: Product) {
        dataList.add(product)
        notifyItemInserted(dataList.size-1)
    }

    fun addProduct(product: Product,position: Int) {
        dataList.add(position,product)
        notifyItemInserted(position)
    }

    fun removeProduct(position: Int) {
        dataList.removeAt(position)
        notifyItemRemoved(position)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun reloadProduct(t: List<Product>) {
        dataList.clear()
        dataList.addAll(t)
        notifyDataSetChanged()
    }

}