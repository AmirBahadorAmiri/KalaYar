package com.amirbahadoramiri.kalayar.views.fragments.database_fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import com.amirbahadoramiri.kalayar.databinding.DatabaseFragmentBinding
import com.amirbahadoramiri.kalayar.views.fragments.base.BaseFragment

class DatabaseFragment : BaseFragment() {

    companion object {

        private var instance: DatabaseFragment? = null

        fun createInstance(): DatabaseFragment {
            return DatabaseFragment()
        }

        fun getInstance(): DatabaseFragment {

            if (instance == null) {
                instance = createInstance()
            }
            return instance!!
        }

    }

    lateinit var binding: DatabaseFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = DatabaseFragmentBinding.inflate(inflater)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        findViews()
        setup()
    }

    private fun findViews() {
    }

    private fun setup() {
        customOnBackPressed()
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