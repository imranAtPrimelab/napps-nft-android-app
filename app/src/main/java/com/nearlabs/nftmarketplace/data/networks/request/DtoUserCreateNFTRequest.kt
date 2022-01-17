package com.nearlabs.nftmarketplace.data.networks.request

import com.google.gson.annotations.SerializedName

data class DtoUserCreateNFTRequest(
    @SerializedName("fullName")
    val fullName: String?,

    @SerializedName("walletName")
    val walletName: String?,

    @SerializedName("phone")
    val phone: String?,

    @SerializedName("email")
    val email: String?,

    @SerializedName("nftID")
    val nftID: String?
)