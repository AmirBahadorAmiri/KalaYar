package com.amirbahadoramiri.kalayar.presentation.ui.fragments.splash

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
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

        val version = "نگارش " + requireContext().packageManager.getPackageInfo(
            requireContext().packageName, 0
        ).versionName
        binding.versionName.text = version

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.RESUMED) {
                delay(2000.milliseconds)
                val user = PrivateDatabase.getPrivateDatabase(requireContext()).getPrivateDAO().getUser()
                if (user == null) {
                    val newUser = User(Devices.getUniqueId(requireContext()), false)
                    PrivateDatabase.getPrivateDatabase(requireContext()).getPrivateDAO().addUser(newUser)
                }
                val store = PublicDatabase.getPublicDatabase(requireContext()).getPublicDAO().getStore()
                val navController = findNavController()
                if (navController.currentDestination?.id == R.id.splashFragment) {
                    if (store == null) {
                        val action = SplashFragmentDirections.actionSplashFragmentToStoreRegisterFragment()
                        navController.navigate(action)
                    } else {
                        val action = SplashFragmentDirections.actionSplashFragmentToMainFragment()
                        navController.navigate(action)
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