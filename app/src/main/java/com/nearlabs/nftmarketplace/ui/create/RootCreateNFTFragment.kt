package com.nearlabs.nftmarketplace.ui.create

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController


import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.nearlabs.nftmarketplace.R
import com.nearlabs.nftmarketplace.common.extensions.viewBinding
import com.nearlabs.nftmarketplace.databinding.FragmentCreateNftBinding
import com.nearlabs.nftmarketplace.databinding.FragmentSettingBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RootCreateNFTFragment : BottomSheetDialogFragment() {

    private val binding by viewBinding(FragmentCreateNftBinding::bind)
    private val navController by lazy { Navigation.findNavController(requireActivity(), R.id.nav_create_nft_host) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_create_nft, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val navGraph = navController.navInflater.inflate(R.navigation.nav_create_nft)
        navController.graph = navGraph
    }
}