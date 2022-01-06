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
    @GET("{user_id}")//
    suspend fun getUser(@Path("user_id") userId: String): DtoUserInfoResponse

    @PUT("{user_id}")
    suspend fun modifyUser(@Path("user_id") userId: String): DtoUserInfoResponse

    @DELETE("{user_id}")
    suspend fun deleteUser(@Path("user_id") userId: String): DtoUserInfoResponse

    @GET("user/{user_id}/resend_code")
    suspend fun resendCode(@Path("user_id") userId: String): DtoUserInfoResponse

    @POST("user/create")
    suspend fun createUser(@Body request: DtoUserCreateRequest): DtoUserInfoResponse

    @POST("user/suggest/?walletName=moisesmarques.near&suggestionCount=10")
    suspend fun suggestWalletName(@Body request: DtoUserCreateRequest): DtoUserInfoResponse
}