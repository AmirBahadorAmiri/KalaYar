package com.amirbahadoramiri.kalayar.views.fragments.splash

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import com.amirbahadoramiri.kalayar.R
import com.amirbahadoramiri.kalayar.core.models.User
import com.amirbahadoramiri.kalayar.databinding.SplashFragmentBinding
import com.amirbahadoramiri.kalayar.tools.database.PrivateDatabase
import com.amirbahadoramiri.kalayar.views.fragments.base.BaseFragment
import com.amirbahadoramiri.kalayar.views.fragments.main.MainFragment
import com.amirbahadoramiri.kalayar.views.fragments.store_register.StoreRegisterFragment
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.core.SingleObserver
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class SplashFragment : BaseFragment() {

    companion object {

        private var instance: SplashFragment? = null

        fun createInstance(): SplashFragment {
            return SplashFragment()
        }

        fun getInstance(): SplashFragment {

            if (instance == null) {
                instance = createInstance()
            }
            return instance!!
        }

    }

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
        findViews()
        setup()
    }

    private fun findViews() {
    }

    private fun setup() {

        onBackPressed()

        val version = "نگارش " + requireContext().packageManager.getPackageInfo(
            requireContext().packageName,
            0
        ).versionName
        binding.versionName.text = version

        Observable.timer(250, TimeUnit.MILLISECONDS)
            .observeOn(Schedulers.io())
            .subscribeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<Long> {
                override fun onSubscribe(d: Disposable) {}
                override fun onNext(t: Long) {}
                override fun onError(e: Throwable) {}
                override fun onComplete() {
                    PrivateDatabase.getPrivateDatabase(requireContext()).getPrivateDAO().getUser()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(object : SingleObserver<User> {
                            override fun onSubscribe(d: Disposable) {}
                            override fun onSuccess(user: User) {
//                                Logger.debug("onSuccess")
                                replaceFragment(MainFragment.getInstance())
                            }

                            override fun onError(e: Throwable) {
//                                Logger.debug("onError2: ${e.message}")
                                replaceFragment(StoreRegisterFragment.getInstance())

                                /*
                                *
                                * اگر قبلا کاربر وجد داشت و برش نگردوند
                                * کاربر باید به بخش پشتیبانی هدایت شود
                                *
                                * */

                            }
                        }
                        )
                }
            })
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