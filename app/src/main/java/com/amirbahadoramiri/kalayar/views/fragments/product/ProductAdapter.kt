package com.amirbahadoramiri.kalayar.views.fragments.product

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.appcompat.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import androidx.sqlite.db.SupportSQLiteQuery
import com.amirbahadoramiri.kalayar.R
import com.amirbahadoramiri.kalayar.core.models.Product
import com.amirbahadoramiri.kalayar.databinding.ProductFragmentAddBottomSheetBinding
import com.amirbahadoramiri.kalayar.tools.database.PublicDatabase
import com.amirbahadoramiri.kalayar.tools.logger.Logger
import com.github.amirbahadoramiri.telegramdialog.library.TeleDirection
import com.github.amirbahadoramiri.telegramdialog.two.TeleDialogDouble
import com.github.amirbahadoramiri.telegramdialog.two.TeleDialogDoubleListener
import com.google.android.material.bottomsheet.BottomSheetDialog
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.CompletableObserver
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.core.SingleObserver
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class ProductAdapter : RecyclerView.Adapter<ProductAdapter.ProductHolder>() {

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
                                    PublicDatabase.getPublicDatabase(itemView.context)?.getPublicDAO()
                                        ?.deleteProduct(dataList.get(absoluteAdapterPosition))
                                        ?.subscribeOn(Schedulers.io())
                                        ?.observeOn(AndroidSchedulers.mainThread())
                                        ?.subscribe(object : CompletableObserver {
                                            override fun onSubscribe(d: Disposable) {}
                                            override fun onComplete() {
                                                removeProduct(absoluteAdapterPosition)
                                            }
                                            override fun onError(e: Throwable) {}
                                        })
                                    dialog.dismiss()
                                }

                                override fun onSecondButtonClicked() {
                                    dialog.dismiss()
                                }
                            })

                            dialog.show()

                        }
                        R.id.edit -> {
                            openProduct(itemView.context,dataList.get(absoluteAdapterPosition),absoluteAdapterPosition)
                        }
                    }

                    true
                }
                popupMenu.show()

            }

            itemView.setOnClickListener {
                openProduct(itemView.context,dataList.get(absoluteAdapterPosition),absoluteAdapterPosition)
            }

        }

        fun openProduct(context: Context,product: Product,position: Int) {

            val bottomSheetDialog = BottomSheetDialog(context)
            val sheetBinding = ProductFragmentAddBottomSheetBinding.inflate(bottomSheetDialog.layoutInflater)
            bottomSheetDialog.setContentView(sheetBinding.root)
            sheetBinding.product = product

            sheetBinding.confirmButton.setOnClickListener {

                val product_name = sheetBinding.productName.text.toString()
                val product_unit = sheetBinding.productUnit.text.toString()
                val product_price = sheetBinding.productPrice.text.toString()

                sheetBinding.productNameLayout.isErrorEnabled = false
                sheetBinding.productPriceLayout.isErrorEnabled = false
                sheetBinding.productUnitLayout.isErrorEnabled = false

                if ( product_name.isEmpty() ) {
                    sheetBinding.productNameLayout.setError(context.getString(R.string.is_necessary))
                    Toast.makeText(context, context.getString(R.string.fill_necessary_field), Toast.LENGTH_SHORT).show()
                }
                else if ( product_price.isEmpty() ) {
                    sheetBinding.productPriceLayout.setError(context.getString(R.string.is_necessary))
                    Toast.makeText(context, context.getString(R.string.fill_necessary_field), Toast.LENGTH_SHORT).show()
                }
                else if ( product_unit.isEmpty() ) {
                    sheetBinding.productUnitLayout.setError(context.getString(R.string.is_necessary))
                    Toast.makeText(context, context.getString(R.string.fill_necessary_field), Toast.LENGTH_SHORT).show()
                } else {

                    product.product_name = product_name
                    product.product_unit = product_unit
                    product.product_price = product_price.toLong()

                    PublicDatabase.getPublicDatabase(context)
                        ?.getPublicDAO()
                        ?.updateProduct(product)
                        ?.subscribeOn(Schedulers.io())
                        ?.observeOn(AndroidSchedulers.mainThread())
                        ?.subscribe(object : CompletableObserver {
                            override fun onSubscribe(d: Disposable) {}
                            override fun onComplete() {
                                updateItem(product,position)
                                bottomSheetDialog.dismiss()
                            }
                            override fun onError(e: Throwable) {
                                Logger.debug(e.message)
                            }
                        })
                }

            }

            bottomSheetDialog.show()

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

    fun updateItem(product: Product,position: Int) {

        Observable.timer(500, TimeUnit.MILLISECONDS)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(object : Observer<Long> {
                            override fun onSubscribe(d: Disposable) {}
                            override fun onNext(t: Long) {}
                            override fun onError(e: Throwable) {}

                            override fun onComplete() {

                                removeProduct(position)

                                Observable.timer(500, TimeUnit.MILLISECONDS)
                                    .subscribeOn(Schedulers.io())
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribe(object : Observer<Long> {
                                        override fun onSubscribe(d: Disposable) {}
                                        override fun onNext(t: Long) {}
                                        override fun onError(e: Throwable) {}

                                        override fun onComplete() {

                                            addProduct(product,position)

                                        }
                                    })

                            }
                        })

    }

    fun reloadDatabase(context: Context) {
        PublicDatabase.getPublicDatabase(context)
            ?.getPublicDAO()
            ?.getAllProduct()
            ?.subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe(object : SingleObserver<List<Product>> {
                override fun onSubscribe(d: Disposable) {}
                override fun onSuccess(t: List<Product>) {
                    dataList.clear()
                    dataList.addAll(t)
                    notifyDataSetChanged()
                }
                override fun onError(e: Throwable) {}
            })


    }

    fun search(context: Context, productName: String) {

        PublicDatabase.getPublicDatabase(context)
            ?.getPublicDAO()
            ?.searchProduct(productName)
            ?.subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe(object : SingleObserver<List<Product>> {
                override fun onSubscribe(d: Disposable) {}
                override fun onSuccess(t: List<Product>) {
                    dataList.clear()
                    dataList.addAll(t)
                    notifyDataSetChanged()
                }
                override fun onError(e: Throwable) {}
            })

    }

    fun order(context: Context,query: SupportSQLiteQuery) {
        PublicDatabase.getPublicDatabase(context)
            ?.getPublicDAO()
            ?.getAllProduct(query)
            ?.subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe(object : SingleObserver<List<Product>> {
                override fun onSubscribe(d: Disposable) {}
                override fun onSuccess(t: List<Product>) {
                    dataList.clear()
                    dataList.addAll(t)
                    notifyDataSetChanged()
                }
                override fun onError(e: Throwable) {}
            })
    }

}