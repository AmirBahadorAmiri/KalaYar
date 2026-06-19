package com.amirbahadoramiri.kalayar.views.fragments.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.recyclerview.widget.GridLayoutManager
import com.amirbahadoramiri.kalayar.R
import com.amirbahadoramiri.kalayar.databinding.MainFragmentBinding
import com.amirbahadoramiri.kalayar.tools.logger.Logger
import com.amirbahadoramiri.kalayar.tools.network.Network
import com.amirbahadoramiri.kalayar.views.fragments.base.BaseFragment
import com.amirbahadoramiri.kalayar.views.fragments.database_fragment.DatabaseFragment
import com.amirbahadoramiri.kalayar.views.fragments.inventory.InventoryFragment
import com.amirbahadoramiri.kalayar.views.fragments.money.MoneyFragment
import com.amirbahadoramiri.kalayar.views.fragments.product.ProductFragment
import com.amirbahadoramiri.kalayar.views.fragments.transaction.TransactionsFragment
import com.amirbahadoramiri.kalayar.views.fragments.weekly_program.WeeklyProgramFragment
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.SingleObserver
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers

class MainFragment : BaseFragment() {

    companion object {

        private var instance: MainFragment? = null

        fun createInstance(): MainFragment {
            return MainFragment()
        }

        fun getInstance(): MainFragment {

            if (instance == null) {
                instance = createInstance()
            }
            return instance!!
        }

    }

    lateinit var binding: MainFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = MainFragmentBinding.inflate(inflater)
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

        val dataList = mutableListOf<ToolsModel>()

        dataList.add(ToolsModel(getString(R.string.products), R.drawable.kalayar_products) {
            replaceFragment(ProductFragment.getInstance())
        })
        dataList.add(ToolsModel(getString(R.string.transactions), R.drawable.kalayar_transactions) {
            replaceFragment(TransactionsFragment.getInstance())
        })
        dataList.add(ToolsModel(getString(R.string.inventory), R.drawable.kalayar_inventory) {
            replaceFragment(InventoryFragment.getInstance())
        })
        dataList.add(ToolsModel(getString(R.string.money), R.drawable.kalayar_money) {
            replaceFragment(MoneyFragment.getInstance())
        })
        dataList.add(
            ToolsModel(
                getString(R.string.weekly_program),
                R.drawable.kalayar_weekly_program
            ) {
                replaceFragment(WeeklyProgramFragment.getInstance())
            })
        dataList.add(
            ToolsModel(
                getString(R.string.import_export_database),
                R.drawable.kalayar_database
            ) {
                replaceFragment(DatabaseFragment.getInstance())
            })

        val adapter1 = MainFragmentToolsRecyclerViewAdapter(dataList)
        binding.recyclerview1.layoutManager =
            GridLayoutManager(requireContext(), 3, GridLayoutManager.VERTICAL, false)
        binding.recyclerview1.adapter = adapter1

        Network.getNetworkInterface().getData(Network.URL)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : SingleObserver<String> {
                override fun onSubscribe(d: Disposable) {}
                override fun onSuccess(t: String) {

                    Logger.debug(t)

                }

                override fun onError(e: Throwable) {}
            });

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