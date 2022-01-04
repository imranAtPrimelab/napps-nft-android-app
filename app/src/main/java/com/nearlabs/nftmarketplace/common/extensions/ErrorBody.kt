package com.nearlabs.nftmarketplace.common.extensions

import com.google.gson.annotations.SerializedName

data class ErrorBody(
    @SerializedName("statusCode")
    val status: Int?,

    @SerializedName("error")
    val error: String?,

    @SerializedName("message")
    val message: String?
)