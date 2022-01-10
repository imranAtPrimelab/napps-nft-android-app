package com.nearlabs.nftmarketplace.data.networks.response

import com.google.gson.annotations.SerializedName

data class DtoBaseResponse<T>(
    @SerializedName("message")
    val message: String?,
    @SerializedName("data")
    val data: T
)

data class DtoMessageResponse (
    @SerializedName("message")
    val message: String,
)