package com.nft.maker.data.networks.response

import com.google.gson.annotations.SerializedName

data class DtoWalletResponse(
    @SerializedName("list")
    val wallets: List<DtoWallet>? = null
)

data class DtoWallet(
    @SerializedName("id")
    val id: Int,

    @SerializedName("name")
    val name: String? = null,

    @SerializedName("address")
    val address: String? = null,

    @SerializedName("selected")
    val selected: Boolean? = null
)