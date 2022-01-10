package com.nearlabs.nftmarketplace.data.networks

import com.nearlabs.nftmarketplace.data.networks.response.DtoBaseResponse
import com.nearlabs.nftmarketplace.data.networks.response.DtoContact
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import android.provider.ContactsContract
import com.nearlabs.nftmarketplace.domain.model.Contact
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body

interface ContactApi {


    @POST("contacts/import")
    suspend fun importContact(@Body contact: List<Contact>) : Response<ResponseBody>

    @GET("contacts/list/{ownerId}")
    suspend fun getContacts(@Path("ownerId") ownerId: String): DtoBaseResponse<List<DtoContact>>
}