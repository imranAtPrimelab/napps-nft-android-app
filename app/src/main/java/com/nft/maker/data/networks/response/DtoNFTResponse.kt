package com.nft.maker.data.networks.response

import com.google.gson.JsonObject
import com.google.gson.annotations.SerializedName

data class DtoNFTResponse(
    @SerializedName("collection_id")
    val collectionId: String,
    @SerializedName("nft_id")
    val nftId: String,
    @SerializedName("title")
    val title: String?,
    @SerializedName("description")
    val description: String?,
    @SerializedName("category")
    val category: String?,
    @SerializedName("file_url")
    val fileUrl: String?,
    @SerializedName("created")
    val created: String?,
    @SerializedName("updated")
    val updated: String?,
    @SerializedName("status")
    val status: String?,
    @SerializedName("owner_id")
    val ownerId: String?,
    @SerializedName("attributes")
    val attributes: List<JsonObject>,
    @SerializedName("action_type")
    val actionType: String,
    @SerializedName("owner")
    val owner: DtoUserResponse? = null,
)