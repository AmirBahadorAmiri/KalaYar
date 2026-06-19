package com.amirbahadoramiri.kalayar.views.fragments.transaction

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.amirbahadoramiri.kalayar.R
import com.amirbahadoramiri.kalayar.core.models.Product
import com.amirbahadoramiri.kalayar.tools.database.PublicDatabase
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.SingleObserver
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers

class TransactionSearchProductAdapter() : RecyclerView.Adapter<TransactionSearchProductAdapter.ProductHolder>() {

    private val dataList = mutableListOf<Product>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ProductHolder(LayoutInflater.from(parent.context).inflate(R.layout.transaction_product_recyclerview_item,parent,false))
    override fun onBindViewHolder(holder: ProductHolder, position: Int) = holder.bind(position)
    override fun getItemCount() = dataList.size

    private var onItemClickListener: OnItemClickListener? = null

    fun setOnItemClickListener(onItemClickListener: OnItemClickListener) {
        this.onItemClickListener = onItemClickListener
    }

    fun getDataList() = dataList

    inner class ProductHolder : RecyclerView.ViewHolder {

        val product_recyclerview_name : AppCompatTextView
        val product_recyclerview_price : AppCompatTextView
        val product_recyclerview_count : AppCompatTextView

        constructor(itemView: View) : super(itemView) {
            this.product_recyclerview_name = itemView.findViewById(R.id.product_recyclerview_name)
            this.product_recyclerview_price = itemView.findViewById(R.id.product_recyclerview_price)
            this.product_recyclerview_count = itemView.findViewById(R.id.product_recyclerview_count)
        }

        fun bind(position: Int) {
            product_recyclerview_name.setText(dataList.get(absoluteAdapterPosition).product_name)
            product_recyclerview_price.setText(dataList.get(absoluteAdapterPosition).getProductPrice())
            product_recyclerview_count.setText(dataList.get(absoluteAdapterPosition).getProductCount())

            itemView.setOnClickListener {
                onItemClickListener?.onClick(dataList.get(absoluteAdapterPosition))
            }

        }

    }

    fun addProducts(addList: List<Product>) {
        dataList.addAll(addList)
        notifyDataSetChanged()
    }

    fun addProduct() {

    }

    fun removeProduct() {

    }

    fun updateProduct() {

    }

    fun searchProduct(context: Context, productName: String) {
        PublicDatabase.getPublicDatabase(context)?.getPublicDAO()
            ?.searchProduct(productName)
            ?.subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe(object : SingleObserver<List<Product>> {
                override fun onSubscribe(d: Disposable) {}
                override fun onSuccess(list: List<Product>) {
                    dataList.clear()
                    addProducts(list)
                }
                override fun onError(e: Throwable) {}
            })
    }

    fun whereCount(context: Context, productCount: Long) {
        PublicDatabase.getPublicDatabase(context)?.getPublicDAO()
            ?.getAllProduct(productCount)
            ?.subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe(object : SingleObserver<List<Product>> {
                override fun onSubscribe(d: Disposable) {}
                override fun onSuccess(list: List<Product>) {
                    dataList.clear()
                    addProducts(list)
                }
                override fun onError(e: Throwable) {}
            })
    }

    fun reloadDatabase(context: Context) {
        PublicDatabase.getPublicDatabase(context)?.getPublicDAO()
            ?.getAllProduct()
            ?.subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe(object : SingleObserver<List<Product>> {
                override fun onSubscribe(d: Disposable) {}
                override fun onSuccess(list: List<Product>) {
                    dataList.clear()
                    addProducts(list)
                }
                override fun onError(e: Throwable) {}
            })
    }

    interface OnItemClickListener {
        fun onClick(product: Product)
    }

}