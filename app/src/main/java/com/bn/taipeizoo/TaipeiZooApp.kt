package com.bn.taipeizoo

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber
import javax.inject.Inject

@HiltAndroidApp
class TaipeiZooApp: Application() {
    @Inject
    lateinit var tree: Timber.DebugTree

    override fun onCreate() {
        super.onCreate()
        Timber.plant(tree)
    }
}