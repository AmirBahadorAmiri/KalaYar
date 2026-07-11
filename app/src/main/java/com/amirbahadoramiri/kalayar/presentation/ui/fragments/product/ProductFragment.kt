package com.amirbahadoramiri.kalayar.presentation.ui.fragments.product

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.activity.OnBackPressedCallback
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.amirbahadoramiri.kalayar.R
import com.amirbahadoramiri.kalayar.databinding.ProductFragmentAddBottomSheetBinding
import com.amirbahadoramiri.kalayar.databinding.ProductFragmentBinding
import com.amirbahadoramiri.kalayar.domain.models.Product
import com.amirbahadoramiri.kalayar.presentation.base.BaseFragment
import com.amirbahadoramiri.kalayar.tools.text_utils.TextUtils
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.milliseconds


class ProductFragment : BaseFragment(), ProductEventListener {

    lateinit var binding: ProductFragmentBinding
    private lateinit var productFragmentViewModel: ProductFragmentViewModel
    private val productAdapter = ProductAdapter(this)
    private var ASC = true

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = ProductFragmentBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setup()
    }

    private fun setup() {

        productFragmentViewModel = ViewModelProvider(this)[ProductFragmentViewModel::class]
        productFragmentViewModel.getAllProductLiveData.observe(viewLifecycleOwner) {
            productAdapter.reloadProduct(it)
        }

        customOnBackPressed()

        binding.productRecyclerview.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = productAdapter
            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    when {
                        dy > 0 && binding.addProduct.isVisible -> binding.addProduct.hide()
                        dy < 0 && !binding.addProduct.isVisible -> binding.addProduct.show()
                    }
                }
            })
        }
        productFragmentViewModel.getAllProduct()

        binding.productSearch.addTextChangedListener(object : TextWatcher {
            var job: Job = Job()
            override fun afterTextChanged(s: Editable?) {
                if (job.isActive) {
                    job.cancel()
                }
                job = lifecycleScope.launch {
                    delay(500.milliseconds)
                    val product_name = binding.productSearch.text.toString()
                    if (product_name.isEmpty()) {
                        productFragmentViewModel.getAllProductLiveData.value?.let {
                            productAdapter.reloadProduct(it)
                        }
                    } else {
                        productFragmentViewModel.getAllProductLiveData.value?.filter {
                            if (it.product_name.contains(product_name)) true else false
                        }?.let {
                            productAdapter.reloadProduct(it)
                        }
                    }
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        binding.productRecyclerviewTitleName.setOnClickListener {
            productFragmentViewModel.getAllProductLiveData.value?.filter {
                if (it.product_name.contains(binding.productSearch.text.toString())) true else false
            }?.let {
                productAdapter.reloadProduct(if (ASC) it.sortedBy { it.product_name } else it.sortedByDescending { it.product_name })
            }
            ASC = !ASC
        }

        binding.productRecyclerviewTitlePrice.setOnClickListener {
            productFragmentViewModel.getAllProductLiveData.value?.filter {
                if (it.product_name.contains(binding.productSearch.text.toString())) true else false
            }?.let {
                productAdapter.reloadProduct(if (ASC) it.sortedBy { it.product_price } else it.sortedByDescending { it.product_price })
            }
            ASC = !ASC
        }

        binding.backBtn.setOnClickListener {
            popBackStack()
        }

        binding.addProduct.setOnClickListener {
            onShowProduct(null, 0)
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

    override fun onShowProduct(product: Product?, position: Int) {
        val bottomSheetDialog = BottomSheetDialog(requireContext())
        val sheetBinding =
            ProductFragmentAddBottomSheetBinding.inflate(bottomSheetDialog.layoutInflater)
        bottomSheetDialog.setContentView(sheetBinding.root)
        if (product != null) sheetBinding.product = product

        sheetBinding.productCount.addTextChangedListener(
            textChangeListener(
                sheetBinding.productCount, 9
            )
        )
        sheetBinding.productPrice.addTextChangedListener(
            textChangeListener(
                sheetBinding.productPrice, 12
            )
        )

        sheetBinding.confirmButton.setOnClickListener {
            val product_name = sheetBinding.productName.text.toString()
            val product_unit = sheetBinding.productUnit.text.toString()
            val product_price = sheetBinding.productPrice.text.toString()
            val product_count = sheetBinding.productCount.text.toString()

            sheetBinding.productNameLayout.isErrorEnabled = false
            sheetBinding.productPriceLayout.isErrorEnabled = false
            sheetBinding.productUnitLayout.isErrorEnabled = false
            sheetBinding.productCountLayout.isErrorEnabled = false

            if (product_name.isEmpty()) {
                sheetBinding.productNameLayout.error = getString(R.string.is_necessary)
                toast(getString(R.string.fill_necessary_field))
            } else if (product_price.isEmpty()) {
                sheetBinding.productPriceLayout.error = getString(R.string.is_necessary)
                toast(getString(R.string.fill_necessary_field))
            } else if (product_count.isEmpty()) {
                sheetBinding.productCountLayout.error = getString(R.string.is_necessary)
                toast(getString(R.string.fill_necessary_field))
            } else if (product_unit.isEmpty()) {
                sheetBinding.productUnitLayout.error = getString(R.string.is_necessary)
                toast(getString(R.string.fill_necessary_field))
            } else {
                if (product == null) {
                    val newProduct = Product(
                        product_name, product_unit, product_price.toLong(), product_count.toLong()
                    )
                    onAddProduct(newProduct, 0)
                } else {
                    product.product_name = product_name
                    product.product_unit = product_unit
                    product.product_price = product_price.toLong()
                    product.product_count = product_count.toLong()
                    onUpdateProduct(product, position)
                }
                bottomSheetDialog.dismiss()
            }
        }
        bottomSheetDialog.show()
    }

    override fun onRemoveProduct(product: Product, position: Int) {
        lifecycleScope.launch {
            delay(500.milliseconds)
            productAdapter.removeProduct(position)
            productFragmentViewModel.removeProduct(product)
        }
    }

    override fun onUpdateProduct(product: Product, position: Int) {
        lifecycleScope.launch {
            delay(500.milliseconds)
            productAdapter.removeProduct(position)
            delay(500.milliseconds)
            productAdapter.addProduct(product, position)
            if (position == 0) binding.productRecyclerview.scrollToPosition(0)
            productFragmentViewModel.updateProduct(product)
        }
    }

    override fun onAddProduct(product: Product, position: Int) {
        lifecycleScope.launch {
            delay(500.milliseconds)
            productAdapter.addProduct(product, position)
            binding.productRecyclerview.scrollToPosition(0)
            productFragmentViewModel.addProduct(product)
        }
    }

    fun textChangeListener(editText: EditText, maxLength: Int): TextWatcher {
        return object : TextWatcher {

            var isProgrammaticChange = false

            override fun afterTextChanged(s: Editable?) {
                val text = s.toString().replace(",", "")
                if (text.isEmpty() || text.length > maxLength || isProgrammaticChange) return
                isProgrammaticChange = true
                val formatted = TextUtils.formatMoney(text)
                editText.setText(formatted)
                editText.setSelection(formatted.length)
                isProgrammaticChange = false
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
        }
    }

}