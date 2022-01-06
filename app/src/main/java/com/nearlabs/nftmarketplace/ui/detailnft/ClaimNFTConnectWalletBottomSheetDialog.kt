package com.nearlabs.nftmarketplace.ui.detailnft

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.nearlabs.nftmarketplace.R
import com.nearlabs.nftmarketplace.common.extensions.popBack
import com.nearlabs.nftmarketplace.databinding.DialogClaimNftConnectWalletBinding
import com.nearlabs.nftmarketplace.ui.base.BaseBottomSheetDialogFragment


class ClaimNFTConnectWalletBottomSheetDialog : BaseBottomSheetDialogFragment() {
    override fun getTheme() = R.style.BottomSheetTransparentDialog

    private lateinit var binding: DialogClaimNftConnectWalletBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dialog?.setCancelable(false)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = DialogClaimNftConnectWalletBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListeners()
    }


    private fun initListeners() {
        binding.btnClose.setOnClickListener {
            popBack()
        }

        binding.btnCreateNewWallet.setOnClickListener {
            // TODO AppsFlyer 11

        }

        binding.btnLogin.setOnClickListener {
            // TODO AppsFlyer 12
        }
    }
}