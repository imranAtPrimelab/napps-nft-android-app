package com.nearlabs.nftmarketplace.ui.setting.changewallet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.nearlabs.nftmarketplace.R
import com.nearlabs.nftmarketplace.common.extensions.observeResultFlow
import com.nearlabs.nftmarketplace.common.extensions.popBack
import com.nearlabs.nftmarketplace.common.extensions.showKeyboard
import com.nearlabs.nftmarketplace.databinding.DialogChangeNameBinding
import com.nearlabs.nftmarketplace.ui.base.BaseBottomSheetDialogFragment
import com.nearlabs.nftmarketplace.ui.setting.SettingsViewModel
import com.nearlabs.nftmarketplace.util.Helpers


class ChangePhoneBottomSheetDialog : BaseBottomSheetDialogFragment() {
    override fun getTheme() = R.style.BottomSheetTransparentDialog
    var currentPhone = ""
    var currentEmail = ""
    private lateinit var binding: DialogChangeNameBinding

    private val viewModel by activityViewModels<SettingsViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dialog?.setCancelable(false)
        initObserve()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = DialogChangeNameBinding.inflate(inflater, container, false)
        binding.editName.hint = getString(R.string.phone_example)
        binding.textTitle.text = getString(R.string.setting_change_phone)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListeners()
        requestKeyboard()
    }

    private fun initListeners() {
        binding.btnClose.setOnClickListener {
            popBack()
        }

        binding.btnAddNewWallet.setOnClickListener {
            val newPhone = binding.editName.text.toString()
            if (newPhone == currentPhone)
            {
                Toast.makeText(
                    requireContext(),
                    getString(R.string.phone_error_same),
                    Toast.LENGTH_SHORT
                ).show()
            }
            else {
                if (Helpers.checkEmailPhone(newPhone, usingEmail = false)) {
                    val bundle = viewModel.checkShouldChangePhone(newPhone, currentEmail)
                    //if bundle == null that means that phone is not the primary OTP method so we can just change it
                    if (bundle == null) {
                        popBack()
                    } else {
                        findNavController().navigate(R.id.toOtpFromPhone, bundle)
                    }
                } else {
                    Toast.makeText(
                        requireContext(),
                        getString(R.string.phone_error),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    private fun requestKeyboard() {
        binding.editName.post { showKeyboard(binding.editName) }
    }

    private fun initObserve() {
        observeResultFlow(
            viewModel.getUserProfile(), successHandler = {
                binding.editName.setText(it.phone)
                currentPhone = it.phone
                currentEmail = it.email
            }, errorHandler = {
                Toast.makeText(requireContext(), it?.message.toString(), Toast.LENGTH_SHORT)
                    .show()
            })
    }
}