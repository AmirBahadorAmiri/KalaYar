package com.amirbahadoramiri.kalayar.presentation.ui.fragments.home

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.amirbahadoramiri.kalayar.R
import com.amirbahadoramiri.kalayar.databinding.HomeFragmentBinding
import com.amirbahadoramiri.kalayar.presentation.base.BaseFragment
import com.amirbahadoramiri.kalayar.presentation.ui.fragments.main.MainFragmentDirections

class HomeFragment : BaseFragment() {

    lateinit var binding: HomeFragmentBinding
    lateinit var homeViewModel: HomeViewModel
    private val priceAdapter = PriceAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = HomeFragmentBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setup()
    }

    private fun setup() {
        homeViewModel = ViewModelProvider(this)[HomeViewModel::class.java]
        setupAdapter()
        setupPrices()
        customOnBackPressed()
        
        homeViewModel.fetchPrices()
    }

    private fun setupPrices() {
        binding.pricesRecyclerview.apply {
            layoutManager = GridLayoutManager(requireContext(), 2, GridLayoutManager.VERTICAL, false)
            adapter = priceAdapter
        }

        homeViewModel.pricesLiveData.observe(viewLifecycleOwner) { prices ->
            priceAdapter.setData(prices)
        }

        binding.tasnimLogoCard.setOnClickListener {
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.tasnimnews.ir/fa/currency"))
            startActivity(browserIntent)
        }

        homeViewModel.errorLiveData.observe(viewLifecycleOwner) { error ->
            // Optionally show error
        }
    }

    private fun setupAdapter() {
        val dataList = mutableListOf<PageModel>()
        dataList.add(PageModel(getString(R.string.products), R.drawable.kalayar_products) {
            val navController =
                requireActivity().findNavController(R.id.activityMainFragmentContainer)
            if (navController.currentDestination?.id == R.id.mainFragment) {
                val action = MainFragmentDirections.actionMainFragmentToProductFragment()
                navController.navigate(action)
            }
        })
        dataList.add(PageModel(getString(R.string.transactions), R.drawable.kalayar_transactions) {
            val navController =
                requireActivity().findNavController(R.id.activityMainFragmentContainer)
            if (navController.currentDestination?.id == R.id.mainFragment) {
                val action = MainFragmentDirections.actionMainFragmentToTransactionsFragment()
                navController.navigate(action)
            }
        })
        dataList.add(PageModel(getString(R.string.inventory), R.drawable.kalayar_inventory) {
            val navController =
                requireActivity().findNavController(R.id.activityMainFragmentContainer)
            if (navController.currentDestination?.id == R.id.mainFragment) {
                val action = MainFragmentDirections.actionMainFragmentToInventoryFragment()
                navController.navigate(action)
            }
        })
        dataList.add(PageModel(getString(R.string.money), R.drawable.kalayar_money) {
            val navController =
                requireActivity().findNavController(R.id.activityMainFragmentContainer)
            if (navController.currentDestination?.id == R.id.mainFragment) {
                val action = MainFragmentDirections.actionMainFragmentToReportFragment()
                navController.navigate(action)
            }
        })
        dataList.add(
            PageModel(
                getString(R.string.import_export_database),
                R.drawable.kalayar_database
            ) {
                val navController =
                    requireActivity().findNavController(R.id.activityMainFragmentContainer)
                if (navController.currentDestination?.id == R.id.mainFragment) {
                    val action = MainFragmentDirections.actionMainFragmentToDatabaseFragment()
                    navController.navigate(action)
                }
            })

        val adapter1 = HomeFragmentPageRecyclerViewAdapter(dataList)
        binding.recyclerview1.apply {
            layoutManager = GridLayoutManager(requireContext(), 3, GridLayoutManager.VERTICAL, false)
            adapter = adapter1
        }
    }

    private fun customOnBackPressed() {
        val backPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this, backPressedCallback)
    }

}