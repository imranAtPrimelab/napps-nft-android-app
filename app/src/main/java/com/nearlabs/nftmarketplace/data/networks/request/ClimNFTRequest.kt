package com.nearlabs.nftmarketplace.data.networks.request

import com.google.gson.annotations.SerializedName

data class ClimNFTRequest(
    @SerializedName("owner_id")
    val ownerId: String?
)
