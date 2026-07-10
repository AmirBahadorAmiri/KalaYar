package com.amirbahadoramiri.kalayar.presentation.ui.fragments.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.navigation.Navigation
import com.amirbahadoramiri.kalayar.R
import com.amirbahadoramiri.kalayar.databinding.MainFragmentBinding
import com.amirbahadoramiri.kalayar.presentation.base.BaseFragment
import com.ismaeldivita.chipnavigation.ChipNavigationBar
import androidx.navigation.findNavController

class MainFragment: BaseFragment() {

    lateinit var binding: MainFragmentBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = MainFragmentBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setup()
    }

    private fun setup() {
        setupBottomNavigation()
        customOnBackPressed()
    }

    private fun setupBottomNavigation() {
        binding.chipNavigationBar.setItemSelected(R.id.home)
        binding.chipNavigationBar.setOnItemSelectedListener(object: ChipNavigationBar.OnItemSelectedListener {
            override fun onItemSelected(id: Int) {
                when(id) {
                    R.id.home->{
                        binding.fragmentMainFragmentContainer.findNavController().navigate(R.id.homeFragment)
                    }
                    R.id.contacts->{
                        binding.fragmentMainFragmentContainer.findNavController().navigate(R.id.contactsFragment)
                    }
                    R.id.profile->{
                        binding.fragmentMainFragmentContainer.findNavController().navigate(R.id.profileFragment)
                    }
                }
            }
        })
    }

    private fun customOnBackPressed() {
        val backPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this, backPressedCallback)
    }

}