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
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.amirbahadoramiri.kalayar.databinding.TransactionFragmentBinding
import com.amirbahadoramiri.kalayar.presentation.base.BaseFragment

class TransactionsFragment : BaseFragment() {

    private val TRANSACTION_LIMIT = 100

    lateinit var binding: TransactionFragmentBinding
    lateinit var transactionViewModel: TransactionViewModel
    val transactionsAdapter = TransactionsAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = TransactionFragmentBinding.inflate(inflater)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setup()
    }

    private fun setup() {

        transactionViewModel = ViewModelProvider(this)[TransactionViewModel::class]
        transactionViewModel.getAllTransactionLiveData.observe(viewLifecycleOwner) {
            transactionsAdapter.reloadTransactions(it)
        }
        customOnBackPressed()

        binding.backBtn.setOnClickListener {
            popBackStack()
        }

        binding.transactionRecyclerview.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = transactionsAdapter
            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    when {
                        dy > 0 && binding.addTransaction.isVisible -> binding.addTransaction.hide()
                        dy < 0 && !binding.addTransaction.isVisible -> binding.addTransaction.show()
                    }
                }
            })
        }
        transactionViewModel.getAllTransactions(TRANSACTION_LIMIT)

        binding.transactionSearch.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                val text = s.toString()
                if ( text.isEmpty() ) {
                    transactionViewModel.getAllTransactionLiveData.value?.let {
                        transactionsAdapter.reloadTransactions(it)
                    }
                } else {
                    transactionViewModel.getAllTransactionLiveData.value?.filter {
                        if ( it.transaction_title.contains(text) || it.transaction_description.contains(text) ) true else false
                    }?.let {
                        transactionsAdapter.reloadTransactions(it)
                    }
                }
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
        })

        binding.addTransaction.setOnClickListener {
            val action = TransactionsFragmentDirections.actionTransactionsFragmentToAddTransactionFragment()
            findNavController().navigate(action)
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