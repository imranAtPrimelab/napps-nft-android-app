package com.nearlabs.nftmarketplace.domain.model.nft

import com.google.gson.JsonObject
import com.nearlabs.nftmarketplace.data.networks.response.DtoNFTResponse
import com.nearlabs.nftmarketplace.domain.model.User
import com.nearlabs.nftmarketplace.domain.model.toDomain

data class NFT(
    val id: String,
    val name: String,
    val type: String,
    val image: String,
    val author: NFTAuthor,
    val description: String,
    val info: NFTInfo,
    val owner: User? = null,
    val attributes : List<JsonObject>? = null
)

fun DtoNFTResponse.toDomainModel() = NFT(
    id = nftId,
    name = title.orEmpty(),
    image = fileUrl.orEmpty(),
    type = category.orEmpty(),
    author = NFTAuthor(ownerId.orEmpty(), "image"),
    description = description.orEmpty(),
    info = NFTInfo("token", "contact"),
    owner = owner?.toDomain(),
    attributes = attributes
)