package com.nearlabs.nftmarketplace.data.networks.request

import com.google.gson.annotations.SerializedName

data class DtoLoginRequest(
    @SerializedName("walletName")
    val walletName : String?,
    @SerializedName("nonce")
    val nonce: String? = null
)
