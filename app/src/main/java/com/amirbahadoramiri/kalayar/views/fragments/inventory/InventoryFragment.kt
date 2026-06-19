package com.amirbahadoramiri.kalayar.views.fragments.inventory

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import com.amirbahadoramiri.kalayar.databinding.InventoryFragmentBinding
import com.amirbahadoramiri.kalayar.views.fragments.base.BaseFragment

class InventoryFragment : BaseFragment() {

    companion object {

        private var instance: InventoryFragment? = null

        fun createInstance(): InventoryFragment {
            return InventoryFragment()
        }

        fun getInstance(): InventoryFragment {

            if (instance == null) {
                instance = createInstance()
            }
            return instance!!
        }

    }

    lateinit var binding: InventoryFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = InventoryFragmentBinding.inflate(inflater)
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