package com.amirbahadoramiri.kalayar.views.fragments.store_register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import com.amirbahadoramiri.kalayar.R
import com.amirbahadoramiri.kalayar.core.models.Store
import com.amirbahadoramiri.kalayar.core.models.User
import com.amirbahadoramiri.kalayar.databinding.StoreRegisterFragmentBinding
import com.amirbahadoramiri.kalayar.tools.database.PrivateDatabase
import com.amirbahadoramiri.kalayar.tools.database.PublicDatabase
import com.amirbahadoramiri.kalayar.tools.devices.Devices
import com.amirbahadoramiri.kalayar.views.fragments.base.BaseFragment
import com.amirbahadoramiri.kalayar.views.fragments.main.MainFragment
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.CompletableObserver
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers

class StoreRegisterFragment : BaseFragment() {

    companion object {

        private var instance: StoreRegisterFragment? = null

        fun createInstance(): StoreRegisterFragment {
            return StoreRegisterFragment()
        }

        fun getInstance(): StoreRegisterFragment {

            if (instance == null) {
                instance = createInstance()
            }
            return instance!!
        }

    }

    lateinit var binding: StoreRegisterFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = StoreRegisterFragmentBinding.inflate(inflater)
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

                val user = User(Devices.getUniqueId(requireContext()), false)

                PrivateDatabase.getPrivateDatabase(requireContext()).getPrivateDAO()
                    .insertUser(user)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(object : CompletableObserver {
                        override fun onSubscribe(d: Disposable) {}
                        override fun onComplete() {

                            PublicDatabase.getPublicDatabase(requireContext())
                                ?.getPublicDAO()
                                ?.addStore(store)
                                ?.subscribeOn(Schedulers.io())
                                ?.observeOn(AndroidSchedulers.mainThread())
                                ?.subscribe(object : CompletableObserver {
                                    override fun onSubscribe(d: Disposable) {}
                                    override fun onComplete() {

                                        replaceFragment(MainFragment.getInstance())

                                    }

                                    override fun onError(e: Throwable) {

                                        /*
                                        *
                                        * اگر نتونست فروشگاه بسازه چیکار باید کنه ؟
                                        *
                                        * */

                                    }
                                })

                        }

                        override fun onError(e: Throwable) {

                            /*
                            *
                            * اگر نتونست کاربر بسازه باید چیکار کنه ؟
                            *
                            * */

                        }
                    })
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
            requireActivity(),
            backPressedCallback
        )
    }

}