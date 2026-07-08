package com.amirbahadoramiri.kalayar.presentation.ui.fragments.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.amirbahadoramiri.kalayar.R
import com.amirbahadoramiri.kalayar.databinding.HomeFragmentBinding
import com.amirbahadoramiri.kalayar.presentation.base.BaseFragment
import com.amirbahadoramiri.kalayar.presentation.ui.fragments.main.MainFragmentDirections

class HomeFragment : BaseFragment() {

    lateinit var binding: HomeFragmentBinding

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
        setupAdapter()
        customOnBackPressed()
    }

    private fun setupAdapter() {
        val dataList = mutableListOf<PageModel>()
        dataList.add(PageModel(getString(R.string.products), R.drawable.kalayar_products) {
            val action = MainFragmentDirections.actionMainFragmentToProductFragment()
            requireActivity().findNavController(R.id.fragmentContainer).navigate(action)
        })
        dataList.add(PageModel(getString(R.string.transactions), R.drawable.kalayar_transactions) {
            val action = MainFragmentDirections.actionMainFragmentToTransactionsFragment()
            requireActivity().findNavController(R.id.fragmentContainer).navigate(action)
        })
        dataList.add(PageModel(getString(R.string.inventory), R.drawable.kalayar_inventory) {
            val action = MainFragmentDirections.actionMainFragmentToInventoryFragment()
            requireActivity().findNavController(R.id.fragmentContainer).navigate(action)
        })
        dataList.add(PageModel(getString(R.string.money), R.drawable.kalayar_money) {
            val action = MainFragmentDirections.actionMainFragmentToMoneyFragment()
            requireActivity().findNavController(R.id.fragmentContainer).navigate(action)
        })
        dataList.add(PageModel(getString(R.string.import_export_database), R.drawable.kalayar_database) {
            val action = MainFragmentDirections.actionMainFragmentToDatabaseFragment()
            requireActivity().findNavController(R.id.fragmentContainer).navigate(action)
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