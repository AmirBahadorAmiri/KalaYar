package com.amirbahadoramiri.kalayar.views.fragments.transaction

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.recyclerview.widget.LinearLayoutManager
import com.amirbahadoramiri.kalayar.R
import com.amirbahadoramiri.kalayar.core.models.Product
import com.amirbahadoramiri.kalayar.core.models.Transaction
import com.amirbahadoramiri.kalayar.core.models.TransactionItem
import com.amirbahadoramiri.kalayar.core.models.TransactionType
import com.amirbahadoramiri.kalayar.databinding.TransactionAddProductFragmentBinding
import com.amirbahadoramiri.kalayar.databinding.TransactionAddProductSearchSheetBinding
import com.amirbahadoramiri.kalayar.databinding.TransactionAddProductSheetBinding
import com.amirbahadoramiri.kalayar.tools.database.PublicDatabase
import com.amirbahadoramiri.kalayar.tools.logger.Logger
import com.amirbahadoramiri.kalayar.views.fragments.base.BaseFragment
import com.google.android.material.bottomsheet.BottomSheetDialog
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.CompletableObserver
import io.reactivex.rxjava3.core.SingleObserver
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers

class AddTransactionFragment : BaseFragment() {

    companion object {

        private var instance: AddTransactionFragment? = null

        fun createInstance(): AddTransactionFragment {
            return AddTransactionFragment()
        }

        fun getInstance(): AddTransactionFragment {

            if (instance == null) {
                instance = createInstance()
            }
            return instance!!
        }

    }

    lateinit var binding: TransactionAddProductFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = TransactionAddProductFragmentBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        findViews()
        setup()
    }

    private fun findViews() {
    }

    private fun setup() {
        customOnBackPressed()

        val layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL,false)
        val transactionProductAdapter = TransactionProductAdapter()
        binding.productRecyclerview.layoutManager = layoutManager
        binding.productRecyclerview.adapter = transactionProductAdapter

        binding.increase.setOnClickListener {
            /*
            *
            * باید چک بشه اگر ایتمی قابلیت این رو نداشت که
            * زیاد بشه و داخل REcyclerView وجود داشت حذف بشه
            *
            *
            *     میشه حتی لیست رو Clear کرد
            *
            * */
        }

        binding.decrease.setOnClickListener {

            /*
            *
            * باید چک بشه اگر ایتمی قابلیت این رو نداشت که
            * کم بشه و داخل REcyclerView وجود داشت حذف بشه
            *
            *
            *  *     میشه حتی لیست رو Clear کرد
            *
            * */

        }

        binding.addProduct.setOnClickListener {

            val searchProductBottomSheet = BottomSheetDialog(requireContext())
            val searchProductBinding = TransactionAddProductSearchSheetBinding.inflate(layoutInflater)
            searchProductBottomSheet.setContentView(searchProductBinding.root)
            val transactionSearchProductAdapter = TransactionSearchProductAdapter()
            transactionSearchProductAdapter.setOnItemClickListener(object : TransactionSearchProductAdapter.OnItemClickListener {
                override fun onClick(product: Product) {

                    var isAvailable = false
                    for ( item in transactionProductAdapter.getList() ) {
                        if ( item.product_id == product.product_id ) {
                            isAvailable = true
                            toast("انتخاب محصول تکراری مجاز نیست")
                        }
                    }

                    searchProductBottomSheet.dismiss()

                    if ( !isAvailable ) {
                        val addProductBottomSheet = BottomSheetDialog(requireContext())
                        val addProductSheetBinding = TransactionAddProductSheetBinding.inflate(layoutInflater)
                        addProductBottomSheet.setContentView(addProductSheetBinding.root)

                        addProductSheetBinding.transactionProductUnit.text = product.product_unit
                        addProductSheetBinding.transactionProductCount.text = product.getProductCount()

                        addProductSheetBinding.transactionProductChangeCount.addTextChangedListener(object : TextWatcher {
                            override fun afterTextChanged(editable: Editable?) {
                                val text = addProductSheetBinding.transactionProductChangeCount.text.toString()
                                if ( text.isNotEmpty() ) {

                                    val value = text.toInt()

                                    if ( value < 1 ) {
                                        addProductSheetBinding.transactionProductChangeCount.setText("1")
                                    } else if ( binding.transactionType.checkedButtonId == R.id.decrease ) {
                                        if ( value > product.product_count.toInt() ) {
                                            addProductSheetBinding.transactionProductChangeCount.setText(product.product_count.toString())
                                        }
                                    }
                                }
                            }
                            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
                            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
                        })

                        addProductSheetBinding.productPlus.setOnClickListener {
                            val text = addProductSheetBinding.transactionProductChangeCount.text.toString()
                            if ( text.isNotEmpty() ) {
                                val value = text.toInt()
                                val newValue = (value+1).toString()
                                if ( binding.transactionType.checkedButtonId == R.id.increase ) {
                                    addProductSheetBinding.transactionProductChangeCount.setText(newValue)
                                } else if ( binding.transactionType.checkedButtonId == R.id.decrease ) {
                                    if ( value < product.product_count.toInt() ) {
                                        addProductSheetBinding.transactionProductChangeCount.setText(newValue)
                                    }
                                }
                            } else {
                                addProductSheetBinding.transactionProductChangeCount.setText("1")
                            }
                        }

                        addProductSheetBinding.productMinus.setOnClickListener {
                            val text = addProductSheetBinding.transactionProductChangeCount.text.toString()
                            if ( text.isNotEmpty() ) {
                                val value = text.toInt()
                                if ( value > 1) addProductSheetBinding.transactionProductChangeCount.setText((value-1).toString())
                            } else{
                                addProductSheetBinding.transactionProductChangeCount.setText("1")
                            }
                        }

                        addProductSheetBinding.confirmButton.setOnClickListener {
                            val change_value = addProductSheetBinding.transactionProductChangeCount.text.toString().toLong()
                            val new_value = if ( binding.transactionType.checkedButtonId == R.id.increase ) (product.product_count+addProductSheetBinding.transactionProductChangeCount.text.toString().toLong()) else (product.product_count-addProductSheetBinding.transactionProductChangeCount.text.toString().toLong())
                            product.change_value = change_value
                            product.new_value = new_value
                            transactionProductAdapter.addItem(product)
                            transactionSearchProductAdapter.getDataList().remove(product)
                            addProductBottomSheet.dismiss()
                        }

                        addProductBottomSheet.show()
                    }

                }
            })
            searchProductBinding.productRecyclerview.layoutManager = LinearLayoutManager(requireContext(),
                LinearLayoutManager.VERTICAL,false)
            searchProductBinding.productRecyclerview.adapter = transactionSearchProductAdapter
            if ( binding.transactionType.checkedButtonId == R.id.increase ) {
                transactionSearchProductAdapter.reloadDatabase(requireContext())
            } else {
                transactionSearchProductAdapter.whereCount(requireContext(),0)
            }

            searchProductBinding.productSearch.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(p0: Editable?) {
                    val text = searchProductBinding.productSearch.text.toString()

                    if ( text.isEmpty() ) {
                        transactionSearchProductAdapter.reloadDatabase(requireContext())
                    } else {
                        transactionSearchProductAdapter.searchProduct(requireContext(),text)
                    }

                }
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            })

            searchProductBottomSheet.show()

        }

        binding.confirmButton.setOnClickListener {

            val transactionTitle = binding.transactionTitle.text.toString()
            val transactionDescription = binding.transactionDescription.text.toString()

            if (transactionProductAdapter.getList().isEmpty())
                toast("هیچ محصولی اضافه نکردید")
            else if (transactionTitle.isEmpty()) {
                toast("تیتر تراکنش نمیتواند خالی باشد")
            } else {
                val transaction = Transaction(if (binding.transactionType.checkedButtonId==R.id.increase) TransactionType.INCREASE else TransactionType.DECREASE,transactionTitle,
                    System.currentTimeMillis(),transactionDescription)

                PublicDatabase.getPublicDatabase(requireContext())?.getPublicDAO()
                    ?.addTransaction(transaction)
                    ?.subscribeOn(Schedulers.io())
                    ?.observeOn(AndroidSchedulers.mainThread())
                    ?.subscribe(object : SingleObserver<Long> {
                        override fun onSubscribe(d: Disposable) {}
                        override fun onSuccess(id: Long) {
                            transaction.transaction_id = id

                            val transactionItems = mutableListOf<TransactionItem>()

                            for (productItem in transactionProductAdapter.getList()) {
                                val transactionItem = TransactionItem(productItem.product_id,productItem.product_name,productItem.product_unit,productItem.product_price,productItem.product_count,productItem.change_value,productItem.new_value, transaction_id = transaction.transaction_id)
                                transactionItems.add(transactionItem)

                                productItem.product_count = productItem.new_value

                            }

                            PublicDatabase.getPublicDatabase(requireContext())?.getPublicDAO()
                                ?.addTransactionItems(transactionItems)
                                ?.subscribeOn(Schedulers.io())
                                ?.observeOn(AndroidSchedulers.mainThread())
                                ?.subscribe(object : CompletableObserver {
                                    override fun onSubscribe(d: Disposable) {}
                                    override fun onComplete() {

                                        PublicDatabase.getPublicDatabase(requireContext())?.getPublicDAO()
                                            ?.updateProducts(transactionProductAdapter.getList())
                                            ?.subscribeOn(Schedulers.io())
                                            ?.observeOn(AndroidSchedulers.mainThread())
                                            ?.subscribe(object : CompletableObserver {
                                                override fun onSubscribe(d: Disposable) {}
                                                override fun onComplete() {
                                                    popBackStack()
                                                }
                                                override fun onError(e: Throwable) {
                                                    Logger.debug(e)
                                                }
                                            })

                                    }
                                    override fun onError(e: Throwable) {
                                        Logger.debug(e)
                                    }
                                })

                        }
                        override fun onError(e: Throwable) {
                            Logger.debug(e)
                        }
                    })

            }
        }

        binding.backBtn.setOnClickListener {
            popBackStack()
        }

    }

    private fun customOnBackPressed() {
        val backPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                popBackStack()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this, backPressedCallback)
    }

}
