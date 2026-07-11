package com.amirbahadoramiri.kalayar.presentation.ui.fragments.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import com.amirbahadoramiri.kalayar.databinding.ProfileFragmentBinding
import com.amirbahadoramiri.kalayar.presentation.base.BaseFragment
import com.amirbahadoramiri.kalayar.tools.darkmode.DarkMode
import com.amirbahadoramiri.kalayar.tools.shared_helper.SharedHelper

class ProfileFragment: BaseFragment() {

    lateinit var binding: ProfileFragmentBinding
    lateinit var profileViewModel: ProfileViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = ProfileFragmentBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setup()
    }

    private fun setup() {
        profileViewModel = ViewModelProvider(this).get(ProfileViewModel::class)
        profileViewModel.storeLiveData.observe(viewLifecycleOwner) {
            binding.storeName.text = it.store_name
            binding.storePhone.text = it.store_phonenumber
            binding.storeAddress.text = it.store_address
            binding.storeWebsite.text = it.store_website
        }
        profileViewModel.getStore()

        binding.themeSwitchButton.isChecked = DarkMode.checkDarkMode(requireContext())
        binding.themeSwitchButton.setOnCheckedChangeListener { buttonView, isChecked ->
            run {
                if (isChecked) {
                    DarkMode.enableDarkMode(requireContext())
                } else {
                    DarkMode.disableDarkMode(requireContext())
                }
            }
        }

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