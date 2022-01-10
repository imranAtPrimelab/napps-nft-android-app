package com.nearlabs.nftmarketplace.data.networks.response

import com.google.gson.annotations.SerializedName

data class DtoUserResponse(
    @SerializedName("user_id")
    val id: String,
    @SerializedName("wallet_status")
    val walletStatus: String?,
    @SerializedName("full_name")
    val fullName: String?,
    @SerializedName("email")
    val email: String?,
    @SerializedName("wallet_id")
    val walletId: String,
    @SerializedName("phone")
    val phone: String?,
    @SerializedName("verified")
    val verified: Boolean?,
    @SerializedName("created")
    val created: Long?
)