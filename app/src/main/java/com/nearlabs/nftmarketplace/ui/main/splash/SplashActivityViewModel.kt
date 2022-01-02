package com.nearlabs.nftmarketplace.ui.main.splash

import androidx.lifecycle.ViewModel
import com.nearlabs.nftmarketplace.networks.State
import com.nearlabs.nftmarketplace.repository.OurUserCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

@HiltViewModel
class SplashActivityViewModel @Inject constructor(private val sampleUseCase: OurUserCases):ViewModel(){

    fun getUserLoginSate() = flow {
        emit(State.LoadingState)
        try {
            delay(1000)
            emit(State.DataState(sampleUseCase()))
        } catch (e: Exception) {
            e.printStackTrace()
//            emit(Utils.resolveError(e))e
        }
    }
}

