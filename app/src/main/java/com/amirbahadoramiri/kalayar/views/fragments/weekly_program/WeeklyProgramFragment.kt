package com.amirbahadoramiri.kalayar.views.fragments.weekly_program

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import com.amirbahadoramiri.kalayar.databinding.WeeklyProgramFragmentBinding
import com.amirbahadoramiri.kalayar.views.fragments.base.BaseFragment

class WeeklyProgramFragment : BaseFragment() {

    companion object {

        private var instance: WeeklyProgramFragment? = null

        fun getInstance(): WeeklyProgramFragment {

            if (instance == null) {
                instance = WeeklyProgramFragment()
            }
            return instance!!
        }

    }

    lateinit var binding: WeeklyProgramFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = WeeklyProgramFragmentBinding.inflate(inflater)
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