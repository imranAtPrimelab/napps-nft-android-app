package com.nearlabs.nftmarketplace.ui.create

import android.os.Bundle
import android.view.View
import com.nearlabs.nftmarketplace.R
import com.nearlabs.nftmarketplace.common.extensions.viewBinding
import com.nearlabs.nftmarketplace.databinding.FragmentCreateNftBinding
import com.nearlabs.nftmarketplace.databinding.FragmentCreateNftPreviewBinding
import com.nearlabs.nftmarketplace.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CreateNFTPreview : BaseFragment(R.layout.fragment_create_nft_preview)  {

    private val binding by viewBinding(FragmentCreateNftPreviewBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //TO-DO
    }



}