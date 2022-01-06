package com.nearlabs.nftmarketplace.ui.detailnft

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.nearlabs.nftmarketplace.R
import com.nearlabs.nftmarketplace.common.extensions.popBack
import com.nearlabs.nftmarketplace.databinding.DialogClaimNftConnectWalletBinding
import com.nearlabs.nftmarketplace.ui.base.BaseBottomSheetDialogFragment
import com.nearlabs.nftmarketplace.util.AppConstants
import com.nearlabs.nftmarketplace.util.AppConstants.CLAIM_NFT_CREATE_NEAR_WALLET_ACCOUNT
import com.nearlabs.nftmarketplace.util.AppConstants.CLAIM_NFT_LOGIN_WITH_NEAR_WALLET_EVENT_NAME


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
            AppConstants.logAppsFlyerEvent(CLAIM_NFT_CREATE_NEAR_WALLET_ACCOUNT,it.context)
        }

        binding.btnLogin.setOnClickListener {
            AppConstants.logAppsFlyerEvent(CLAIM_NFT_LOGIN_WITH_NEAR_WALLET_EVENT_NAME,it.context)
        }
    }
}