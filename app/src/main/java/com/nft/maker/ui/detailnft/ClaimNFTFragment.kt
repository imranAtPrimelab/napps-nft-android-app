package com.nft.maker.ui.detailnft

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.nft.maker.R
import com.nft.maker.extensions.observeResultFlow
import com.nft.maker.extensions.popBack
import com.nft.maker.extensions.viewBinding
import com.nft.maker.databinding.FragmentClaimNftBinding
import com.nft.maker.model.nft.NFT
import com.nft.maker.ui.base.BaseFragment
import com.nft.maker.util.AppConstants
import com.nft.maker.util.AppConstants.CLAIM_NFT_EVENT_NAME
import com.nft.maker.viewmodel.NFTViewModel
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
    private var claimNftInfo: NFT? = null

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
                claimNftInfo = it
                setUpNftDetails(it)
            })
    }

    private fun setUpNftDetails(nft: NFT) {
        binding.sendNftTitle.text = nft.name
        binding.sendNftDescription.text = nft.description
        binding.sendNftId.text = nft.id
        binding.nftCreatorName.text = nft.owner?.name
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