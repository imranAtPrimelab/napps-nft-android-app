package com.nearlabs.nftmarketplace.repository

import com.nearlabs.nftmarketplace.data.networks.response.DtoWallet
import com.nearlabs.nftmarketplace.data.networks.response.DtoWalletResponse
import com.nearlabs.nftmarketplace.domain.model.User
import com.nearlabs.nftmarketplace.domain.model.nft.NFT
import com.nearlabs.nftmarketplace.domain.model.nft.NFTAuthor
import com.nearlabs.nftmarketplace.domain.model.nft.NFTInfo
import com.nearlabs.nftmarketplace.domain.model.nft.NFTType
import com.nearlabs.nftmarketplace.domain.model.transaction.Transaction
import com.nearlabs.nftmarketplace.domain.model.transaction.TransactionAddress
import com.nearlabs.nftmarketplace.domain.model.transaction.TransactionDirection
import kotlin.random.Random


/**
 * This is the dummy data generator for testing purpose.
 * It will be removed once integrated with API from backend.
 */
object DummyDataGenerator {

    private fun assetNames() = listOf(
        "Async Blueprints",
        "Party Bear",
        "Domains.Kred",
        "Decentraland Names"
    )

    private fun contactNames() = listOf(
        "Darlene Robertson",
        "Jacob Jones",
        "Jenny Wilson",
        "Ronald Richards",
        "Cameron Williamson",
        "Darrell Steward",
        "Wade Warren",
        "Courtney Henry"
    )

    fun contacts(): List<User> {
        val names = contactNames()
        return (0..100).map {
            User(
                id = it,
                name = names[it % names.size],
                nick = "@johndoe",
                image = ""
            )
        }
    }

    fun NFTs(): List<NFT> {
        val names = assetNames()
        return (17720L..17820L).map {
            val assetName = names[it.toInt() % names.size]
            NFT(
                id = it.toString(),
                name = assetName,
                type = when (it % 4) {
                    0L -> NFTType.DigitalArt
                    1L -> NFTType.Collectibles
                    2L -> NFTType.Music
                    else -> NFTType.DigitalArt
                },
                image = "",
                author = NFTAuthor(
                    name = "john_doe",
                    image = ""
                ),
                info = NFTInfo(
                    tokenId = "38943",
                    contract = "0xa6f79B60359f141df90A0C745125B131cAAfFD12".lowercase()
                ),
                description = "Having returned home to Rathleigh House near Macroom, Cork, Ireland, the hot-tempered Art became involved in a feud with a protestant landowner and magistrate, "
            )
        }
    }

    fun transactions(): List<Transaction> {
        return (17720L..17820L).map {
            Transaction(
                id = it,
                sender = TransactionAddress(
                    name = "michael.near",
                    address = "0xa6f79B60359f141df90A0C745125B131cAAfFD12".lowercase()
                ),
                receiver = TransactionAddress(
                    name = "michael.near",
                    address = "0xa6f79B60359f141df90A0C745125B131cAAfFD12".lowercase()
                ),
                direction = if (Random.nextBoolean()) TransactionDirection.Incoming else TransactionDirection.Outgoing,
                timestamp = System.currentTimeMillis()
            )
        }
    }

    fun wallets(): DtoWalletResponse {
        return DtoWalletResponse(
            listOf(
                DtoWallet(1, "johndoe.near", "johndoe.near.address", true),
                DtoWallet(2, "demo.near", "demo.near.address", false)
            )
        )
    }
}