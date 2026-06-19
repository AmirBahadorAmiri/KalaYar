package com.amirbahadoramiri.kalayar.views.fragments.base

import android.content.Intent
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.amirbahadoramiri.kalayar.views.activities.main.MainActivity

open class BaseFragment : Fragment() {

    fun openActivity(cls: Class<*>?) {
        startActivity(Intent(requireContext(), cls))
    }

    fun replaceFragment(baseFragment: BaseFragment) {
        (requireActivity() as MainActivity).replaceFragment(baseFragment)
    }

    fun addFragment(baseFragment: BaseFragment) {
        (requireActivity() as MainActivity).addFragment(baseFragment)
    }

    fun popBackStack() {
        (requireActivity() as MainActivity).popBackStack()
    }

    fun toast(str: String) {
        Toast.makeText(requireContext(), str, Toast.LENGTH_SHORT).show()
    }

}