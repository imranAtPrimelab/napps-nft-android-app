package com.nearlabs.nftmarketplace.domain.model.nft

data class NFT(
     val id: Long,
     val name: String,
     val type: NFTType,
     val image: String,
     val author: NFTAuthor,
     val description: String,
     val info: NFTInfo
)

