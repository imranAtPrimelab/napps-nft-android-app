package com.nearlabs.nftmarketplace.ui.auth

import android.content.res.ColorStateList
import android.os.Bundle
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

        initListeners()
    }

    private fun initListeners() {
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
            AppConstants.logAppsFlyerEvent(GET_STARTED_EVENT_NAME,it.context)
            userViewModel.currentPhone = binding.etEmailPhone.text.toString()
            userViewModel.currentEmail = binding.etEmailPhone.text.toString()
            findNavController().navigate(R.id.signupFragment)
        }

        binding.tvPhoneLogin.setOnClickListener {
            AppConstants.logAppsFlyerEvent(CLICK_LOGIN_WITH_PHONE_EVENT_NAME,it.context)
            binding.etEmailPhone.hint = requireActivity().getString(R.string.phone_example)
        }

        binding.tvEmailLogin.setOnClickListener {
            AppConstants.logAppsFlyerEvent(CLICK_LOGIN_WITH_PHONE_EVENT_NAME,it.context)
            binding.etEmailPhone.hint = requireActivity().getString(R.string.email_example)

        }

        binding.etEmailPhone.doAfterTextChanged {
            binding.btnGetStarted.backgroundTintList = ColorStateList.valueOf(
                ContextCompat.getColor(
                    requireContext(),
                    if (it.isNullOrBlank()) R.color.btndisabled_color else R.color.blue
                )
            )
        }
    }
}