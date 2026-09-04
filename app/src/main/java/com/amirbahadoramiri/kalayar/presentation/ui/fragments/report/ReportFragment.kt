package com.amirbahadoramiri.kalayar.presentation.ui.fragments.report

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.lifecycle.ViewModelProvider
import com.amirbahadoramiri.kalayar.R
import com.amirbahadoramiri.kalayar.databinding.ReportFragmentBinding
import com.amirbahadoramiri.kalayar.domain.models.ReportData
import com.amirbahadoramiri.kalayar.domain.models.Store
import com.amirbahadoramiri.kalayar.presentation.base.BaseFragment
import com.amirbahadoramiri.kalayar.tools.PDFUtils
import com.amirbahadoramiri.kalayar.tools.text_utils.TextUtils

class ReportFragment : BaseFragment() {

    private lateinit var binding: ReportFragmentBinding
    private lateinit var viewModel: ReportViewModel
    private var store: Store? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = ReportFragmentBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setup()
    }

    private fun setup() {
        viewModel = ViewModelProvider(this)[ReportViewModel::class.java]
        
        viewModel.reportLiveData.observe(viewLifecycleOwner) { data ->
            updateUI(data)
        }

        viewModel.getStoreLiveData.observe(viewLifecycleOwner) {
            store = it
        }
        
        viewModel.calculateReport()
        viewModel.getStore()

        customOnBackPressed()

        binding.backBtn.setOnClickListener {
            popBackStack()
        }

        binding.printBtn.setOnClickListener {
            viewModel.reportLiveData.value?.let { reportData ->
                PDFUtils.generateAndShareReportPDF(requireContext(), reportData, store)
            }
        }
    }

    private fun updateUI(data: ReportData) {
        // Inventory
        binding.totalValue.text = "${TextUtils.numberFormat(data.totalInventoryValue)} ${getString(R.string.toman)}"
        
        binding.rowTotalItems.rowTitle.text = getString(R.string.total_products_count)
        binding.rowTotalItems.rowValue.text = data.totalProductsCount.toString()
        
        binding.rowProductTypes.rowTitle.text = getString(R.string.product_types_count)
        binding.rowProductTypes.rowValue.text = data.productTypesCount.toString()

        binding.rowLowStock.rowTitle.text = getString(R.string.low_stock_products)
        binding.rowLowStock.rowValue.text = data.lowStockCount.toString()

        binding.rowHighValue.rowTitle.text = getString(R.string.high_value_stock)
        binding.rowHighValue.rowValue.text = data.highValueProduct

        // Sales
        binding.rowSalesToday.rowTitle.text = getString(R.string.sales_today)
        binding.rowSalesToday.rowValue.text = formatPrice(data.salesToday)

        binding.rowSales1m.rowTitle.text = getString(R.string.sales_1_month)
        binding.rowSales1m.rowValue.text = formatPrice(data.sales1Month)

        binding.rowSales3m.rowTitle.text = getString(R.string.sales_3_months)
        binding.rowSales3m.rowValue.text = formatPrice(data.sales3Months)

        binding.rowSales6m.rowTitle.text = getString(R.string.sales_6_months)
        binding.rowSales6m.rowValue.text = formatPrice(data.sales6Months)

        binding.rowSales1y.rowTitle.text = getString(R.string.sales_1_year)
        binding.rowSales1y.rowValue.text = formatPrice(data.sales1Year)

        binding.rowTotalSales.rowTitle.text = getString(R.string.total_sales)
        binding.rowTotalSales.rowValue.text = formatPrice(data.totalSales)

        binding.rowTotalProfit.rowTitle.text = getString(R.string.total_profit)
        binding.rowTotalProfit.rowValue.text = formatPrice(data.totalProfit)

        binding.rowTopSelling.rowTitle.text = getString(R.string.top_selling_product)
        binding.rowTopSelling.rowValue.text = data.topSellingProduct

        binding.rowLeastSelling.rowTitle.text = getString(R.string.least_selling_product)
        binding.rowLeastSelling.rowValue.text = data.leastSellingProduct

        binding.rowTransactionCount.rowTitle.text = getString(R.string.total_transactions_count)
        binding.rowTransactionCount.rowValue.text = data.transactionCount.toString()
    }

    private fun formatPrice(value: Long): String {
        return "${TextUtils.numberFormat(value)} ${getString(R.string.toman)}"
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