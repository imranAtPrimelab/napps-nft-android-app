package com.nearlabs.nftmarketplace

import android.app.Application
import com.appsflyer.AppsFlyerLib
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class NftApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        AppsFlyerLib.getInstance().setDebugLog(true)
        AppsFlyerLib.getInstance().init(BuildConfig.AppsFlyer_Dev_Key, null, this)
        AppsFlyerLib.getInstance().start(this,BuildConfig.AppsFlyer_Dev_Key)
    }
}