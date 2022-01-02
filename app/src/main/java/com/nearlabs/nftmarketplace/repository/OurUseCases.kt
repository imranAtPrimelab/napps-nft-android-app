package com.nearlabs.nftmarketplace.repository

import com.google.gson.JsonObject
import com.nearlabs.nftmarketplace.data.APIs
import javax.inject.Inject

class OurUserCases @Inject constructor(
    private val apIs: APIs
) {
    suspend operator fun invoke(): JsonObject {
        val response = apIs.sampleGet()
        //here you can add some domain logic or call another UseCase
        return response
    }
}