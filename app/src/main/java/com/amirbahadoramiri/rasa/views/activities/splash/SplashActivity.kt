package com.amirbahadoramiri.rasa.views.activities.splash

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.widget.AppCompatTextView
import com.amirbahadoramiri.rasa.R
import com.amirbahadoramiri.rasa.models.User
import com.amirbahadoramiri.rasa.tools.database.PrivateDatabase
import com.amirbahadoramiri.rasa.views.activities.base.BaseActivity
import com.amirbahadoramiri.rasa.views.activities.main.MainActivity
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.core.SingleObserver
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class SplashActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_splash)
        setViewCompat()

        findViews()
        setupViews()

    }

    private fun findViews() {
    }


    private fun setupViews() {
        findViewById<AppCompatTextView>(R.id.splash_activity_version_txt).text =
            "نگارش " + packageManager.getPackageInfo(
                packageName,
                0
            ).versionName

        Observable.timer(2500, TimeUnit.MILLISECONDS)
            .observeOn(Schedulers.io())
            .subscribeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<Long> {
                override fun onSubscribe(d: Disposable) {}
                override fun onNext(t: Long) {}
                override fun onError(e: Throwable) {}

                override fun onComplete() {

                    PrivateDatabase.getPrivateDB(this@SplashActivity).privateDAO().getUser()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(object : SingleObserver<User> {
                            override fun onSubscribe(d: Disposable) {}
                            override fun onSuccess(user: User) {
                                startActivity(
                                    Intent(
                                        this@SplashActivity,
                                        MainActivity::class.java
                                    )
                                )
                                finish()
                            }

                            override fun onError(e: Throwable) {
                                startActivity(
                                    Intent(
                                        this@SplashActivity,
                                        MainActivity::class.java
                                    )
                                )
                                finish()
                            }

                        }
                        )


                }

            })

    }

}