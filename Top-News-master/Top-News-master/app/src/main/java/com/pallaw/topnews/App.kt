package com.pallaw.topnews

import android.app.Application
import androidx.annotation.NonNull
import timber.log.Timber

/**
 * Created by Pallaw Pathak on 08/05/20. - https://www.linkedin.com/in/pallaw-pathak-a6a324a1/
 */
class App : Application() {
    override fun onCreate() {
        super.onCreate()

        setupTimber()
    }

    private fun setupTimber() {
        if (BuildConfig.DEBUG) {
            Timber.plant(object : Timber.DebugTree() {
                override fun log(
                    priority: Int,
                    tag: String?,
                    @NonNull msg: String,
                    t: Throwable?
                ) {
                    super.log(priority, "top_news_$tag", msg, t)
                }
            })
        }
    }
}