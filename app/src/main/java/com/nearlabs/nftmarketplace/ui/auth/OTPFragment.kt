package com.nearlabs.nftmarketplace.ui.auth

import android.content.res.ColorStateList
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.nearlabs.nftmarketplace.R
import com.nearlabs.nftmarketplace.common.extensions.observeResultFlow
import com.nearlabs.nftmarketplace.common.extensions.viewBinding
import com.nearlabs.nftmarketplace.databinding.FragmentLoginBinding
import com.nearlabs.nftmarketplace.databinding.FragmentOtpBinding
import com.nearlabs.nftmarketplace.ui.base.BaseFragment
import com.nearlabs.nftmarketplace.util.AppConstants
import com.nearlabs.nftmarketplace.util.AppConstants.OTP_VERIFICATION_EVENT_NAME
import com.nearlabs.nftmarketplace.viewmodel.UserViewModel
import kotlin.text.StringBuilder

class OTPFragment: BaseFragment(R.layout.fragment_otp) {

    private val binding by viewBinding(FragmentOtpBinding::bind)
    private val userViewModel: UserViewModel by activityViewModels()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if(userViewModel.loginType == "email"){
            binding.sentCodeText.text = requireActivity().getString(R.string.sent_code_email)
        }else{
            binding.sentCodeText.text = requireActivity().getString(R.string.sent_code_phone)
        }

        initListeners()
    }

    private fun initListeners() {
        binding.edt1.doAfterTextChanged {
            binding.edt2.requestFocus()
        }
        binding.edt2.doAfterTextChanged {
            binding.edt3.requestFocus()
        }
        binding.edt3.doAfterTextChanged {
            binding.edt4.requestFocus()
        }
        binding.edt4.doAfterTextChanged {
            binding.edt5.requestFocus()
        }
        binding.edt5.doAfterTextChanged {
            binding.edt6.requestFocus()
        }
        binding.edt6.doAfterTextChanged {
            binding.edt6.clearFocus()
            binding.btnContinue.backgroundTintList = ColorStateList.valueOf(
                ContextCompat.getColor(
                    requireContext(),
                    if (it.isNullOrBlank()) R.color.btndisabled_color else R.color.blue
                )
            )
        }
        binding.btnContinue.setOnClickListener {
            AppConstants.logAppsFlyerEvent(OTP_VERIFICATION_EVENT_NAME,it.context)
            val nonce = StringBuilder()
                .append(binding.edt1.text.toString())
                .append(binding.edt2.text.toString())
                .append(binding.edt3.text.toString())
                .append(binding.edt4.text.toString())
                .append(binding.edt5.text.toString())
                .append(binding.edt6.text.toString())
                .toString()
            observeResultFlow(
                userViewModel.verifyUser(
                    userViewModel.walletName,
                    nonce
                ), successHandler = {
                    findNavController().navigate(R.id.toMain)
                }, errorHandler = {
                    Toast.makeText(requireContext(), it?.message.toString(), Toast.LENGTH_SHORT)
                        .show()
                })
        }

        binding.closeSignup.setOnClickListener(View.OnClickListener {
            findNavController().navigate(R.id.toAuth)
        })

        binding.resendCodeText.setOnClickListener {
            userViewModel.loginUser(userViewModel.walletName)
            Toast.makeText(requireContext(), getString(R.string.resend_code_confirmation), Toast.LENGTH_SHORT).show()
        }
    }
}