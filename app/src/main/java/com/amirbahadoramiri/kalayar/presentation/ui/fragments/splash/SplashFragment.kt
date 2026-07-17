package com.amirbahadoramiri.kalayar.presentation.ui.fragments.splash

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.activity.OnBackPressedCallback
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.amirbahadoramiri.kalayar.R
import com.amirbahadoramiri.kalayar.data.db.PrivateDatabase
import com.amirbahadoramiri.kalayar.data.db.PublicDatabase
import com.amirbahadoramiri.kalayar.databinding.SplashFragmentBinding
import com.amirbahadoramiri.kalayar.domain.models.User
import com.amirbahadoramiri.kalayar.presentation.base.BaseFragment
import com.amirbahadoramiri.kalayar.tools.devices.Devices
import com.amirbahadoramiri.kalayar.tools.packager.Packager
import com.github.amirbahadoramiri.telegramdialog.TelegramConfirmDialog
import com.github.amirbahadoramiri.telegramdialog.TelegramInputConfirmDialog
import com.github.amirbahadoramiri.telegramdialog.direction.DialogDirection
import com.github.amirbahadoramiri.telegramdialog.listeners.OnConfirmListener
import com.github.amirbahadoramiri.telegramdialog.listeners.OnInputConfirmListener
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.time.Duration.Companion.milliseconds

class SplashFragment : BaseFragment() {

    lateinit var binding: SplashFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = SplashFragmentBinding.inflate(inflater)
        R.layout.splash_fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setup()
    }

    private fun setup() {

        onBackPressed()

        val version = Packager.negareshApplication(requireContext())
        binding.versionName.text = version

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.RESUMED) {
                delay(2000.milliseconds)
                val user =
                    PrivateDatabase.getPrivateDatabase(requireContext()).getPrivateDAO().getUser()
                if (user == null) {
                    val newUser = User(Devices.getUniqueId(requireContext()), false)
                    PrivateDatabase.getPrivateDatabase(requireContext()).getPrivateDAO()
                        .addUser(newUser)
                }
                val store =
                    PublicDatabase.getPublicDatabase(requireContext()).getPublicDAO().getStore()
                val navController = findNavController()
                if (navController.currentDestination?.id == R.id.splashFragment) {
                    if (store == null) {
                        val action =
                            SplashFragmentDirections.actionSplashFragmentToStoreRegisterFragment()
                        navController.navigate(action)
                    } else {
                        if (user?.user_password?.isEmpty() ?: false) {
                            val action =
                                SplashFragmentDirections.actionSplashFragmentToMainFragment()
                            navController.navigate(action)
                        } else {
                            val passwordDialog =
                                TelegramInputConfirmDialog(requireContext(), DialogDirection.RTL)
                                    .setTitle(getString(R.string.password))
                                    .setMessage(getString(R.string.enter_password))
                                    .setCancelable(false)
                                    .setCardBackgroundColor(requireContext().getColor(R.color.kalayar_page_background_color))
                                    .setEditTextDrawable(
                                        R.drawable.kalayar_password_hint_icon,
                                        requireContext().getColor(R.color.kalayar_dialog_blue_color)
                                    )
                                    .setEditTextHint(getString(R.string.enter_text))
                                    .setPositiveButtonText(getString(R.string.login))
                                    .setPositiveButtonTextColor(requireContext().getColor(R.color.kalayar_dialog_blue_color))
                                    .setPositiveButtonRippleColor(requireContext().getColor(R.color.kalayar_dialog_blue_color_tint))
                                    .setPositiveButtonBackgroundColor(requireContext().getColor(R.color.kalayar_page_background_color))
                                    .setNegativeButtonText(getString(R.string.forget_password))
                                    .setNegativeButtonTextColor(requireContext().getColor(R.color.kalayar_dialog_red_color))
                                    .setNegativeButtonRippleColor(requireContext().getColor(R.color.kalayar_dialog_red_color_tint))
                                    .setNegativeButtonBackgroundColor(requireContext().getColor(R.color.kalayar_page_background_color))

                            passwordDialog.setOnClickListener(object : OnInputConfirmListener {
                                override fun onNegativeButtonClicked(p0: String?) {

                                    val questionDialog = TelegramConfirmDialog(requireContext(), DialogDirection.RTL)
                                        .setTitle(getString(R.string.warning))
                                        .setCancelable(false)
                                        .setMessage(getString(R.string.confirm_irreversible_action))
                                        .setCardBackgroundColor(requireContext().getColor(R.color.kalayar_page_background_color))
                                        .setPositiveButtonText(getString(R.string.yes_delete_it))
                                        .setPositiveButtonTextColor(requireContext().getColor(R.color.kalayar_dialog_red_color))
                                        .setPositiveButtonRippleColor(requireContext().getColor(R.color.kalayar_dialog_red_color_tint))
                                        .setPositiveButtonBackgroundColor(requireContext().getColor(R.color.kalayar_page_background_color))
                                        .setNegativeButtonText(getString(R.string.cancel))
                                        .setNegativeButtonTextColor(requireContext().getColor(R.color.kalayar_dialog_blue_color))
                                        .setNegativeButtonRippleColor(requireContext().getColor(R.color.kalayar_dialog_blue_color_tint))
                                        .setNegativeButtonBackgroundColor(requireContext().getColor(R.color.kalayar_page_background_color))

                                    questionDialog.setOnClickListener(object : OnConfirmListener {
                                        override fun onNegativeButtonClicked() {
                                            questionDialog.dismiss()
                                            passwordDialog.show()
                                        }
                                        override fun onPositiveButtonClicked() {

                                            val textQuestionDialog = TelegramInputConfirmDialog(requireContext(), DialogDirection.RTL)
                                                .setTitle(getString(R.string.warning))
                                                .setCancelable(false)
                                                .setMessage(getString(R.string.type_agree_to_continue))
                                                .setCardBackgroundColor(requireContext().getColor(R.color.kalayar_page_background_color))
                                                .setEditTextHint(getString(R.string.apply))
                                                .setPositiveButtonText(getString(R.string.delete_it))
                                                .setPositiveButtonTextColor(requireContext().getColor(R.color.kalayar_dialog_red_color))
                                                .setPositiveButtonRippleColor(requireContext().getColor(R.color.kalayar_dialog_red_color_tint))
                                                .setPositiveButtonBackgroundColor(requireContext().getColor(R.color.kalayar_page_background_color))
                                                .setNegativeButtonText(getString(R.string.cancel))
                                                .setNegativeButtonTextColor(requireContext().getColor(R.color.kalayar_dialog_blue_color))
                                                .setNegativeButtonRippleColor(requireContext().getColor(R.color.kalayar_dialog_blue_color_tint))
                                                .setNegativeButtonBackgroundColor(requireContext().getColor(R.color.kalayar_page_background_color))


                                            textQuestionDialog.setOnClickListener(object : OnInputConfirmListener {
                                                override fun onNegativeButtonClicked(p0: String?) {
                                                    textQuestionDialog.dismiss()
                                                    passwordDialog.show()
                                                }

                                                override fun onPositiveButtonClicked(p0: String?) {
                                                    if (p0.toString().equals(getString(R.string.apply))) {
                                                        lifecycleScope.launch {
                                                            withContext(Dispatchers.IO) {
                                                                PublicDatabase.getPublicDatabase(requireContext())
                                                                    .clearAllTables()
                                                                PrivateDatabase.getPrivateDatabase(requireContext())
                                                                    .clearAllTables()
                                                            }
                                                            val intent = requireContext().packageManager
                                                                .getLaunchIntentForPackage(requireContext().packageName)
                                                                ?.apply {
                                                                    addFlags(
                                                                        Intent.FLAG_ACTIVITY_NEW_TASK or
                                                                                Intent.FLAG_ACTIVITY_CLEAR_TASK
                                                                    )
                                                                }
                                                            intent?.let {
                                                                startActivity(it)
                                                                requireActivity().finishAffinity()
                                                            }
                                                        }
                                                    } else {
                                                        val shake_animation = AnimationUtils.loadAnimation(requireContext(),R.anim.shake_animation)
                                                        textQuestionDialog.startEditTextAnimation(shake_animation)
                                                        textQuestionDialog.setEditTextValue("")
                                                    }
                                                }
                                            })
                                            textQuestionDialog.show()
                                            questionDialog.dismiss()
                                        }
                                    })
                                    questionDialog.show()
                                    passwordDialog.dismiss()
                                }
                                override fun onPositiveButtonClicked(p0: String?) {
                                    if (user?.user_password.equals(p0)) {
                                        val action =
                                            SplashFragmentDirections.actionSplashFragmentToMainFragment()
                                        navController.navigate(action)
                                        passwordDialog.dismiss()
                                    } else {
                                        val shake_animation = AnimationUtils.loadAnimation(requireContext(),R.anim.shake_animation)
                                        passwordDialog.startEditTextAnimation(shake_animation)
                                        passwordDialog.setEditTextValue("")
                                    }
                                }
                            })
                            passwordDialog.show()
                        }
                    }
                }
            }
        }
    }

    private fun onBackPressed() {
        val backPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                requireActivity().finish()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(
            requireActivity(), backPressedCallback
        )
    }

}