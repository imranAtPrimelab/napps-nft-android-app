package com.nft.maker.repository

import com.google.gson.Gson
import com.nft.maker.extensions.getMimeType
import com.nft.maker.extensions.safeCall
import com.nft.maker.extensions.safeCallWithHttpError
import com.nft.maker.data.localcontact.ContactSource
import com.nft.maker.data.preference.SharePrefs
import com.nft.maker.model.Contact
import com.nft.maker.model.nft.toDomainModel
import com.nft.maker.model.toDomain
import com.nft.maker.model.toDomainModel
import com.nft.maker.model.transaction.TransactionDirection
import com.nft.maker.model.transaction.toDomainModel
import com.nft.maker.data.networks.*
import com.nft.maker.data.networks.request.*
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
    private val loginApi: LoginApi,
    private val sharePrefs: SharePrefs,
    private val localContact: ContactSource
) {

    fun isLoggedIn() = sharePrefs.accessToken.isNotEmpty()

    suspend fun getContacts() = safeCall {
        val dtoContacts = contactApi.getContacts(sharePrefs.userId)
        dtoContacts.data.mapNotNull { it.toDomainModel() }
    }

    suspend fun getTransactions() = safeCall {
        val dtoTransactions = transactionApi.getTransaction(sharePrefs.userId)
        dtoTransactions.data.map { it.toDomainModel() }
    }

    suspend fun getSentTransactions() = safeCall {
        val dtoTransactions = transactionApi.getTransaction(sharePrefs.userId)
        dtoTransactions.data
            .map { it.toDomainModel() }
            .filter { it.direction == TransactionDirection.Outgoing }
    }

    suspend fun getRecvTransactions() = safeCall {
        val dtoTransactions = transactionApi.getTransaction(sharePrefs.userId)
        dtoTransactions.data
            .map { it.toDomainModel() }
            .filter { it.direction == TransactionDirection.Incoming }
    }

    suspend fun getRecentTransactions() = safeCall {
        val dtoTransactions = transactionApi.getTransaction(sharePrefs.userId).data.take(20)
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

    suspend fun getDummyWallets() = safeCall {
        DummyDataGenerator.wallets().wallets?.map { it.toDomainModel() } ?: emptyList()
    }

    suspend fun getAllNFTCollection() = safeCall {
        val dtoNft = nftApi.getAllNFTCollections(sharePrefs.userId)
        dtoNft.data.map { it.toDomainModel() }
    }

    suspend fun createUser(name: String, walletId: String, phone: String, email: String, claimNFTID: String? = null) =
        safeCallWithHttpError {
            val request = DtoUserCreateNFTRequest(
                fullName = name,
                walletName = walletId,
                phone = phone,
                email = email,
                nftID = claimNFTID
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

    suspend fun login(walletName: String) =
        safeCallWithHttpError {
            val request = DtoLoginRequest(walletName = walletName)
            val dtoResponse = loginApi.login(request).apply {
                sharePrefs.loginType = type
                sharePrefs.walletName = walletName
            }
            dtoResponse
        }

    suspend fun verifyLogin(walletName: String, nonce: String) =
        safeCall {
            val request = DtoLoginRequest(
                walletName = walletName,
                nonce = nonce
            )
            val dtoResponse = loginApi.verifyLogin(request).apply {
                sharePrefs.userId = userInfo.id
                sharePrefs.userName = userInfo.fullName ?: ""
                sharePrefs.accessToken = accessToken
                sharePrefs.idToken = idToken
                sharePrefs.refreshToken = refreshToken
                sharePrefs.userInfo = Gson().toJson(userInfo)
            }
        }


    suspend fun sendTransaction(request: DtoSendTransactionRequest) = safeCall {
        val response = transactionApi.sendTransaction(request)
        response.isSuccessful
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

    suspend fun postLocalContact(contacts: List<Contact>) = safeCall {
        val request = localContact.getAllContactWithEmail(sharePrefs.userId)
        contactApi.importContact(contacts)
    }

    suspend fun postAddLocalContact(contacts: Contact) = safeCall {
        contactApi.addContact(contacts)
    }

    suspend fun getLocalContact() = safeCall {
        localContact.getAllContactWithEmail(sharePrefs.userId)

    }

    suspend fun getNFTDetails(nftId: String) = safeCall {
        val nftDetailsResponse = nftApi.getNFTDetails(nftId)
        nftDetailsResponse.data.toDomainModel()
    }

    suspend fun claimNFT(nftId: String) = safeCallWithHttpError {
        val climNftResponse = nftApi.claimNFT(nftId, ClimNFTRequest(sharePrefs.userId))
        climNftResponse.message
    }

    suspend fun getUserProfile(userId: String) = safeCall {
        val dtoResponse = api.getUserProfile(userId)
        dtoResponse.dtoUserInfo.toDomain()
    }

    suspend fun modifyUser(userId: String, currentPhone: String, currentEmail: String) = safeCall {
        val dToUser = DtoUserCreateRequest(sharePrefs.userName, sharePrefs.walletName, currentPhone, currentEmail)
        api.modifyUser(sharePrefs.userId, dToUser)
    }
}