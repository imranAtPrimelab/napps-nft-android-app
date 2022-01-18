package com.nft.maker.ui.detailnft

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.nft.maker.R
import com.nft.maker.extensions.observeResultFlow
import com.nft.maker.extensions.popBack
import com.nft.maker.extensions.viewBinding
import com.nft.maker.databinding.FragmentSendNftBinding
import com.nft.maker.model.nft.NFT
import com.nft.maker.ui.base.BaseFragment
import com.nft.maker.ui.base.activity.BaseActivity
import com.nft.maker.ui.sendNFTDialog.SendNFTViewModel
import com.nft.maker.util.AppConstants
import com.nft.maker.viewmodel.NFTViewModel
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import java.lang.Exception
import java.net.URL

@AndroidEntryPoint
class SendNFTFragment : BaseFragment(R.layout.fragment_send_nft) {
    private val binding by viewBinding(FragmentSendNftBinding::bind)
    private val nftViewModel: NFTViewModel by viewModels()
    private val viewModel by activityViewModels<SendNFTViewModel>()

    var nftPosition = 0
    var selectedNFt : NFT? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if(arguments!= null){
            nftPosition = requireArguments().getInt("NftPosition",0)
        }
        viewModel.bottomVisibility.value = View.GONE
        initListeners()
        initObserve()
        initViews()
    }

    private fun initListeners() {
        binding.btnClose.setOnClickListener { popBack() }
        binding.nftSendButton.setOnClickListener {
            (this.activity as BaseActivity).showProgressDialog()
            AppConstants.logAppsFlyerEvent(AppConstants.SEND_NFT_BUTTON_EVENT_NAME,it.context)
            viewModel.transactionItem = selectedNFt
            observeResultFlow(
                viewModel.getContacts(),
                successHandler = { it ->
                    if(it.isEmpty()){
                        Toast.makeText(requireContext(), "no contacts imported, please import contacts!", Toast.LENGTH_SHORT).show()
                    }else{
                        (this.activity as BaseActivity).dismissProgressDialog()
                        findNavController().navigate(R.id.toSelectPeople)
                    }
                }, errorHandler = {
                    (this.activity as BaseActivity).dismissProgressDialog()
                    Timber.e(it)
                    Toast.makeText(requireContext(), it?.message.toString(), Toast.LENGTH_SHORT).show()
                }
            )

        }
    }

    private fun initObserve() {
        (this.activity as BaseActivity).showProgressDialog()
        observeResultFlow(
            nftViewModel.getAllNFTCollection(),
            successHandler = {
                val currentNft = it[nftPosition]
                observeResultFlow(
                nftViewModel.getNftDetails(currentNft.id),
                    successHandler = { nft ->
                        (this.activity as BaseActivity).dismissProgressDialog()
                        selectedNFt = nft
                        Glide.with(requireContext()).load(URL(nft.image)).into(binding.sendNftImage);
                        if(nft.type.isEmpty()){
                            binding.category.text = "Digital Arts"
                        }else{
                            binding.category.text = nft.type
                        }
                        binding.sendNftTitle.text = nft.name
                        binding.nftCreatorName.text = nft.owner!!.name
                        binding.sendNftDescription.text = nft.description
                        binding.nftTokenId.text = nft.id
                        val attributes = HashMap<String,String>()
                        val attributesResponse = nft.attributes
                        try {
                            for (i in attributesResponse!!.indices) {
                                attributes[attributesResponse[i].get("attr_name").toString()
                                    .replace("\"", "")] =
                                    attributesResponse[i].get("attr_value").toString()
                                        .replace("\"", "")
                            }
                            binding.attributesList.adapter =
                                NftAttributesAdapter(requireContext(), attributes)
                        }catch (noAttributes : Exception){

                        }
                    }
                )

            }
        )



    }

    override fun onHiddenChanged(hidden: Boolean) {
        if(hidden) viewModel.bottomVisibility.value = View.VISIBLE
        super.onHiddenChanged(hidden)
    }

    override fun onDetach() {
        viewModel.bottomVisibility.value = View.VISIBLE
        super.onDetach()
    }


    override fun onAttach(context: Context) {
        viewModel.bottomVisibility.value = View.GONE
        super.onAttach(context)
    }


    private fun initViews(){


    }
}