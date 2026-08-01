package com.amirbahadoramiri.kalayar.presentation.ui.fragments.transaction

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.amirbahadoramiri.kalayar.R
import com.amirbahadoramiri.kalayar.databinding.TransactionFragmentBinding
import com.amirbahadoramiri.kalayar.databinding.TransactionShowSheetBinding
import com.amirbahadoramiri.kalayar.domain.models.Store
import com.amirbahadoramiri.kalayar.domain.models.Transaction
import com.amirbahadoramiri.kalayar.domain.models.TransactionItem
import com.amirbahadoramiri.kalayar.presentation.base.BaseFragment
import com.amirbahadoramiri.kalayar.tools.PDFUtils
import com.amirbahadoramiri.kalayar.tools.text_utils.TextUtils
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.milliseconds

class TransactionsFragment : BaseFragment(), TransactionEventListener {

    lateinit var binding: TransactionFragmentBinding
    lateinit var transactionViewModel: TransactionViewModel
    val transactionsAdapter = TransactionsAdapter(this)

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

        if (transactionViewModel.getAllTransactionLiveData.value == null) {
            transactionViewModel.getAllTransactions()
        }

        findNavController().currentBackStackEntry?.savedStateHandle?.getLiveData<Transaction>("new_transaction")
            ?.observe(viewLifecycleOwner) { transaction ->
                transaction?.let {
                    onAddTransaction(it, 0)
                    findNavController().currentBackStackEntry?.savedStateHandle?.remove<Transaction>("new_transaction")
                }
            }

        binding.transactionSearch.addTextChangedListener(object : TextWatcher {
            var job: Job = Job()
            private var lastText = ""

            override fun afterTextChanged(s: Editable?) {
                val text = s.toString()
                if (text == lastText) return
                lastText = text

                if (job.isActive) {
                    job.cancel()
                }
                job = lifecycleScope.launch {
                    delay(500.milliseconds)
                    if (text.isEmpty()) {
                        transactionViewModel.getAllTransactionLiveData.value?.let {
                            transactionsAdapter.reloadTransactions(it)
                        }
                    } else {
                        transactionViewModel.getAllTransactionLiveData.value?.filter {
                            it.transaction_title.contains(text) || it.transaction_description.contains(
                                text
                            )
                        }?.let {
                            transactionsAdapter.reloadTransactions(it)
                        }
                    }
                }
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
        })

        binding.addTransaction.setOnClickListener {
            if (findNavController().currentDestination?.id == R.id.transactionsFragment) {
                val action =
                    TransactionsFragmentDirections.actionTransactionsFragmentToAddTransactionFragment()
                findNavController().navigate(action)
            }
        }
    }

    private fun customOnBackPressed() {
        val backPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                popBackStack()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, backPressedCallback)
    }

    override fun onShowTransaction(transaction: Transaction, position: Int) {
        val bottomSheetDialog = BottomSheetDialog(requireContext())
        val showBinding = TransactionShowSheetBinding.inflate(layoutInflater)
        bottomSheetDialog.setContentView(showBinding.root)
        showBinding.transaction = transaction

        val itemAdapter = TransactionItemAdapter()
        showBinding.itemsRecyclerview.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = itemAdapter
        }

        var currentItems = listOf<TransactionItem>()
        var currentStore: Store? = null
        var currentTotalPrice = 0L

        val itemsObserver = Observer<List<TransactionItem>> { items ->
            currentItems = items
            itemAdapter.setData(items)
            var totalPrice = 0L
            items.forEach { totalPrice += (it.product_price * it.change_amount) }
            currentTotalPrice = totalPrice
            showBinding.totalPriceValue.text =
                "${TextUtils.numberFormat(totalPrice)} ${getString(R.string.toman)}"
        }

        val storeObserver = Observer<Store?> { store ->
            currentStore = store
            showBinding.store = store
        }

        transactionViewModel.getTransactionItemsLiveData.observe(viewLifecycleOwner, itemsObserver)
        transactionViewModel.getStoreLiveData.observe(viewLifecycleOwner, storeObserver)

        bottomSheetDialog.setOnDismissListener {
            transactionViewModel.getTransactionItemsLiveData.removeObserver(itemsObserver)
            transactionViewModel.getStoreLiveData.removeObserver(storeObserver)
        }

        transaction.transaction_id?.let {
            transactionViewModel.getTransactionItems(it)
        }
        transactionViewModel.getStore()

        showBinding.printBtn.setOnClickListener {
            PDFUtils.generateAndShareTransactionPDF(
                requireContext(),
                transaction,
                currentItems,
                currentTotalPrice,
                currentStore
            )
        }

        bottomSheetDialog.show()
    }

    override fun onRemoveTransaction(transaction: Transaction, position: Int) {
        lifecycleScope.launch {
            delay(500.milliseconds)
            transactionsAdapter.removeTransaction(position)
            transactionViewModel.removeTransaction(transaction)
            transactionViewModel.removeTransactionItems(transaction)
        }
    }

    override fun onPrintTransaction(transaction: Transaction, position: Int) {
        transaction.transaction_id?.let { id ->
            transactionViewModel.getTransactionItems(id)
            transactionViewModel.getStore()

            val itemsObserver = object : Observer<List<TransactionItem>> {
                override fun onChanged(transactionItems: List<TransactionItem>) {
                    val storeObserver = object : Observer<Store?> {
                        override fun onChanged(store: Store?) {
                            var totalPrice = 0L
                            transactionItems.forEach { totalPrice += (it.product_price * it.change_amount) }
                            PDFUtils.generateAndShareTransactionPDF(
                                requireContext(),
                                transaction,
                                transactionItems,
                                totalPrice,
                                store
                            )
                            transactionViewModel.getStoreLiveData.removeObserver(this)
                        }
                    }
                    transactionViewModel.getStoreLiveData.observe(viewLifecycleOwner, storeObserver)
                    transactionViewModel.getTransactionItemsLiveData.removeObserver(this)
                }
            }
            transactionViewModel.getTransactionItemsLiveData.observe(viewLifecycleOwner, itemsObserver)
        }
    }

    override fun onUpdateTransaction(transaction: Transaction, position: Int) {}
    override fun onAddTransaction(transaction: Transaction, position: Int) {
        lifecycleScope.launch {
            delay(500.milliseconds)
            transactionsAdapter.addTransaction(transaction, position)
            binding.transactionRecyclerview.scrollToPosition(position)
            transactionViewModel.getAllTransactionLiveData.value?.add(position, transaction)
        }
    }

}
