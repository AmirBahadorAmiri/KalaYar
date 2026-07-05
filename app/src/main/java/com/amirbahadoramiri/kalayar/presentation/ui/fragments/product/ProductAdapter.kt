package com.amirbahadoramiri.kalayar.presentation.ui.fragments.product

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.appcompat.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import com.amirbahadoramiri.kalayar.R
import com.amirbahadoramiri.kalayar.domain.models.Product
import com.github.amirbahadoramiri.telegramdialog.library.TeleDirection
import com.github.amirbahadoramiri.telegramdialog.two.TeleDialogDouble
import com.github.amirbahadoramiri.telegramdialog.two.TeleDialogDoubleListener

class ProductAdapter(val productEventListener: ProductEventListener) : RecyclerView.Adapter<ProductAdapter.ProductHolder>() {

    private var dataList: MutableList<Product> = mutableListOf()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ProductHolder(LayoutInflater.from(parent.context).inflate(R.layout.product_recyclerview_item,parent,false))
    override fun getItemCount() = dataList.size
    override fun onBindViewHolder(holder: ProductHolder, position: Int) { holder.bind() }


    inner class ProductHolder : RecyclerView.ViewHolder {

        var product_recyclerview_name : AppCompatTextView
        var product_recyclerview_price : AppCompatTextView
        var menu_icon : AppCompatImageView

        constructor(itemView: View) : super(itemView) {

            product_recyclerview_name = itemView.findViewById(R.id.product_recyclerview_name)
            product_recyclerview_price = itemView.findViewById(R.id.product_recyclerview_price)
            menu_icon = itemView.findViewById(R.id.menu_icon)

        }

        fun bind() {

            product_recyclerview_name.setText(dataList.get(absoluteAdapterPosition).product_name)
            product_recyclerview_price.setText(dataList.get(absoluteAdapterPosition).formatMoney())

            menu_icon.setOnClickListener {
                showPopupMenu(it)
            }
            itemView.setOnClickListener { productEventListener.onShowProduct(dataList[absoluteAdapterPosition],absoluteAdapterPosition) }

        }

        private fun showPopupMenu(it: View) {
            val popupMenu = PopupMenu(it.context,it)
            popupMenu.menuInflater.inflate(R.menu.product_fragment_recyclerview_popup_menu,popupMenu.menu)
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