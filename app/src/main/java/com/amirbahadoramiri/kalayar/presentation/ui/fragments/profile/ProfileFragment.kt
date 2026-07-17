package com.amirbahadoramiri.kalayar.presentation.ui.fragments.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.CompoundButton
import androidx.activity.OnBackPressedCallback
import androidx.lifecycle.ViewModelProvider
import com.amirbahadoramiri.kalayar.R
import com.amirbahadoramiri.kalayar.databinding.ProfileFragmentBinding
import com.amirbahadoramiri.kalayar.databinding.ProfileStoreBottomSheetBinding
import com.amirbahadoramiri.kalayar.domain.models.Store
import com.amirbahadoramiri.kalayar.presentation.base.BaseFragment
import com.amirbahadoramiri.kalayar.tools.darkmode.DarkMode
import com.amirbahadoramiri.kalayar.tools.packager.Packager
import com.github.amirbahadoramiri.telegramdialog.TelegramConfirmDialog
import com.github.amirbahadoramiri.telegramdialog.TelegramInputConfirmDialog
import com.github.amirbahadoramiri.telegramdialog.direction.DialogDirection
import com.github.amirbahadoramiri.telegramdialog.listeners.OnConfirmListener
import com.github.amirbahadoramiri.telegramdialog.listeners.OnInputConfirmListener
import com.google.android.material.bottomsheet.BottomSheetDialog

class ProfileFragment : BaseFragment() {

    lateinit var binding: ProfileFragmentBinding
    lateinit var profileViewModel: ProfileViewModel
    lateinit var store: Store

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
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
            store = it
            binding.store = store
        }
        profileViewModel.userLiveData.observe(viewLifecycleOwner) {
            binding.passwordSwitchButton.isChecked = it.user_password.isNotEmpty()
        }
        profileViewModel.getStore()
        profileViewModel.checkPassword()

        binding.storeView.setOnClickListener {
            val storeViewSheet = BottomSheetDialog(requireContext())
            val storeViewSheetBinding = ProfileStoreBottomSheetBinding.inflate(layoutInflater)
            storeViewSheet.setContentView(storeViewSheetBinding.root)

            storeViewSheetBinding.store = store
            storeViewSheetBinding.confirmButton.setOnClickListener {
                store.store_name = storeViewSheetBinding.storeName.text.toString()
                store.store_address = storeViewSheetBinding.storeAddress.text.toString()
                store.store_website = storeViewSheetBinding.storeWebsite.text.toString()
                store.store_phonenumber = storeViewSheetBinding.storePhone.text.toString()
                profileViewModel.updateStore(store)
                storeViewSheet.dismiss()
            }
            storeViewSheet.show()
        }

        binding.themeSwitchButton.isChecked = DarkMode.checkDarkMode(requireContext())
        binding.themeSwitchButton.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                DarkMode.enableDarkMode(requireContext())
            } else {
                DarkMode.disableDarkMode(requireContext())
            }
        }
        binding.themeButton.setOnClickListener {
            binding.themeSwitchButton.isChecked = !binding.themeSwitchButton.isChecked
        }

        binding.passwordSwitchButton.setOnCheckedChangeListener(object : CompoundButton.OnCheckedChangeListener {
            override fun onCheckedChanged(buttonView: CompoundButton, isChecked: Boolean) {
                if (!buttonView.isPressed) return

                val user = profileViewModel.userLiveData.value ?: return

                if (isChecked) {

                    val questionDialog = TelegramConfirmDialog(requireContext(), DialogDirection.RTL)
                        .setTitle(getString(R.string.warning))
                        .setMessage(getString(R.string.password_warning_data_loss))
                        .setCardBackgroundColor(requireContext().getColor(R.color.kalayar_page_background_color))
                        .setPositiveButtonText(getString(R.string.yes))
                        .setPositiveButtonTextColor(requireContext().getColor(R.color.kalayar_dialog_blue_color))
                        .setPositiveButtonRippleColor(requireContext().getColor(R.color.kalayar_dialog_blue_color_tint))
                        .setPositiveButtonBackgroundColor(requireContext().getColor(R.color.kalayar_page_background_color))
                        .setNegativeButtonText(getString(R.string.cancel))
                        .setNegativeButtonTextColor(requireContext().getColor(R.color.kalayar_dialog_red_color))
                        .setNegativeButtonRippleColor(requireContext().getColor(R.color.kalayar_dialog_red_color_tint))
                        .setNegativeButtonBackgroundColor(requireContext().getColor(R.color.kalayar_page_background_color))

                    questionDialog.setOnClickListener(object : OnConfirmListener {
                        override fun onCanceled() {
                            binding.passwordSwitchButton.isChecked = false
                        }
                        override fun onNegativeButtonClicked() {
                            binding.passwordSwitchButton.isChecked = false
                            questionDialog.dismiss()
                        }
                        override fun onPositiveButtonClicked() {
                            val passwordDialog = TelegramInputConfirmDialog(requireContext(), DialogDirection.RTL)
                                .setTitle(getString(R.string.password))
                                .setMessage(getString(R.string.enter_password))
                                .setCardBackgroundColor(requireContext().getColor(R.color.kalayar_page_background_color))
                                .setEditTextDrawable(
                                    R.drawable.kalayar_password_hint_icon,
                                    requireContext().getColor(R.color.kalayar_dialog_blue_color)
                                )
                                .setEditTextHint(getString(R.string.enter_text))
                                .setPositiveButtonText(getString(R.string.choose_password))
                                .setPositiveButtonTextColor(requireContext().getColor(R.color.kalayar_dialog_blue_color))
                                .setPositiveButtonRippleColor(requireContext().getColor(R.color.kalayar_dialog_blue_color_tint))
                                .setPositiveButtonBackgroundColor(requireContext().getColor(R.color.kalayar_page_background_color))
                                .setNegativeButtonText(getString(R.string.cancel))
                                .setNegativeButtonTextColor(requireContext().getColor(R.color.kalayar_dialog_red_color))
                                .setNegativeButtonRippleColor(requireContext().getColor(R.color.kalayar_dialog_red_color_tint))
                                .setNegativeButtonBackgroundColor(requireContext().getColor(R.color.kalayar_page_background_color))

                            passwordDialog.setOnClickListener(object : OnInputConfirmListener {
                                override fun onCanceled() {
                                    binding.passwordSwitchButton.isChecked = false
                                }
                                override fun onNegativeButtonClicked(p0: String?) {
                                    binding.passwordSwitchButton.isChecked = false
                                    passwordDialog.dismiss()
                                }
                                override fun onPositiveButtonClicked(p0: String?) {
                                    if (p0.toString().length > 3) {
                                        user.user_password = p0.toString()
                                        profileViewModel.updateUser(user)
                                        passwordDialog.dismiss()
                                    } else {
                                        val shake_animation = AnimationUtils.loadAnimation(requireContext(), R.anim.shake_animation)
                                        passwordDialog.startEditTextAnimation(shake_animation)
                                        toast(getString(R.string.password_too_short))
                                    }
                                }
                            })

                            passwordDialog.show()
                            questionDialog.dismiss()
                        }
                    })

                    questionDialog.show()

                } else {
                    user.user_password = ""
                    profileViewModel.updateUser(user)
                }
            }
        })
        binding.passwordButton.setOnClickListener {
            binding.passwordSwitchButton.isChecked = !binding.passwordSwitchButton.isChecked
        }

        binding.rateButton.setOnClickListener {
            Packager.openInMarket(requireContext())
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