package com.nearlabs.nftmarketplace.repository

import com.google.gson.JsonObject
import com.nearlabs.nftmarketplace.common.extensions.safeCall
import com.nearlabs.nftmarketplace.data.networks.*
import com.nearlabs.nftmarketplace.data.networks.request.DtoLoginRequest
import com.nearlabs.nftmarketplace.data.networks.request.DtoSendTransactionRequest
import com.nearlabs.nftmarketplace.data.networks.request.DtoUserCreateRequest
import com.nearlabs.nftmarketplace.data.preference.SharePrefs
import com.nearlabs.nftmarketplace.domain.model.nft.toDomainModel
import com.nearlabs.nftmarketplace.domain.model.toDomain
import com.nearlabs.nftmarketplace.domain.model.toDomainModel
import com.nearlabs.nftmarketplace.domain.model.transaction.TransactionDirection
import com.nearlabs.nftmarketplace.domain.model.transaction.toDomainModel

class Repository(
    private val api: Api,
    private val transactionApi: TransactionApi,
    private val contactApi: ContactApi,
    private val nftApi: NFTApi,
    private val userApi: UserApi,
    private val loginApi: LoginApi,
    private val sharePrefs: SharePrefs
) {

    fun isLoggedIn() = sharePrefs.idToken.isNotEmpty()

    suspend fun getContacts() = safeCall {
        // TODO: need to pass owner id
        val dtoContacts = contactApi.getContacts("").data
        dtoContacts.mapNotNull { it.toDomainModel() }
    }

    suspend fun getTransactions() = safeCall {
        val dtoTransactions = transactionApi.getTransaction()
        dtoTransactions.map { it.toDomainModel() }
    }

    suspend fun getSentTransactions() = safeCall {
        val dtoTransactions = transactionApi.getTransaction()
        dtoTransactions
            .map { it.toDomainModel() }
            .filter { it.direction == TransactionDirection.Outgoing }
    }

    suspend fun getRecvTransactions() = safeCall {
        val dtoTransactions = transactionApi.getTransaction()
        dtoTransactions
            .map { it.toDomainModel() }
            .filter { it.direction == TransactionDirection.Incoming }
    }

    suspend fun getRecentTransactions() = safeCall {
        val dtoTransactions = transactionApi.getTransaction().take(20)
        dtoTransactions.map { it.toDomainModel() }
    }


    suspend fun getDummyTransactions() = safeCall {
        DummyDataGenerator.transactions()
    }

    suspend fun getDummySentTransactions() = safeCall {
        DummyDataGenerator.transactions().filter { it.direction == TransactionDirection.Outgoing }
    }

    suspend fun getDummyRecvTransactions() = safeCall {
        DummyDataGenerator.transactions().filter { it.direction == TransactionDirection.Incoming }
    }

    suspend fun getDummyNFTs() = safeCall {
        DummyDataGenerator.NFTs()
    }

    suspend fun getDummyPeoples() = safeCall {
        DummyDataGenerator.peoples()
    }

    suspend fun getDummyWallets() = safeCall {
        DummyDataGenerator.wallets().wallets?.map { it.toDomainModel() } ?: emptyList()
    }

    suspend fun getAllNFTCollection() = safeCall {
        val dtoNft = nftApi.getAllNFTCollections(sharePrefs.userId)
        dtoNft.data.map { it.toDomainModel() }
    }

    suspend fun createUser(name: String, walletId: String, phone: String, email: String) =
        safeCall {
            val request = DtoUserCreateRequest(
                fullName = name,
                walletName = walletId,
                phone = phone,
                email = email
            )
            val dtoResponse = userApi.createUser(request).apply {
                sharePrefs.userId = userInfo.id
                sharePrefs.accessToken = accessToken
                sharePrefs.idToken = idToken
                sharePrefs.refreshToken = refreshToken
            }
            dtoResponse.userInfo.toDomain()
        }

    suspend fun login(walletName: String) =
        safeCall {
            val request = DtoLoginRequest(
                walletName = walletName
            )

            val dtoResponse = loginApi.login(request).apply {
                sharePrefs.loginType = type
                sharePrefs.walletName = walletName
            }

    }

    suspend fun verifyLogin(walletName: String, nonce : String) =
        safeCall {
            val request = DtoLoginRequest(
                walletName = walletName,
                nonce = nonce
            )
            val dtoResponse = loginApi.verifyLogin(request).apply {

            }

        }



    suspend fun sendTransaction(request: DtoSendTransactionRequest) = safeCall {
        val response = transactionApi.sendTransaction(request)
        response.isSuccessful
    }
}