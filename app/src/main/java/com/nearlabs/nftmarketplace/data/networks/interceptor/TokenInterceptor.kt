package com.nearlabs.nftmarketplace.data.networks.interceptor

import com.nearlabs.nftmarketplace.data.preference.SharePrefs
import okhttp3.Interceptor
import okhttp3.Response

class TokenInterceptor(private val sharePrefsRepository: SharePrefs) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val idToken = sharePrefsRepository.accessToken
        val requestBuilder = chain.request().newBuilder()
        if (idToken.isNotEmpty()) {
            requestBuilder.header("Authorization", "Bearer $idToken")
        }
        return chain.proceed(requestBuilder.build())
    }
}