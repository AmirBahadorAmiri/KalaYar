package com.amirbahadoramiri.kalayar.presentation.ui.fragments.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.navigation.findNavController
import com.amirbahadoramiri.kalayar.R
import com.amirbahadoramiri.kalayar.databinding.MainFragmentBinding
import com.amirbahadoramiri.kalayar.presentation.base.BaseFragment
import com.github.amirbahadoramiri.telegramdialog.TelegramConfirmDialog
import com.github.amirbahadoramiri.telegramdialog.direction.DialogDirection
import com.github.amirbahadoramiri.telegramdialog.listeners.OnConfirmListener
import com.ismaeldivita.chipnavigation.ChipNavigationBar

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
        binding.chipNavigationBar.setOnItemSelectedListener(object :
            ChipNavigationBar.OnItemSelectedListener {
            override fun onItemSelected(id: Int) {
                val navController = binding.fragmentMainFragmentContainer.findNavController()
                when (id) {
                    R.id.home -> {
                        if (navController.currentDestination?.id != R.id.homeFragment) {
                            navController.navigate(R.id.homeFragment)
                        }
                    }
                    R.id.contacts -> {
                        if (navController.currentDestination?.id != R.id.contactsFragment) {
                            navController.navigate(R.id.contactsFragment)
                        }
                    }
                    R.id.profile -> {
                        if (navController.currentDestination?.id != R.id.profileFragment) {
                            navController.navigate(R.id.profileFragment)
                        }
                    }
                }
            }
        })
    }

    private fun customOnBackPressed() {
        val backPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                val exitDialog = TelegramConfirmDialog(requireContext(), DialogDirection.RTL)
                    .setTitle(getString(R.string.exit_app))
                    .setMessage(getString(R.string.exit_app_message))
                    .setCancelable(true)
                    .setCardBackgroundColor(requireContext().getColor(R.color.kalayar_page_background_color))
                    .setPositiveButtonText(getString(R.string.yes))
                    .setPositiveButtonTextColor(requireContext().getColor(R.color.kalayar_dialog_red_color))
                    .setPositiveButtonRippleColor(requireContext().getColor(R.color.kalayar_dialog_red_color_tint))
                    .setPositiveButtonBackgroundColor(requireContext().getColor(R.color.kalayar_page_background_color))
                    .setNegativeButtonText(getString(R.string.cancel))
                    .setNegativeButtonTextColor(requireContext().getColor(R.color.kalayar_dialog_blue_color))
                    .setNegativeButtonRippleColor(requireContext().getColor(R.color.kalayar_dialog_blue_color_tint))
                    .setNegativeButtonBackgroundColor(requireContext().getColor(R.color.kalayar_page_background_color))

                exitDialog.setOnClickListener(object : OnConfirmListener {
                    override fun onNegativeButtonClicked() {
                        exitDialog.dismiss()
                    }

                    override fun onPositiveButtonClicked() {
                        requireActivity().finish()
                    }
                })
                exitDialog.show()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, backPressedCallback)
    }

}