package com.nearlabs.nftmarketplace.ui.detailnft

import android.os.Bundle
import android.view.View
import com.nearlabs.nftmarketplace.R
import com.nearlabs.nftmarketplace.common.extensions.popBack
import com.nearlabs.nftmarketplace.common.extensions.viewBinding
import com.nearlabs.nftmarketplace.databinding.FragmentSendNftBinding
import com.nearlabs.nftmarketplace.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SendNFTFragment : BaseFragment(R.layout.fragment_send_nft) {
    private val binding by viewBinding(FragmentSendNftBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListeners()
        initObserve()
    }

    private fun initListeners() {
        binding.btnClose.setOnClickListener { popBack() }
        binding.nftSendButton.setOnClickListener {
            // TODO AppsFlyer 16
        }
    }

    private fun initObserve() {}
}