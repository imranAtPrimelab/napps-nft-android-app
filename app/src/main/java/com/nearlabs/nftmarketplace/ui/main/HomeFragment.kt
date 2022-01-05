package com.nearlabs.nftmarketplace.ui.main

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.nearlabs.nftmarketplace.R
import com.nearlabs.nftmarketplace.common.extensions.viewBinding
import com.nearlabs.nftmarketplace.databinding.FragmentHomeBinding
import com.nearlabs.nftmarketplace.ui.base.BaseFragment

class HomeFragment: BaseFragment(R.layout.fragment_home) {

    private val binding by viewBinding (FragmentHomeBinding::bind )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.username.setOnClickListener{
            findNavController().navigate(R.id.nav_setting)
        }

        binding.seeAllNFTs.setOnClickListener {
            findNavController().navigate(R.id.toMyNFTs)
        }
    }
}