package com.nft.maker.ui.main

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.NavOptions
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.nft.maker.R
import com.nft.maker.extensions.observeResultFlow
import com.nft.maker.extensions.viewBinding
import com.nft.maker.databinding.FragmentMainBinding
import com.nft.maker.ui.base.BaseFragment
import com.nft.maker.ui.detailnft.ClaimNFTFragment
import com.nft.maker.ui.sendNFTDialog.SendNFTViewModel
import com.nft.maker.viewmodel.NFTViewModel
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

        val navHostFragment = childFragmentManager.findFragmentById(com.nft.maker.R.id.fragmentContainerView) as NavHostFragment
        val navController = navHostFragment.navController
        val navGraph = navController.navInflater.inflate(com.nft.maker.R.navigation.nav_bottom_nav_screens)
        navController.setGraph(navGraph, arguments)
        binding.bottomNavBar.setupWithNavController(navController)
        viewModel.bottomVisibility.observe(viewLifecycleOwner, Observer {
            binding.bottomNavBar.visibility = it
        })

    }
}