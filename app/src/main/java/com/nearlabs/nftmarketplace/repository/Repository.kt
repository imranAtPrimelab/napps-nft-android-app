package com.nearlabs.nftmarketplace.repository

import com.google.gson.Gson
import com.nearlabs.nftmarketplace.common.extensions.getMimeType
import com.nearlabs.nftmarketplace.common.extensions.safeCall
import com.nearlabs.nftmarketplace.common.extensions.safeCallWithHttpError
import com.nearlabs.nftmarketplace.data.localcontact.ContactSource
import com.nearlabs.nftmarketplace.data.networks.*
import com.nearlabs.nftmarketplace.data.networks.request.*
import com.nearlabs.nftmarketplace.data.preference.SharePrefs
import com.nearlabs.nftmarketplace.domain.model.Contact
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

    suspend fun createUser(name: String, walletId: String, phone: String, email: String) =
        safeCallWithHttpError {
            var redirectUrl = sharePrefs.redirectedUrl
            var nftId = redirectUrl.replace("https://dev.nftmakerapp.io/nft/detail/claim/", "")
            if (redirectUrl == null || redirectUrl.equals("") || redirectUrl.equals("null")) {
                val request = DtoUserCreateRequest(
                    fullName = name,
                    walletName = walletId,
                    phone = phone,
                    email = email,
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

            } else {
                val request = DtoUserCreateNFTRequest(
                    fullName = name,
                    walletName = walletId,
                    phone = phone,
                    email = email,
                    nftID = nftId
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