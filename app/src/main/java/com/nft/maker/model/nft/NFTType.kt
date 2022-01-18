package com.nft.maker.model.nft

sealed class NFTType(val name: String) {
    object DigitalArt : NFTType("Digital Art")
    object Collectibles : NFTType("Collectibles")
    object Music : NFTType("Music")
}
