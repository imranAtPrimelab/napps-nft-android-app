package com.nearlabs.nftmarketplace.ui.detailnft

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.nearlabs.nftmarketplace.R
import com.nearlabs.nftmarketplace.common.extensions.observeResultFlow
import com.nearlabs.nftmarketplace.common.extensions.popBack
import com.nearlabs.nftmarketplace.common.extensions.viewBinding
import com.nearlabs.nftmarketplace.databinding.FragmentClaimNftBinding
import com.nearlabs.nftmarketplace.domain.model.nft.NFT
import com.nearlabs.nftmarketplace.ui.base.BaseFragment
import com.nearlabs.nftmarketplace.util.AppConstants
import com.nearlabs.nftmarketplace.util.AppConstants.CLAIM_NFT_EVENT_NAME
import com.nearlabs.nftmarketplace.viewmodel.NFTViewModel
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class ClaimNFTFragment : BaseFragment(R.layout.fragment_claim_nft) {
    companion object {
        const val CLIM_NFT_ID = "nftId"
    }

    private val binding by viewBinding(FragmentClaimNftBinding::bind)
    private val nftViewModel: NFTViewModel by viewModels()
    private lateinit var nftId: String

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        nftId = arguments?.getString(CLIM_NFT_ID) ?: return
        initListeners()
        initObserve()
    }

    private fun initListeners() {
        binding.nftClaimButton.setOnClickListener {
            AppConstants.logAppsFlyerEvent(CLAIM_NFT_EVENT_NAME, it.context)
            claimNft()
        }

        binding.close.setOnClickListener {
            popBack()
        }
    }

    private fun initObserve() {
        observeResultFlow(
            nftViewModel.getNftDetails(nftId),
            successHandler = {
                loadNftCreatorDetail(it.author.name)
                setUpNftDetails(it)
            })
    }

    private fun loadNftCreatorDetail(userId: String) {
        observeResultFlow(
            nftViewModel.getNftCreator(userId),
            successHandler = {
                binding.nftCreatorName.text = it.name
            })
    }

    private fun setUpNftDetails(nft: NFT) {
        binding.sendNftTitle.text = nft.name
        binding.sendNftDescription.text = nft.description
        binding.sendNftId.text = nft.id
    }

    private fun claimNft() {
        observeResultFlow(
            nftViewModel.claimNft(nftId),
            successHandler = {
                Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
                popBack()
            }, errorHandler = {
                Toast.makeText(requireContext(), it?.message.toString(), Toast.LENGTH_SHORT).show()
            }, httpErrorHandler = {
                Timber.e(it.toString())
                Toast.makeText(requireContext(), it?.message.toString(), Toast.LENGTH_SHORT).show()
            })
    }
}