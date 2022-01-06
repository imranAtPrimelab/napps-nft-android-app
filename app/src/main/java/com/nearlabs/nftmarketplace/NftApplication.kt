package com.nearlabs.nftmarketplace

import android.app.Application
import com.facebook.stetho.Stetho
import com.jakewharton.threetenabp.AndroidThreeTen
import com.appsflyer.AppsFlyerLib
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class NftApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        AndroidThreeTen.init(this)
        Stetho.initializeWithDefaults(this)
        AppsFlyerLib.getInstance().init(BuildConfig.AppsFlyer_Dev_Key, null, this)
        AppsFlyerLib.getInstance().start(this, BuildConfig.AppsFlyer_Dev_Key)
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}