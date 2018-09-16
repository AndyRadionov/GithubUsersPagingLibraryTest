package io.github.andyradionov.githubuserspaginglibrarytest.app

import android.app.Application
import io.github.andyradionov.githubuserspaginglibrarytest.BuildConfig

import timber.log.Timber

class PagingApp : Application() {

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }

}
