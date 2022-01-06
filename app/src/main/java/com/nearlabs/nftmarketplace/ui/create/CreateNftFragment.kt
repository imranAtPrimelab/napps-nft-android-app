package com.nearlabs.nftmarketplace.ui.create

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.nearlabs.nftmarketplace.R
import com.nearlabs.nftmarketplace.common.extensions.collect
import com.nearlabs.nftmarketplace.common.extensions.pagingCollect
import com.nearlabs.nftmarketplace.common.extensions.viewBinding
import com.nearlabs.nftmarketplace.databinding.FragmentCreateNftBinding
import com.nearlabs.nftmarketplace.databinding.FragmentHistoryBinding
import com.nearlabs.nftmarketplace.ui.base.BaseBottomSheetDialogFragment
import com.nearlabs.nftmarketplace.ui.base.BaseFragment
import com.nearlabs.nftmarketplace.util.AppConstants
import com.nearlabs.nftmarketplace.util.AppConstants.CREATE_NFT_NEXT_BUTTON_EVENT_NAME
import com.nearlabs.nftmarketplace.viewmodel.CreateNftViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class CreateNftFragment : BaseBottomSheetDialogFragment() {

    private val binding by viewBinding(FragmentCreateNftBinding::bind)
    private val viewModel by activityViewModels<CreateNftViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_create_nft, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        initListeners()
        initObservers()
    }

    private fun initViews() {
        setFullHeight()
    }

    private fun initListeners() {
        binding.btnAction.setOnClickListener {
            when (viewModel.currentStep) {
                CreateNftViewModel.STEP_UPLOAD -> AppConstants.logAppsFlyerEvent(
                    CREATE_NFT_NEXT_BUTTON_EVENT_NAME,
                    it.context
                )
                CreateNftViewModel.STEP_PREVIEW -> {
                    // TODO AppsFlyer 9
                }
            }
            viewModel.nextStep()
        }
        binding.btnClose.setOnClickListener {
            dismiss()
        }
    }

    private fun initObservers() {
        lifecycleScope.launchWhenStarted {
            viewModel.step.collect {
                handleStep(it)
            }
        }
    }

    private fun handleStep(step: Int) {
        binding.rootUpload.root.visibility =
            if (step == CreateNftViewModel.STEP_UPLOAD) View.VISIBLE else View.GONE
        binding.rootPreview.root.visibility =
            if (step == CreateNftViewModel.STEP_PREVIEW) View.VISIBLE else View.GONE

        if (viewModel.isFinalStep()) {
            dismiss()
        }
    }
}