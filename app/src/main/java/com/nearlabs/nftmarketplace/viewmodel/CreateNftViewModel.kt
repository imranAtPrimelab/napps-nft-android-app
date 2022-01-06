package com.nearlabs.nftmarketplace.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nearlabs.nftmarketplace.common.extensions.resultFlow
import com.nearlabs.nftmarketplace.data.networks.request.AttributesItem
import com.nearlabs.nftmarketplace.data.networks.request.NftCreateRequest
import com.nearlabs.nftmarketplace.data.networks.request.NftInformation
import com.nearlabs.nftmarketplace.data.preference.SharePrefs
import com.nearlabs.nftmarketplace.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import java.io.File
import javax.inject.Inject

@HiltViewModel
class CreateNftViewModel @Inject constructor(private val repository: Repository, private val sharePrefs: SharePrefs) : ViewModel() {

    companion object {
        const val STEP_UPLOAD = 0
        const val STEP_PREVIEW = 1
        const val STEP_FINAL = 2
    }

    var currentStep = STEP_UPLOAD
    val step = MutableStateFlow(currentStep)
    val minted = MutableStateFlow(false)
    val userNameObservable = MutableLiveData<String>()

    init {
        userNameObservable.value = sharePrefs.userName
    }

    fun nextStep() {
        currentStep = step.value + 1
        step.value = currentStep
        if (isFinalStep()) minted.value = true
    }

    fun isFinalStep() = step.value == STEP_FINAL

    fun createNft(selectedFile: File, title: String, description: String, attributeName: String, attributeValue: String) = resultFlow {
        val nftCreateRequest = NftCreateRequest(selectedFile, NftInformation(title = title, description = description))
        nftCreateRequest.nftInformation.ownerId = sharePrefs.userId
        if (attributeName.isNotEmpty() && attributeValue.isNotEmpty()) {
            nftCreateRequest.nftInformation.attributes = listOf(AttributesItem(attrName = attributeName, attrValue = attributeValue))
        }
        repository.createNft(nftCreateRequest)
    }
}