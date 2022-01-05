package com.nearlabs.nftmarketplace.data.networks

import com.google.gson.JsonObject
import com.nearlabs.nftmarketplace.data.networks.request.DtoUserCreateRequest
import com.nearlabs.nftmarketplace.data.networks.response.DtoContact
import com.nearlabs.nftmarketplace.data.networks.response.DtoUserInfoResponse
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

interface UserApi {
    @GET("users/id")//
    suspend fun getUser(): JsonObject

    @PUT("users/h315j-3kn1i5-315j3")
    suspend fun modifyUser(): JSONObject

    @DELETE("users/h315j-3kn1i5-315j3")
    suspend fun deleteUser(): JSONObject

    @GET("user/{user_id}/resend_code")
    suspend fun resendCode(@Path("user_id") userId: String): JSONObject

    @POST("user/create")
    suspend fun createUser(@Body request: DtoUserCreateRequest): DtoUserInfoResponse
}