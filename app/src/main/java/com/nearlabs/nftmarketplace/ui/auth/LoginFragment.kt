package com.nearlabs.nftmarketplace.ui.auth

import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.text.InputType
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
import com.nearlabs.nftmarketplace.ui.base.BaseFragment
import com.nearlabs.nftmarketplace.util.AppConstants
import com.nearlabs.nftmarketplace.util.AppConstants.CLICK_LOGIN_WITH_PHONE_EVENT_NAME
import com.nearlabs.nftmarketplace.util.AppConstants.GET_STARTED_EVENT_NAME
import com.nearlabs.nftmarketplace.util.AppConstants.LOGIN_WITH_PHONE_EVENT_NAME
import com.nearlabs.nftmarketplace.viewmodel.UserViewModel

class LoginFragment : BaseFragment(R.layout.fragment_login) {

    private val binding by viewBinding(FragmentLoginBinding::bind)
    private val userViewModel: UserViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.ccp.setCountryForPhoneCode(1)
        initListeners()
    }

    private fun initListeners() {
        binding.ccp.registerCarrierNumberEditText(binding.etEmailPhone)
        binding.btnLogin.setOnClickListener {
            AppConstants.logAppsFlyerEvent(LOGIN_WITH_PHONE_EVENT_NAME,it.context)
            observeResultFlow(
                userViewModel.loginUser(
                    binding.etEmailPhoneLogin.text.toString()
                ), successHandler = {
                    userViewModel.walletName = binding.etEmailPhoneLogin.text.toString()
                    findNavController().navigate(R.id.toOtp)
                }, errorHandler = {
                    Toast.makeText(requireContext(), it?.message.toString(), Toast.LENGTH_SHORT)
                        .show()
                })
        }
        binding.btnGetStarted.setOnClickListener {
            val usesEmail = !userViewModel.usesPhone
            if (checkEmailPhone(binding.etEmailPhone.text.toString(), usesEmail)) {
                AppConstants.logAppsFlyerEvent(GET_STARTED_EVENT_NAME, it.context)
                if (userViewModel.usesPhone) {
                    userViewModel.currentPhone = binding.ccp.fullNumber
                } else {
                    userViewModel.currentEmail = binding.etEmailPhone.text.toString()
                }
                findNavController().navigate(R.id.signupFragment)
            }
            else
            {
                if (usesEmail)
                {
                    Toast.makeText(requireContext(), getString(R.string.email_error), Toast.LENGTH_SHORT).show()
                }
                else
                {
                    Toast.makeText(requireContext(), getString(R.string.phone_error), Toast.LENGTH_SHORT).show()
                }
            }
        }

        binding.tvPhoneLogin.setOnClickListener {
            AppConstants.logAppsFlyerEvent(CLICK_LOGIN_WITH_PHONE_EVENT_NAME,it.context)
            binding.tvPhoneLogin.backgroundTintList = ColorStateList.valueOf(resources.getColor(R.color.light_grey))
            binding.tvEmailLogin.backgroundTintList = ColorStateList.valueOf(Color.WHITE)
            binding.etEmailPhone.hint = requireActivity().getString(R.string.phone_example)
            binding.etEmailPhone.inputType = InputType.TYPE_CLASS_PHONE
            userViewModel.usesPhone = true
            binding.ccp.visibility = View.VISIBLE
        }

        binding.tvEmailLogin.setOnClickListener {
            AppConstants.logAppsFlyerEvent(CLICK_LOGIN_WITH_PHONE_EVENT_NAME,it.context)
            binding.tvPhoneLogin.backgroundTintList = ColorStateList.valueOf(Color.WHITE)
            binding.tvEmailLogin.backgroundTintList = ColorStateList.valueOf(resources.getColor(R.color.light_grey))
            binding.etEmailPhone.hint = requireActivity().getString(R.string.email_example)
            binding.etEmailPhone.inputType = InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS
            userViewModel.usesPhone = false
            binding.ccp.visibility = View.GONE
        }

        binding.etEmailPhone.doAfterTextChanged {
            binding.btnGetStarted.backgroundTintList = ColorStateList.valueOf(
                ContextCompat.getColor(
                    requireContext(),
                    if (it.isNullOrBlank()) R.color.btndisabled_color else R.color.blue
                )
            )
        }

        binding.etEmailPhoneLogin.doAfterTextChanged {
            binding.btnLogin.backgroundTintList = ColorStateList.valueOf(
                ContextCompat.getColor(
                    requireContext(),
                    if (it.isNullOrBlank()) R.color.btndisabled_color else R.color.blue
                )
            )
        }

    }

    private fun checkEmailPhone(text: String, usingEmail: Boolean): Boolean {
        if (usingEmail)
        {
            val isLegitEmail = android.util.Patterns.EMAIL_ADDRESS.matcher(text).matches()
            return (!text.isBlank() && isLegitEmail)
        }
        else
        {
            val isLegitNumber = android.util.Patterns.PHONE.matcher(text).matches()
            return (!text.isBlank() && isLegitNumber)
        }
    }
}