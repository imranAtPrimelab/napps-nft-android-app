package com.nft.maker.data.networks.request

import com.google.gson.annotations.SerializedName
import java.io.File

data class NftCreateRequest(
    var file: File,
    var nftInformation: NftInformation
)

data class NftInformation(

    @field:SerializedName("collection_id")
    var collectionId: String? = null,

    @field:SerializedName("tracker_id")
    var trackerId: String? = null,

    @field:SerializedName("owner_id")
    var ownerId: String? = null,

    @field:SerializedName("description")
    var description: String? = null,

    @field:SerializedName("attributes")
    var attributes: List<AttributesItem>? = null,

    @field:SerializedName("title")
    var title: String? = null,

    @field:SerializedName("category")
    var category: String? = null
)

data class AttributesItem(
    @field:SerializedName("attr_name")
    val attrName: String? = null,

    @field:SerializedName("attr_value")
    val attrValue: String? = null
)