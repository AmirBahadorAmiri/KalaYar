package com.amirbahadoramiri.kalayar.presentation.ui.fragments.transaction

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.amirbahadoramiri.kalayar.R
import com.amirbahadoramiri.kalayar.databinding.TransactionAddProductFragmentBinding
import com.amirbahadoramiri.kalayar.databinding.TransactionAddProductSearchSheetBinding
import com.amirbahadoramiri.kalayar.databinding.TransactionAddProductSheetBinding
import com.amirbahadoramiri.kalayar.domain.models.Product
import com.amirbahadoramiri.kalayar.domain.models.Transaction
import com.amirbahadoramiri.kalayar.domain.models.TransactionType
import com.amirbahadoramiri.kalayar.presentation.base.BaseFragment
import com.amirbahadoramiri.kalayar.tools.text_utils.TextUtils
import com.google.android.material.bottomsheet.BottomSheetDialog

class AddTransactionFragment : BaseFragment(), OnButtonCheckListener {

    lateinit var binding: TransactionAddProductFragmentBinding
    lateinit var addTransactionViewModel: AddTransactionViewModel

    val transactionProductAdapter = TransactionProductAdapter()
    val transactionSearchProductAdapter = TransactionSearchProductAdapter()

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
        setup()
    }

    private fun setup() {

        calculateLastPrice()

        addTransactionViewModel = ViewModelProvider(this)[AddTransactionViewModel::class]
        addTransactionViewModel.transactionAddLiveData.observe(viewLifecycleOwner) {
            if (it) popBackStack()
        }
        addTransactionViewModel.allProductShownLiveData.observe(viewLifecycleOwner) {
            transactionSearchProductAdapter.addProducts(it)
        }
        addTransactionViewModel.getAllProductLiveData.observe(viewLifecycleOwner) {
            if ( binding.transactionType.checkedButtonId == R.id.increase ) {
                addTransactionViewModel.getAllProductLiveData.value?.let {
                    addTransactionViewModel.allProductShownLiveData.postValue(it.toMutableList())
                }
            } else {
                addTransactionViewModel.getAllProductLiveData.value?.filter {
                    if (it.product_count>0) true else false
                }?.let {
                    addTransactionViewModel.allProductShownLiveData.postValue(it.toMutableList())
                }
            }
        }
        addTransactionViewModel.getAllProduct()

        customOnBackPressed()

        binding.productRecyclerview.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = transactionProductAdapter
            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    when {
                        dy > 0 && binding.addProduct.isVisible -> {
                            binding.addProduct.hide()
                            binding.confirmButton.hide()
                        }
                        dy < 0 && !binding.addProduct.isVisible -> {
                            binding.addProduct.show()
                            binding.confirmButton.show()
                        }
                    }
                }
            })
        }

        binding.transactionType.addOnButtonCheckedListener { group, checkedId, isChecked ->
            onCheck(checkedId,isChecked)
        }

        binding.addProduct.setOnClickListener {

            val searchProductBottomSheet = BottomSheetDialog(requireContext())
            val searchProductBinding = TransactionAddProductSearchSheetBinding.inflate(layoutInflater)
            searchProductBottomSheet.setContentView(searchProductBinding.root)
            searchProductBinding.productRecyclerview.apply {
                layoutManager = LinearLayoutManager(requireContext())
                adapter = transactionSearchProductAdapter
            }

            transactionSearchProductAdapter.setOnItemClickListener(object : TransactionSearchProductAdapter.OnItemClickListener {
                override fun onClick(product: Product) {
                    searchProductBottomSheet.dismiss()

                    val addProductBottomSheet = BottomSheetDialog(requireContext())
                    val addProductSheetBinding = TransactionAddProductSheetBinding.inflate(layoutInflater)
                    addProductBottomSheet.setContentView(addProductSheetBinding.root)
                    addProductSheetBinding.product = product

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
                        val change_amount = addProductSheetBinding.transactionProductChangeCount.text.toString().toLong()
                        val final_value = if ( binding.transactionType.checkedButtonId == R.id.increase ) (product.product_count+addProductSheetBinding.transactionProductChangeCount.text.toString().toLong()) else (product.product_count-addProductSheetBinding.transactionProductChangeCount.text.toString().toLong())
                        product.change_amount = change_amount
                        product.final_value = final_value
                        transactionProductAdapter.addItem(product)
                        calculateLastPrice()
                        addTransactionViewModel.allProductShownLiveData.value?.remove(product)
                        transactionSearchProductAdapter.getDataList().remove(product)
                        addProductBottomSheet.dismiss()
                    }

                    addProductBottomSheet.show()

                }
            })

            searchProductBinding.productSearch.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(p0: Editable?) {
                    val text = searchProductBinding.productSearch.text.toString()

                    if ( text.isEmpty() ) {
                        addTransactionViewModel.allProductShownLiveData.value?.let {
                            transactionSearchProductAdapter.addProducts(it)
                        }
                    } else {
                        addTransactionViewModel.allProductShownLiveData.value?.filter {
                            if (it.product_name.contains(text)) true else false
                        }?.let {
                            transactionSearchProductAdapter.addProducts(it)
                        }
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
                addTransactionViewModel.addTransaction(transaction,transactionProductAdapter.getList())
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

    fun calculateLastPrice() {
        var last_price = 0L
        transactionProductAdapter.getList().map { it.product_price*it.change_amount }.forEach {
            last_price+=it
        }
        val calculated_price = getString(R.string.transaction_last_price) + " ${TextUtils.formatMoney(last_price)}"
        binding.transactionLastPrice.text = calculated_price
    }

    override fun onCheck(checkedId: Int, isChecked: Boolean) {
        transactionProductAdapter.clearList()
        calculateLastPrice()
        addTransactionViewModel.getAllProduct()
        if (isChecked) {
            if (checkedId == R.id.increase) {
            } else if (checkedId == R.id.decrease) {
                val items = transactionProductAdapter.getList()
                val toRemove = mutableListOf<Product>()
                for (item in items) {
                    if (item.product_count <= 0) {
                        toRemove.add(item)
                    }
                }
                if (toRemove.isNotEmpty()) {
                    toast("برخی کالاها به دلیل عدم موجودی از لیست حذف شدند")
                    for (item in toRemove) {
                        val pos = items.indexOf(item)
                        transactionProductAdapter.removeItem(pos)
                    }
                }
            }
        }
    }

}
