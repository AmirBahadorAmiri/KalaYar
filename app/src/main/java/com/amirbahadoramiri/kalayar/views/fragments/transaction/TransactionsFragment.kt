package com.amirbahadoramiri.kalayar.views.fragments.transaction

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.recyclerview.widget.LinearLayoutManager
import com.amirbahadoramiri.kalayar.databinding.TransactionFragmentBinding
import com.amirbahadoramiri.kalayar.views.fragments.base.BaseFragment

class TransactionsFragment : BaseFragment() {

    companion object {

        private var instance: TransactionsFragment? = null

        fun createInstance(): TransactionsFragment {
            return TransactionsFragment()
        }

        fun getInstance(): TransactionsFragment {

            if (instance == null) {
                instance = createInstance()
            }
            return instance!!
        }

    }

    lateinit var binding: TransactionFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = TransactionFragmentBinding.inflate(inflater)
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

        binding.backBtn.setOnClickListener {
            popBackStack()
        }

        val layoutManager = LinearLayoutManager(requireContext())
        binding.transactionRecyclerview.layoutManager = layoutManager
        val adapter = TransactionsAdapter()
        binding.transactionRecyclerview.adapter = adapter
        adapter.reloadDatabase(requireContext())

        binding.addTransaction.setOnClickListener {
            replaceFragment(AddTransactionFragment.createInstance())
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