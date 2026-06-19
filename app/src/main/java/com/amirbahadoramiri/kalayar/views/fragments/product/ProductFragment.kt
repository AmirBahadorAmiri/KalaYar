package com.amirbahadoramiri.kalayar.views.fragments.product

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.sqlite.db.SimpleSQLiteQuery
import androidx.sqlite.db.SupportSQLiteQuery
import com.amirbahadoramiri.kalayar.R
import com.amirbahadoramiri.kalayar.core.models.Product
import com.amirbahadoramiri.kalayar.databinding.ProductFragmentAddBottomSheetBinding
import com.amirbahadoramiri.kalayar.databinding.ProductFragmentBinding
import com.amirbahadoramiri.kalayar.tools.database.PublicDatabase
import com.amirbahadoramiri.kalayar.tools.logger.Logger
import com.amirbahadoramiri.kalayar.views.fragments.base.BaseFragment
import com.google.android.material.bottomsheet.BottomSheetDialog
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.SingleObserver
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers


class ProductFragment : BaseFragment() {

    companion object {

        private var instance: ProductFragment? = null

        fun createInstance(): ProductFragment {
            return ProductFragment()
        }

        fun getInstance(): ProductFragment {

            if (instance == null) {
                instance = createInstance()
            }
            return instance!!
        }

    }

    lateinit var binding: ProductFragmentBinding

    private var ASC = true

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = ProductFragmentBinding.inflate(inflater)
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

        val layoutManager = LinearLayoutManager(requireContext())
        binding.productRecyclerview.layoutManager = layoutManager
        val adapter = ProductAdapter()
        binding.productRecyclerview.adapter = adapter
        adapter.reloadDatabase(requireContext())

        binding.productRecyclerview.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                when {
                    dy > 0 && binding.addProduct.isVisible -> binding.addProduct.hide()
                    dy < 0 && !binding.addProduct.isVisible -> binding.addProduct.show()
                }
            }
        })

        binding.productSearch.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {

                val product_name = binding.productSearch.text.toString()
                if (product_name.isEmpty()) {
                    adapter.reloadDatabase(requireContext())
                } else {
                    adapter.search(requireContext(), product_name)
                }

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        binding.productRecyclerviewTitleName.setOnClickListener {
            val queryString = StringBuilder("SELECT * FROM product")

            if (binding.productSearch.text.toString().isNotEmpty()) {
                queryString.append(" WHERE product_name LIKE '%${binding.productSearch.text.toString()}%'")
            }

            if (ASC) {
                queryString.append(" ORDER BY product_name DESC")
            } else {
                queryString.append(" ORDER BY product_name ASC")
            }

            val query: SupportSQLiteQuery = SimpleSQLiteQuery(queryString.toString())
            adapter.order(requireContext(), query)
            ASC = !ASC
        }

        binding.productRecyclerviewTitlePrice.setOnClickListener {
            val queryString = StringBuilder("SELECT * FROM product")

            if (binding.productSearch.text.toString().isNotEmpty()) {
                queryString.append(" WHERE product_name LIKE '%${binding.productSearch.text.toString()}%'")
            }

            if (ASC) {
                queryString.append(" ORDER BY product_price DESC")
            } else {
                queryString.append(" ORDER BY product_price ASC")
            }

            val query: SupportSQLiteQuery = SimpleSQLiteQuery(queryString.toString())
            adapter.order(requireContext(), query)
            ASC = !ASC
        }

        binding.backBtn.setOnClickListener {
            popBackStack()
        }

        binding.addProduct.setOnClickListener {

            val bottomSheetDialog = BottomSheetDialog(requireContext())
            val sheetBinding =
                ProductFragmentAddBottomSheetBinding.inflate(bottomSheetDialog.layoutInflater)
            bottomSheetDialog.setContentView(sheetBinding.root)

            sheetBinding.confirmButton.setOnClickListener {

                val product_name = sheetBinding.productName.text.toString()
                val product_unit = sheetBinding.productUnit.text.toString()
                val product_price = sheetBinding.productPrice.text.toString()

                sheetBinding.productNameLayout.isErrorEnabled = false
                sheetBinding.productPriceLayout.isErrorEnabled = false
                sheetBinding.productUnitLayout.isErrorEnabled = false

                if (product_name.isEmpty()) {
                    sheetBinding.productNameLayout.setError(getString(R.string.is_necessary))
                    toast(getString(R.string.fill_necessary_field))
                } else if (product_price.isEmpty()) {
                    sheetBinding.productPriceLayout.setError(getString(R.string.is_necessary))
                    toast(getString(R.string.fill_necessary_field))
                } else if (product_unit.isEmpty()) {
                    sheetBinding.productUnitLayout.setError(getString(R.string.is_necessary))
                    toast(getString(R.string.fill_necessary_field))
                } else {
                    val product = Product(product_name, product_unit, product_price.toLong())
                    PublicDatabase.getPublicDatabase(requireContext())
                        ?.getPublicDAO()
                        ?.addProduct(product)
                        ?.subscribeOn(Schedulers.io())
                        ?.observeOn(AndroidSchedulers.mainThread())
                        ?.subscribe(object : SingleObserver<Long> {
                            override fun onSubscribe(d: Disposable) {}
                            override fun onSuccess(id: Long) {
                                product.product_id = id
                                adapter.addProduct(product)
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

    private fun customOnBackPressed() {
        val backPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                popBackStack()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this, backPressedCallback)
    }

}