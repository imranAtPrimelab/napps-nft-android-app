package com.nearlabs.nftmarketplace.data.networks

import com.nearlabs.nftmarketplace.data.networks.response.DtoContact
import retrofit2.http.GET
import retrofit2.http.Path

interface ContactApi {

    @GET("/contacts/list/{ownerId}")
    suspend fun getContacts(@Path("ownerId") ownerId: String): List<DtoContact>
}