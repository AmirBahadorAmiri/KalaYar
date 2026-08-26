package com.amirbahadoramiri.kalayar.presentation.ui.fragments.store_register

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.amirbahadoramiri.kalayar.R
import com.amirbahadoramiri.kalayar.data.db.PublicDatabase
import com.amirbahadoramiri.kalayar.databinding.StoreRegisterFragmentBinding
import com.amirbahadoramiri.kalayar.domain.models.Store
import com.amirbahadoramiri.kalayar.presentation.base.BaseFragment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream

class StoreRegisterFragment : BaseFragment() {

    lateinit var binding: StoreRegisterFragmentBinding
    private lateinit var storeViewModel: StoreViewModel

    private val openDocumentLauncher = registerForActivityResult(ActivityResultContracts.OpenDocument()) { uri ->
        uri?.let { restoreDatabase(it) }
    }

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

        binding.restoreButton.setOnClickListener {
            openDocumentLauncher.launch(arrayOf("application/octet-stream", "application/x-sqlite3", "*/*"))
        }

    }

    private fun restoreDatabase(uri: Uri) {
        lifecycleScope.launch {
            try {
                withContext(Dispatchers.IO) {
                    PublicDatabase.closeDatabase()

                    val dbFile = requireContext().getDatabasePath("public.db")

                    requireContext().contentResolver.openInputStream(uri)?.use { input ->
                        FileOutputStream(dbFile).use { output ->
                            input.copyTo(output)
                        }
                    }

                    val shmFile = File(dbFile.path + "-shm")
                    val walFile = File(dbFile.path + "-wal")
                    if (shmFile.exists()) shmFile.delete()
                    if (walFile.exists()) walFile.delete()

                    withContext(Dispatchers.Main) {
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
                }
            } catch (e: Exception) {
                toast("خطا در بازیابی دیتابیس: ${e.message}")
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