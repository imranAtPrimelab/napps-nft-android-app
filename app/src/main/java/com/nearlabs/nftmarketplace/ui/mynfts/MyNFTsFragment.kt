package com.nearlabs.nftmarketplace.ui.mynfts

import com.nearlabs.nftmarketplace.R
import com.nearlabs.nftmarketplace.common.extensions.viewBinding
import com.nearlabs.nftmarketplace.databinding.FragmentMyNftsBinding
import com.nearlabs.nftmarketplace.ui.base.BaseFragment

class MyNFTsFragment: BaseFragment(R.layout.fragment_my_nfts) {
    val binding by viewBinding(FragmentMyNftsBinding::bind)
}