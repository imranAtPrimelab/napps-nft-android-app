package com.nft.maker.data.networks.response

import com.google.gson.annotations.SerializedName

data class DtoLoginResponse (

    @SerializedName("message")
    val message: String,
    @SerializedName("code")
    val code: String,
    @SerializedName("type")
    val type: String

)