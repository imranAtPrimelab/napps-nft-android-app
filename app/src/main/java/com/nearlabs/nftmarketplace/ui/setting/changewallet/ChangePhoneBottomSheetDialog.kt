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
import com.nearlabs.nftmarketplace.databinding.DialogChangePhoneBinding
import com.nearlabs.nftmarketplace.ui.base.BaseBottomSheetDialogFragment
import com.nearlabs.nftmarketplace.ui.setting.SettingsViewModel
import com.nearlabs.nftmarketplace.util.Helpers


class ChangePhoneBottomSheetDialog : BaseBottomSheetDialogFragment() {
    override fun getTheme() = R.style.BottomSheetTransparentDialog
    var currentPhone = ""
    var currentEmail = ""
    private lateinit var binding: DialogChangePhoneBinding

    private val viewModel by activityViewModels<SettingsViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dialog?.setCancelable(false)
        initObserve()

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = DialogChangePhoneBinding.inflate(inflater, container, false)
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
        binding.ccp.registerCarrierNumberEditText(binding.editName)
        binding.btnClose.setOnClickListener {
            popBack()
        }

        binding.btnAddNewWallet.setOnClickListener {
            val newPhone = binding.ccp.fullNumber
            if (newPhone == currentPhone && !newPhone.isEmpty())
            {
                Toast.makeText(
                    requireContext(),
                    getString(R.string.phone_error_same),
                    Toast.LENGTH_SHORT
                ).show()
            }
            else if (binding.editName.text?.isEmpty() == true && currentPhone.isEmpty()) {
                viewModel.settingFragment?.binding?.csivPhone?.setValue("")
                popBack()
            }
            else {
                if (Helpers.checkEmailPhone(newPhone, usingEmail = false)) {
                    val bundle = viewModel.checkShouldChangePhone(newPhone, currentEmail)
                    viewModel.settingFragment?.binding?.csivPhone?.setValue(newPhone)
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
                if (it.phone.length >= 3) {
                    binding.editName.setText(it.phone.subSequence(3, it.phone.length))
                    val currentPhoneCode = it.phone.subSequence(0, 3).toString().toInt()
                    binding.ccp.setCountryForPhoneCode(currentPhoneCode)
                    currentPhone = binding.ccp.fullNumber
                }
                else
                {
                    binding.editName.setText("")
                    binding.ccp.setCountryForPhoneCode(1)
                    currentPhone = ""
                }
                currentEmail = it.email
            }, errorHandler = {
                Toast.makeText(requireContext(), it?.message.toString(), Toast.LENGTH_SHORT)
                    .show()
            })
    }
}