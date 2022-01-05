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

    val step = MutableStateFlow(STEP_UPLOAD)
    val minted = MutableStateFlow(false)

    fun nextStep() {
        step.value = step.value + 1
        if (isFinalStep()) minted.value = true
    }

    fun isFinalStep() = step.value == STEP_FINAL
}