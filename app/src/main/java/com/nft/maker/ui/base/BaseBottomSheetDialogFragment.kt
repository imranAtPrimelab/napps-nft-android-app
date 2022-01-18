package com.nft.maker.ui.base

import android.content.res.Resources
import android.view.View
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.nft.maker.R

abstract class BaseBottomSheetDialogFragment : BottomSheetDialogFragment() {
    override fun getTheme() = R.style.BottomSheetDialog

    protected fun setFullHeight() {
        val bottomSheetInternal = dialog?.findViewById<View>(R.id.design_bottom_sheet)
        bottomSheetInternal?.let {
            BottomSheetBehavior.from(bottomSheetInternal).run {
                peekHeight = Resources.getSystem().displayMetrics.heightPixels
                state = BottomSheetBehavior.STATE_EXPANDED
            }
        }
    }
}