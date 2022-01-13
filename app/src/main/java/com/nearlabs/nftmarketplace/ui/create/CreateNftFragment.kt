package com.nearlabs.nftmarketplace.ui.create

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.res.ColorStateList
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.nearlabs.nftmarketplace.R
import com.nearlabs.nftmarketplace.common.extensions.observeResultFlow
import com.nearlabs.nftmarketplace.common.extensions.viewBinding
import com.nearlabs.nftmarketplace.databinding.FragmentCreateNftBinding
import com.nearlabs.nftmarketplace.ui.base.BaseBottomSheetDialogFragment
import com.nearlabs.nftmarketplace.util.AppConstants
import com.nearlabs.nftmarketplace.util.AppConstants.CREATE_NFT_NEXT_BUTTON_EVENT_NAME
import com.nearlabs.nftmarketplace.util.adapters.NFTPropertiesAdapter
import com.nearlabs.nftmarketplace.viewmodel.CreateNftViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import me.rosuh.filepicker.config.FilePickerManager
import timber.log.Timber
import java.io.File

@AndroidEntryPoint
class CreateNftFragment : BaseBottomSheetDialogFragment() {

    private val binding by viewBinding(FragmentCreateNftBinding::bind)
    private val viewModel by activityViewModels<CreateNftViewModel>()
    private var selectedFile: File? = null
    private var isTitleAdded: Boolean = false
    private var isDecAdded: Boolean = false
    private val checkValidation: Boolean
        get() = selectedFile != null && isTitleAdded && isDecAdded
    private var size = 0
    private var container = mutableListOf<Int>(size)
    private val propertiesAdapter by lazy{
        return@lazy NFTPropertiesAdapter(requireContext(), container)
    }


    private val requestPermissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()) { granted ->
        if (granted) {
            FilePickerManager
                .from(this)
                .maxSelectable(1)
                .showCheckBox(false)
                .forResult(FilePickerManager.REQUEST_CODE)
        }
    }

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
        manageNextButtonEnable(checkValidation)
        viewModel.clearStep()
        binding.rootUpload.root.visibility = View.VISIBLE
        binding.rootPreview.root.visibility = View.GONE
        setFullHeight()
        binding.rootUpload.layoutProperties.adapter = propertiesAdapter

    }

    private fun initListeners() {
        binding.btnAction.setOnClickListener {
            when (viewModel.currentStep) {
                CreateNftViewModel.STEP_UPLOAD -> {
                    AppConstants.logAppsFlyerEvent(
                        CREATE_NFT_NEXT_BUTTON_EVENT_NAME,
                        it.context
                    )
                    viewModel.nextStep()
                }
                CreateNftViewModel.STEP_PREVIEW -> {
                    selectedFile?.let { file ->
                        handleActionButtonVisibility(false)
                        observeResultFlow(
                            viewModel.createNft(
                                file,
                                binding.rootUpload.titleEditText.text.toString(),
                                binding.rootUpload.descriptionEditText.text.toString(),
                                propertiesAdapter.getProperties()[0].values.toList(),
                                propertiesAdapter.getProperties()[1].values.toList()
                            ), successHandler = {
                                handleActionButtonVisibility(true)
                                viewModel.nextStep()
                            }, errorHandler = {
                                handleActionButtonVisibility(true)
                                Toast.makeText(requireContext(), it?.message.toString(), Toast.LENGTH_SHORT).show()
                            }
                        )
                    } ?: run {
                        Toast.makeText(requireContext(), "Please select file!", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
        binding.btnClose.setOnClickListener {
            dismiss()
        }
        binding.rootUpload.selectFileButtonView.setOnClickListener {
            requestPermissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
        }
        viewModel.userNameObservable.observeForever {
            binding.rootPreview.tvAuthor.text = it
        }
        binding.rootUpload.titleEditText.doAfterTextChanged {
            isTitleAdded = it.toString().isNotEmpty()
            manageNextButtonEnable(checkValidation)
        }
        binding.rootUpload.descriptionEditText.doAfterTextChanged {
            isDecAdded = it.toString().isNotEmpty()
            manageNextButtonEnable(checkValidation)
        }
        binding.rootUpload.addMore.setOnClickListener{
            container.add(size++)
            propertiesAdapter.notifyItemChanged(size)
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
            if (step == CreateNftViewModel.STEP_PREVIEW) {
                binding.rootPreview.tvNftName.text = binding.rootUpload.titleEditText.text.toString()
                View.VISIBLE
            } else {
                View.GONE
            }

        if (viewModel.isFinalStep()) {
            dismiss()
            try {
                requireParentFragment().findNavController().navigate(R.id.toNftMintedSheetDialog)
            } catch (notBottomFlow : Exception){
                requireParentFragment().findNavController().navigate(R.id.toMain)
            }
        }
    }

    private fun handleActionButtonVisibility(isVisible: Boolean) {
        if (isVisible) {
            binding.btnAction.visibility = View.VISIBLE
            binding.progressBar.visibility = View.GONE
        } else {
            binding.btnAction.visibility = View.GONE
            binding.progressBar.visibility = View.VISIBLE
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            FilePickerManager.REQUEST_CODE -> {
                if (resultCode == Activity.RESULT_OK) {
                    val list = FilePickerManager.obtainData()
                    list.firstOrNull()?.let { filePath ->
                        Timber.i("File %s", filePath)
                        binding.rootUpload.selectedFilePath.text = filePath
                        selectedFile = File(filePath)
                        Glide.with(requireContext())
                            .load(selectedFile)
                            .into(binding.rootPreview.ivThumbnail)
                        manageNextButtonEnable(checkValidation)
                    }
                } else {
                    Toast.makeText(requireActivity(), "You didn't choose anything~", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun manageNextButtonEnable(isEnable: Boolean) {
        binding.btnAction.isEnabled = isEnable
        binding.btnAction.isClickable = isEnable
        binding.btnAction.backgroundTintList = ColorStateList.valueOf(
            ContextCompat.getColor(
                requireContext(),
                if (isEnable) R.color.blue else R.color.btndisabled_color
            )
        )
    }
}