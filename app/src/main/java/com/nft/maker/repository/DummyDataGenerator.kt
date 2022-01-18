package com.nft.maker.repository

import com.nft.maker.data.networks.response.DtoWallet
import com.nft.maker.data.networks.response.DtoWalletResponse
import com.nft.maker.model.Contact

import com.nft.maker.model.nft.NFT
import com.nft.maker.model.nft.NFTAuthor
import com.nft.maker.model.nft.NFTInfo
import com.nft.maker.model.transaction.Transaction
import com.nft.maker.model.transaction.CounterParty
import com.nft.maker.model.transaction.TransactionDirection
import org.threeten.bp.LocalDateTime
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

    fun contacts(): List<Contact> {
        val names = contactNames()
        return (0..100).map {
            Contact(
                first_name = names[it % names.size],
                last_name = "@johndoe",
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
                type = "",
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
                id = it.toString(),
                counterParty = CounterParty(
                    name = "michael.near",
                    walletId = "0xa6f79B60359f141df90A0C745125B131cAAfFD12".lowercase()
                ),
                direction = if (Random.nextBoolean()) TransactionDirection.Incoming else TransactionDirection.Outgoing,
                timestamp = LocalDateTime.now()
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