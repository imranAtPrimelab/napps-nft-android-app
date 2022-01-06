package com.nearlabs.nftmarketplace.viewmodel

import androidx.lifecycle.ViewModel
import com.nearlabs.nftmarketplace.common.extensions.resultFlow
import com.nearlabs.nftmarketplace.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class CreateNftViewModel @Inject constructor(private val repository: Repository) : ViewModel() {

    companion object {
        const val STEP_UPLOAD = 0
        const val STEP_PREVIEW = 1
        const val STEP_FINAL = 2
    }

    var currentStep = STEP_UPLOAD
    val step = MutableStateFlow(currentStep)
    val minted = MutableStateFlow(false)

    fun nextStep() {
        currentStep = step.value + 1
        step.value = currentStep
        if (isFinalStep()) minted.value = true
    }

    fun isFinalStep() = step.value == STEP_FINAL
}