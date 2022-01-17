package com.nearlabs.nftmarketplace.ui.main

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.NavOptions
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.nearlabs.nftmarketplace.R
import com.nearlabs.nftmarketplace.common.extensions.observeResultFlow
import com.nearlabs.nftmarketplace.common.extensions.viewBinding
import com.nearlabs.nftmarketplace.databinding.FragmentMainBinding
import com.nearlabs.nftmarketplace.ui.base.BaseFragment
import com.nearlabs.nftmarketplace.ui.detailnft.ClaimNFTFragment
import com.nearlabs.nftmarketplace.ui.sendNFTDialog.SendNFTViewModel
import com.nearlabs.nftmarketplace.viewmodel.NFTViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : BaseFragment(R.layout.fragment_main) {

    val binding by viewBinding(FragmentMainBinding::bind)
    private val viewModel by activityViewModels<SendNFTViewModel>()
    private val nftViewModel by activityViewModels<NFTViewModel>()


    override fun onCreate(savedInstanceState: Bundle?) {
        val navOptions = NavOptions.Builder()
            .setPopUpTo(R.id.mainFragment, true)
            .build()
        observeResultFlow(
            nftViewModel.getAllNFTCollection(),
            successHandler = { nftList ->
                val claimNFTID = arguments?.getString(ClaimNFTFragment.CLIM_NFT_ID)
                if (claimNFTID == null && nftList.isEmpty()) {
                    findNavController().navigate(R.id.toGiftNft, savedInstanceState, navOptions)
                }
            }
        )
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val navHostFragment = childFragmentManager.findFragmentById(com.nearlabs.nftmarketplace.R.id.fragmentContainerView) as NavHostFragment
        val navController = navHostFragment.navController
        val navGraph = navController.navInflater.inflate(com.nearlabs.nftmarketplace.R.navigation.nav_bottom_nav_screens)
        navController.setGraph(navGraph, arguments)
        binding.bottomNavBar.setupWithNavController(navController)
        viewModel.bottomVisibility.observe(viewLifecycleOwner, Observer {
            binding.bottomNavBar.visibility = it
        })

    }
}