package com.nearlabs.nftmarketplace.data.preference

import android.content.SharedPreferences
import com.nearlabs.nftmarketplace.common.extensions.boolean

class SharePrefs(private val sharePrefs: SharedPreferences) {


    var isLoggedIn by sharePrefs.boolean()

    fun registerKeyChangeListener(listener: SharedPreferences.OnSharedPreferenceChangeListener) {
        sharePrefs.registerOnSharedPreferenceChangeListener(listener)
    }

    fun unregisterKeyChangeListener(listener: SharedPreferences.OnSharedPreferenceChangeListener) {
        sharePrefs.unregisterOnSharedPreferenceChangeListener(listener)
    }

    fun clear() {
        sharePrefs.edit().clear().apply()
    }

    fun getUserId(): String {
        return "h315j-3kn1i5-315j3"
    }
}