package com.amirbahadoramiri.kalayar.presentation.ui.fragments.database_fragment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.lifecycleScope
import com.amirbahadoramiri.kalayar.R
import com.amirbahadoramiri.kalayar.data.db.PublicDatabase
import com.amirbahadoramiri.kalayar.databinding.DatabaseFragmentBinding
import com.amirbahadoramiri.kalayar.presentation.base.BaseFragment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream

class DatabaseFragment : BaseFragment() {

    lateinit var binding: DatabaseFragmentBinding

    private val createDocumentLauncher = registerForActivityResult(ActivityResultContracts.CreateDocument("application/octet-stream")) { uri ->
        uri?.let { backupDatabase(it) }
    }

    private val openDocumentLauncher = registerForActivityResult(ActivityResultContracts.OpenDocument()) { uri ->
        uri?.let { restoreDatabase(it) }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = DatabaseFragmentBinding.inflate(inflater)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setup()
    }

    private fun setup() {
        customOnBackPressed()
        
        binding.backBtn.setOnClickListener {
            popBackStack()
        }

        binding.backupBtn.setOnClickListener {
            createDocumentLauncher.launch("kalayar_backup_${System.currentTimeMillis()}.db")
        }

        binding.restoreBtn.setOnClickListener {
            openDocumentLauncher.launch(arrayOf("application/octet-stream", "application/x-sqlite3", "*/*"))
        }
    }

    private fun backupDatabase(uri: Uri) {
        lifecycleScope.launch {
            try {
                withContext(Dispatchers.IO) {
                    val db = PublicDatabase.getPublicDatabase(requireContext())
                    db.openHelper.writableDatabase.query("PRAGMA wal_checkpoint(FULL)").use { it.moveToFirst() }
                    PublicDatabase.closeDatabase()

                    val dbFile = requireContext().getDatabasePath("public.db")
                    if (!dbFile.exists()) {
                        withContext(Dispatchers.Main) {
                            toast(getString(R.string.database_not_initialized))
                        }
                        return@withContext
                    }

                    requireContext().contentResolver.openOutputStream(uri)?.use { output ->
                        FileInputStream(dbFile).use { input ->
                            input.copyTo(output)
                        }
                    }
                    withContext(Dispatchers.Main) {
                        toast(getString(R.string.backup_saved_successfully))
                    }
                }
            } catch (e: Exception) {
                toast("خطا در تهیه نسخه پشتیبان: ${e.message}")
            }
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

    private fun customOnBackPressed() {
        val backPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                popBackStack()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this, backPressedCallback)
    }

}
