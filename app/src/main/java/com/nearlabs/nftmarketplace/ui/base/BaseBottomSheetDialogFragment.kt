package com.nearlabs.nftmarketplace.ui.base

import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.nearlabs.nftmarketplace.R

abstract class BaseBottomSheetDialogFragment : BottomSheetDialogFragment() {
    override fun getTheme() = R.style.BottomSheetDialog
}