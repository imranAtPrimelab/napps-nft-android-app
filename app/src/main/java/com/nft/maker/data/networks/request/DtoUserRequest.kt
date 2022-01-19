package com.nft.maker.data.networks.request

import com.google.gson.annotations.SerializedName

data class DtoUserCreateRequest(
    @SerializedName("full_name")
    val fullName: String?,
    @SerializedName("walletName")
    val walletName: String?,
    @SerializedName("phone")
    val phone: String?,
    @SerializedName("email")
    val email: String?
)