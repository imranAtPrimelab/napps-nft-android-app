package com.nearlabs.nftmarketplace.ui.create

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.nearlabs.nftmarketplace.R
import com.nearlabs.nftmarketplace.common.extensions.observeResultFlow
import com.nearlabs.nftmarketplace.common.extensions.popBack
import com.nearlabs.nftmarketplace.common.extensions.showKeyboard
import com.nearlabs.nftmarketplace.databinding.DialogChangeNameBinding
import com.nearlabs.nftmarketplace.databinding.FragmentNftMintedBinding
import com.nearlabs.nftmarketplace.ui.base.BaseBottomSheetDialogFragment
import com.nearlabs.nftmarketplace.ui.setting.SettingsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NftMintedSheetDialog : BaseBottomSheetDialogFragment() {
    override fun getTheme() = R.style.BottomSheetTransparentDialog

    private lateinit var binding: FragmentNftMintedBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dialog?.setCancelable(false)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentNftMintedBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        initListeners()
    }

    private fun initViews() {

    }

    private fun initListeners() {

    }

}