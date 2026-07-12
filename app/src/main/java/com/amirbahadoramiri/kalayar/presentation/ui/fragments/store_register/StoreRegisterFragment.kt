package com.amirbahadoramiri.kalayar.presentation.ui.fragments.store_register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.amirbahadoramiri.kalayar.R
import com.amirbahadoramiri.kalayar.databinding.StoreRegisterFragmentBinding
import com.amirbahadoramiri.kalayar.domain.models.Store
import com.amirbahadoramiri.kalayar.presentation.base.BaseFragment

class StoreRegisterFragment : BaseFragment() {

    lateinit var binding: StoreRegisterFragmentBinding
    private lateinit var storeViewModel: StoreViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = StoreRegisterFragmentBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setup()
    }

    private fun setup() {

        storeViewModel = ViewModelProvider(this).get(StoreViewModel::class)
        storeViewModel.storeIsSaved.observe(viewLifecycleOwner) {
            if (it) {
                val navController = findNavController()
                if (navController.currentDestination?.id == R.id.storeRegisterFragment) {
                    val action =
                        StoreRegisterFragmentDirections.actionStoreRegisterFragmentToMainFragment()
                    navController.navigate(action)
                }
            }
        }

        onBackPressed()
        binding.confirmButton.setOnClickListener {

            val name = binding.storeName.text.toString();
            val address = binding.storeAddress.text.toString();
            val website = binding.storeWebsite.text.toString();
            val phone = binding.storePhone.text.toString();

            if (name.isEmpty()) {
                binding.storeName.setError(getString(R.string.is_necessary))
                toast(getString(R.string.fill_necessary_field))
            } else {
                val store = Store(name, address, phone, website)
                storeViewModel.saveUser(store)
            }
        }
    }

    private fun onBackPressed() {
        val backPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                requireActivity().finish()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(requireActivity(), backPressedCallback)
    }

}