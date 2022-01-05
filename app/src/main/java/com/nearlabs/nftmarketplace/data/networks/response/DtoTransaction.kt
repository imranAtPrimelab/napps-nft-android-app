package com.nearlabs.nftmarketplace.data.networks.response

import com.google.gson.annotations.SerializedName

data class DtoTransaction(
    @SerializedName("sender_id")
    val senderId: String
)