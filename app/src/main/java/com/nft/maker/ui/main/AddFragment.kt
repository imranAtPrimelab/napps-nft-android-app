package com.nft.maker.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.nft.maker.R
import com.nft.maker.extensions.observeResultFlow
import com.nft.maker.databinding.FragmentAddBinding
import com.nft.maker.ui.base.BaseBottomSheetDialogFragment
import com.nft.maker.ui.base.activity.BaseActivity
import com.nft.maker.ui.sendNFTDialog.SendNFTViewModel
import com.nft.maker.util.AppConstants
import com.nft.maker.util.AppConstants.DASHBOARD_CREATE_NFT_EVENT_NAME
import com.nft.maker.util.AppConstants.DASHBOARD_SEND_NFT_EVENT_NAME

class AddFragment : BaseBottomSheetDialogFragment() {
    override fun getTheme() = R.style.BottomSheetTransparentDialog
    private val viewModel by activityViewModels<SendNFTViewModel>()

    private lateinit var binding: FragmentAddBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dialog?.setCancelable(false)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListeners()
    }


    private fun initListeners() {
        binding.btnCreateNft.setOnClickListener {
            AppConstants.logAppsFlyerEvent(DASHBOARD_CREATE_NFT_EVENT_NAME,it.context)
            findNavController().navigate(R.id.toCreateNft)
        }

        binding.btnSendNft.setOnClickListener {
            (this.activity as BaseActivity).showProgressDialog()
            observeResultFlow(viewModel.getNFT(),
                successHandler = {
                    (this.activity as BaseActivity).dismissProgressDialog()
                    if(it.size > 0){
                        AppConstants.logAppsFlyerEvent(DASHBOARD_SEND_NFT_EVENT_NAME,requireContext())
                        findNavController().navigate(R.id.sendNFTBottomSheetDialog)
                    } else {
                        Toast.makeText(requireContext(), "You don't have any NFTs in your account.", Toast.LENGTH_SHORT).show()
                    }
                },
                errorHandler = {
                    (this.activity as BaseActivity).dismissProgressDialog()
                    Toast.makeText(requireContext(), "Server error, error = "+it?.localizedMessage, Toast.LENGTH_SHORT).show()
                }
            )
        }
    }
}