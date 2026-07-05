package com.amirbahadoramiri.kalayar.presentation.ui.fragments.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.amirbahadoramiri.kalayar.R
import com.amirbahadoramiri.kalayar.databinding.MainFragmentBinding
import com.amirbahadoramiri.kalayar.presentation.base.BaseFragment

class MainFragment : BaseFragment() {

    lateinit var binding: MainFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = MainFragmentBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setup()
    }

    private fun setup() {

        val dataList = mutableListOf<PageModel>()

        dataList.add(PageModel(getString(R.string.products), R.drawable.kalayar_products) {
            val action = MainFragmentDirections.actionMainFragmentToProductFragment()
            findNavController().navigate(action)
            })

        dataList.add(PageModel(getString(R.string.transactions), R.drawable.kalayar_transactions) {
            val action = MainFragmentDirections.actionMainFragmentToTransactionsFragment()
            findNavController().navigate(action)
            })

        dataList.add(PageModel(getString(R.string.inventory), R.drawable.kalayar_inventory) {
            val action = MainFragmentDirections.actionMainFragmentToInventoryFragment()
            findNavController().navigate(action)
            })

        dataList.add(PageModel(getString(R.string.money), R.drawable.kalayar_money) {
            val action = MainFragmentDirections.actionMainFragmentToMoneyFragment()
            findNavController().navigate(action)
            })

        dataList.add(PageModel(getString(R.string.import_export_database), R.drawable.kalayar_database) {
            val action = MainFragmentDirections.actionMainFragmentToDatabaseFragment()
            findNavController().navigate(action)
            })

        val adapter1 = MainFragmentPageRecyclerViewAdapter(dataList)
        binding.recyclerview1.layoutManager =
            GridLayoutManager(requireContext(), 3, GridLayoutManager.VERTICAL, false)
        binding.recyclerview1.adapter = adapter1

        customOnBackPressed()

    }

    private fun customOnBackPressed() {
        val backPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this, backPressedCallback)
    }

}