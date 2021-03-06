package com.nft.maker.ui.setting.changewallet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.nft.maker.R
import com.nft.maker.extensions.observeResultFlow
import com.nft.maker.extensions.popBack
import com.nft.maker.extensions.showKeyboard
import com.nft.maker.databinding.DialogChangeNameBinding
import com.nft.maker.ui.base.BaseBottomSheetDialogFragment
import com.nft.maker.ui.setting.SettingsViewModel
import com.nft.maker.util.Helpers


class ChangeEmailBottomSheetDialog : BaseBottomSheetDialogFragment() {
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
        binding.editName.hint = getString(R.string.email_example)
        binding.textTitle.text = getString(R.string.setting_change_email)
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
            val newEmail = binding.editName.text.toString()
            if (newEmail == currentEmail)
            {
                Toast.makeText(
                    requireContext(),
                    getString(R.string.email_error_same),
                    Toast.LENGTH_SHORT
                ).show()
            }
            else {
                if (Helpers.checkEmailPhone(newEmail, usingEmail = true)) {
                    val bundle = viewModel.checkShouldChangeEmail(newEmail, currentPhone)
                    viewModel.settingFragment?.binding?.csivEmail?.setValue(newEmail)
                    //if bundle == null that means that email is not the primary OTP method so we can just change it
                    if (bundle == null) {
                        popBack()
                    } else {
                        findNavController().navigate(R.id.toOtpFromEmail, bundle)
                    }
                } else {
                    Toast.makeText(
                        requireContext(),
                        getString(R.string.email_error),
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
                binding.editName.setText(it.email)
                currentPhone = it.phone
                currentEmail = it.email
            }, errorHandler = {
                Toast.makeText(requireContext(), it?.message.toString(), Toast.LENGTH_SHORT)
                    .show()
            })
    }
}