package com.nearlabs.nftmarketplace.data.networks.api

import com.google.gson.JsonObject
import com.nearlabs.nftmarketplace.data.networks.response.DtoUserResponse
import org.json.JSONObject
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT

interface Contacts {

    @GET("contacts/:contact_id")//
    suspend fun getContact(): JsonObject

    @PUT("contacts/:contact_id")
    suspend fun modifyContact(): JSONObject

    @DELETE("contacts/:contact_id")
    suspend fun deleteContact(): JSONObject

    @POST("contacts")
    suspend fun addContact(): JSONObject

    @POST("contacts/import")
    suspend fun importContacts(): JSONObject

    @GET("contacts/list/:owner_id")
    suspend fun listContacts(): JSONObject

    companion object {

        private const val BASE_URL = "https://bib4ivjb9i.execute-api.us-west-1.amazonaws.com/"

        fun create() : Contacts {

            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build()
            return retrofit.create(Contacts::class.java)

        }
    }
}