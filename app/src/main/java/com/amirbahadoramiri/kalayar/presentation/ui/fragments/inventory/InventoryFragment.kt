package com.amirbahadoramiri.kalayar.presentation.ui.fragments.inventory

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.amirbahadoramiri.kalayar.databinding.InventoryFragmentBinding
import com.amirbahadoramiri.kalayar.presentation.base.BaseFragment
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.milliseconds

class InventoryFragment : BaseFragment() {

    lateinit var binding: InventoryFragmentBinding
    lateinit var inventoryFragmentViewModel: InventoryFragmentViewModel
    private val productAdapter = InventoryAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = InventoryFragmentBinding.inflate(inflater)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setup()
    }

    private fun setup() {

        inventoryFragmentViewModel = ViewModelProvider(this)[InventoryFragmentViewModel::class]
        inventoryFragmentViewModel.getAllProductLiveData.observe(viewLifecycleOwner) {
            productAdapter.reloadProduct(it)
        }

        customOnBackPressed()

        binding.inventoryRecyclerview.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = productAdapter
        }
        inventoryFragmentViewModel.getAllProduct()

        binding.backBtn.setOnClickListener {
            popBackStack()
        }

        binding.inventorySearch.addTextChangedListener(object : TextWatcher {
            var job: Job = Job()
            override fun afterTextChanged(s: Editable?) {
                if (job.isActive) {
                    job.cancel()
                }
                job = lifecycleScope.launch {
                    delay(500.milliseconds)
                    val product_name = s.toString()
                    if (product_name.isEmpty()) {
                        inventoryFragmentViewModel.getAllProductLiveData.value?.let {
                            productAdapter.reloadProduct(it)
                        }
                    } else {
                        inventoryFragmentViewModel.getAllProductLiveData.value?.filter {
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