package com.amirbahadoramiri.kalayar.presentation.ui.fragments.money

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.lifecycle.ViewModelProvider
import com.amirbahadoramiri.kalayar.R
import com.amirbahadoramiri.kalayar.databinding.MoneyFragmentBinding
import com.amirbahadoramiri.kalayar.domain.models.Product
import com.amirbahadoramiri.kalayar.presentation.base.BaseFragment
import com.amirbahadoramiri.kalayar.tools.text_utils.TextUtils

class MoneyFragment : BaseFragment() {

    lateinit var binding: MoneyFragmentBinding
    lateinit var moneyFragmentViewModel: MoneyFragmentViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = MoneyFragmentBinding.inflate(inflater)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setup()
    }

    private fun setup() {

        moneyFragmentViewModel = ViewModelProvider(this)[MoneyFragmentViewModel::class]
        moneyFragmentViewModel.getAllProductLiveData.observe(viewLifecycleOwner) {
            calculateSummary(it)
        }
        moneyFragmentViewModel.getAllProduct()

        customOnBackPressed()

        binding.backBtn.setOnClickListener {
            popBackStack()
        }
    }

    private fun calculateSummary(products: MutableList<Product>) {
        var totalValue: Long = 0
        var totalItems: Long = 0
        for (product in products) {
            totalValue += (product.product_price * product.product_count)
            totalItems += product.product_count
        }

        val mojodi = TextUtils.numberFormat(totalValue) + getString(R.string.toman)
        binding.totalValue.text = mojodi
        binding.totalItems.text = totalItems.toString()
        binding.productTypesCount.text = products.size.toString()
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