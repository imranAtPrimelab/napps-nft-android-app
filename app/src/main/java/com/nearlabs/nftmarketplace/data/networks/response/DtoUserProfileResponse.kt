package com.nearlabs.nftmarketplace.data.networks.response

import com.google.gson.annotations.SerializedName

data class DtoUserProfileResponse(
    @SerializedName("message")
    val message: String,
    @SerializedName("data")
    val dtoUserInfo: DtoUserResponse
)