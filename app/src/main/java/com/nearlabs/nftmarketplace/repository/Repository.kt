package com.nearlabs.nftmarketplace.repository

import com.google.gson.Gson
import com.nearlabs.nftmarketplace.common.extensions.getMimeType
import com.nearlabs.nftmarketplace.common.extensions.safeCall
import com.nearlabs.nftmarketplace.data.networks.*
import com.nearlabs.nftmarketplace.data.networks.request.DtoUserCreateRequest
import com.nearlabs.nftmarketplace.data.networks.request.NftCreateRequest
import com.nearlabs.nftmarketplace.data.preference.SharePrefs
import com.nearlabs.nftmarketplace.domain.model.nft.toDomainModel
import com.nearlabs.nftmarketplace.domain.model.toDomain
import com.nearlabs.nftmarketplace.domain.model.toDomainModel
import com.nearlabs.nftmarketplace.domain.model.transaction.TransactionDirection
import com.nearlabs.nftmarketplace.domain.model.transaction.toDomainModel
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody

class Repository(
    private val api: Api,
    private val transactionApi: TransactionApi,
    private val contactApi: ContactApi,
    private val nftApi: NFTApi,
    private val userApi: UserApi,
    private val sharePrefs: SharePrefs
) {

    fun isLoggedIn() = sharePrefs.idToken.isNotEmpty()

    suspend fun getContacts() = safeCall {
        // TODO: need to pass owner id
        val dtoContacts = contactApi.getContacts("")
        dtoContacts.map { it.toDomainModel() }
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
                sharePrefs.userName = userInfo.fullName ?: ""
                sharePrefs.accessToken = accessToken
                sharePrefs.idToken = idToken
                sharePrefs.refreshToken = refreshToken
                sharePrefs.userInfo = Gson().toJson(userInfo)
            }
            dtoResponse.userInfo.toDomain()
        }

    suspend fun createNft(nftCreateRequest: NftCreateRequest) = safeCall {
        val filePart = MultipartBody.Part.createFormData(
            "file",
            nftCreateRequest.file.name,
            nftCreateRequest.file.asRequestBody(nftCreateRequest.file.getMimeType().toMediaTypeOrNull())
        )
        val nftInfoJsonString = Gson().toJson(nftCreateRequest.nftInformation)
        val dtoResponse = nftApi.createNft(nftInfoJsonString.toRequestBody(), filePart)
        dtoResponse.message
    }
}