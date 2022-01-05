package com.nearlabs.nftmarketplace.ui.gift

import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.nearlabs.nftmarketplace.R
import com.nearlabs.nftmarketplace.common.extensions.viewBinding
import com.nearlabs.nftmarketplace.databinding.FragmentGiftNftBinding
import com.nearlabs.nftmarketplace.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class GiftFragment : BaseFragment(R.layout.fragment_gift_nft) {

    private val binding by viewBinding(FragmentGiftNftBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListeners()
    }

    private fun initListeners() {
        binding.sendGift.setOnClickListener {
            Toast.makeText(requireContext(), "Send gift", Toast.LENGTH_SHORT).show()
        }
    }
}