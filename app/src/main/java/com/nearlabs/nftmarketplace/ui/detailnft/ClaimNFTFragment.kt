package com.nearlabs.nftmarketplace.ui.detailnft

import android.os.Bundle
import android.view.View
import com.nearlabs.nftmarketplace.R
import com.nearlabs.nftmarketplace.common.extensions.viewBinding
import com.nearlabs.nftmarketplace.databinding.FragmentClaimNftBinding
import com.nearlabs.nftmarketplace.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ClaimNFTFragment : BaseFragment(R.layout.fragment_claim_nft) {
    private val binding by viewBinding(FragmentClaimNftBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListeners()
        initObserve()
    }

    private fun initListeners() {
        binding.nftClaimButton.setOnClickListener {
            // TODO AppsFlyer 10
        }
    }

    private fun initObserve() {}
}