package com.amirbahadoramiri.kalayar.presentation.base

import android.content.Intent
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController

open class BaseFragment : Fragment() {

    fun openActivity(cls: Class<*>?) {
        startActivity(Intent(requireContext(), cls))
    }

    fun popBackStack() {
        findNavController().popBackStack()
    }

    fun toast(str: String) {
        Toast.makeText(requireContext(), str, Toast.LENGTH_SHORT).show()
    }

}