package com.nearlabs.nftmarketplace.data.networks.response

import com.google.gson.annotations.SerializedName

data class DtoContact(
    @SerializedName("first_name")
    val firstName: String?
)