package com.nearlabs.nftmarketplace.ui.base

import androidx.lifecycle.lifecycleScope
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.nearlabs.nftmarketplace.R
import com.nearlabs.nftmarketplace.common.extensions.ErrorHandler
import com.nearlabs.nftmarketplace.common.extensions.State
import com.nearlabs.nftmarketplace.common.extensions.SuccessHandler
import com.nearlabs.nftmarketplace.common.extensions.collect
import kotlinx.coroutines.flow.StateFlow

abstract class BaseBottomSheetDialogFragment : BottomSheetDialogFragment() {
    override fun getTheme() = R.style.BottomSheetDialog

    protected fun <T> observeResultFlow(
        stateFlow: StateFlow<State<T>>,
        errorHandler: ErrorHandler = { },
        successHandler: SuccessHandler<T>
    ) {
        lifecycleScope.collect(
            stateFlow,
            successHandler = { result ->
                result?.let {
                    successHandler.invoke(it)
                }
            },
            errorHandler = {
                errorHandler.invoke(it)
            },
            httpErrorHandler = {

            },
            loadingHandler = {

            }
        )
    }
}