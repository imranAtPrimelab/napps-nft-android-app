package com.nearlabs.nftmarketplace.data.preference

import android.content.SharedPreferences
import com.nearlabs.nftmarketplace.common.extensions.boolean
import com.nearlabs.nftmarketplace.common.extensions.string

class SharePrefs(private val sharePrefs: SharedPreferences) {

    var userId by sharePrefs.string()
    var userName by sharePrefs.string()
    var accessToken by sharePrefs.string()
    var idToken by sharePrefs.string()
    var refreshToken by sharePrefs.string()
    var loginType  by sharePrefs.string()
    var walletName  by sharePrefs.string()
    var userInfo by sharePrefs.string()

    fun registerKeyChangeListener(listener: SharedPreferences.OnSharedPreferenceChangeListener) {
        sharePrefs.registerOnSharedPreferenceChangeListener(listener)
    }

    fun unregisterKeyChangeListener(listener: SharedPreferences.OnSharedPreferenceChangeListener) {
        sharePrefs.unregisterOnSharedPreferenceChangeListener(listener)
    }

    fun clear() {
        sharePrefs.edit().clear().apply()
    }

}