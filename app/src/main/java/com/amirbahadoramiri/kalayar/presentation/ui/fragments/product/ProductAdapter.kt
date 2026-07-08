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
import com.github.amirbahadoramiri.telegramdialog.library.TeleDirection
import com.github.amirbahadoramiri.telegramdialog.two.TeleDialogDouble
import com.github.amirbahadoramiri.telegramdialog.two.TeleDialogDoubleListener

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

        private fun showPopupMenu(it: View) {
            val popupMenu = PopupMenu(it.context,it)
            popupMenu.menuInflater.inflate(R.menu.product_recyclerview_popup_menu,popupMenu.menu)
            popupMenu.setOnMenuItemClickListener {

                when(it.itemId) {
                    R.id.delete -> {

                        val dialog = TeleDialogDouble(itemView.context)
                            .setDirection(TeleDirection.RTL)
                            .setTitle("حذف محصول")
                            .setMessage("محصول و موجودی های آن حذف خواهند شد\n" +
                                    "تمامی تراکنش ها باقی خواهند ماند، با حذف\n" +
                                    "محصول موافقید ؟")
                            .setButtonOneText("حذف")
                            .setButtonOneTextColor(R.color.kalayar_red_color)
                            .setButtonOneRippleColor(R.color.kalayar_red_color_tint)
                            .setButtonTwoText("لغو")
                            .setButtonTwoTextColor(R.color.kalayar_blue_color)
                            .setButtonTwoRippleColor(R.color.kalayar_blue_color_tint)

                        dialog.setOnClickListener(object : TeleDialogDoubleListener {
                            override fun onFirstButtonClicked() {
                                productEventListener.onRemoveProduct(dataList[absoluteAdapterPosition],absoluteAdapterPosition)
                                dialog.dismiss()
                            }

                            override fun onSecondButtonClicked() {
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