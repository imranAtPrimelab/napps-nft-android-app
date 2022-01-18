package com.nft.maker.ui.auth

import android.content.res.ColorStateList
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.nft.maker.R
import com.nft.maker.extensions.observeResultFlow
import com.nft.maker.extensions.popBackTo
import com.nft.maker.extensions.viewBinding
import com.nft.maker.databinding.FragmentOtpBinding
import com.nft.maker.ui.base.BaseFragment
import com.nft.maker.util.AppConstants
import com.nft.maker.util.AppConstants.OTP_VERIFICATION_EVENT_NAME
import com.nft.maker.viewmodel.NFTViewModel
import com.nft.maker.viewmodel.UserViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OTPFragment : BaseFragment(R.layout.fragment_otp) {

    private val binding by viewBinding(FragmentOtpBinding::bind)
    private val userViewModel: UserViewModel by activityViewModels()
    private val nftViewModel by activityViewModels<NFTViewModel>()
    private var fromSettings = false
    private var firstVerificationDone = false
    private var id = ""
    private var loginType = ""
    companion object {
        const val LOGIN_TYPE = "login_type"
        const val FROM_SETTINGS = "from_settings"
        const val PHONE = "phone"
        const val EMAIL = "email"
        const val ID = "id"
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loginType = arguments?.getString(LOGIN_TYPE) ?: "phone"
        fromSettings = arguments?.getBoolean(FROM_SETTINGS) ?: false
        userViewModel.currentEmail = arguments?.getString(EMAIL) ?: ""
        userViewModel.currentPhone = arguments?.getString(PHONE) ?: ""
        id = arguments?.getString(id) ?: ""
        if (loginType == "email") {
            if (fromSettings)
            {
                binding.sentCodeText.text = requireActivity().getString(R.string.sent_code_email_current)
            }
            else {
                binding.sentCodeText.text = requireActivity().getString(R.string.sent_code_email)
            }
        } else {
            if (fromSettings)
            {
                binding.sentCodeText.text = requireActivity().getString(R.string.sent_code_phone_current)
            }
            else {
                binding.sentCodeText.text = requireActivity().getString(R.string.sent_code_phone)
            }
        }
        activity?.findViewById<BottomNavigationView>(R.id.bottom_nav_bar)?.visibility = View.GONE
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
            AppConstants.logAppsFlyerEvent(OTP_VERIFICATION_EVENT_NAME, it.context)
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
                    if (fromSettings)
                    {
                        if (!firstVerificationDone)
                        {
                            observeResultFlow(
                                userViewModel.updateUser(id
                                ), successHandler = {
                                    userViewModel.loginUser(userViewModel.walletName)
                                    firstVerificationDone = true
                                    binding.edt1.text?.clear()
                                    binding.edt2.text?.clear()
                                    binding.edt3.text?.clear()
                                    binding.edt4.text?.clear()
                                    binding.edt5.text?.clear()
                                    binding.edt6.text?.clear()
                                    binding.edt1.requestFocus()
                                    if (loginType == "phone")
                                    {
                                        Toast.makeText(
                                            requireContext(),
                                            getString(R.string.sent_code_phone_new),
                                            Toast.LENGTH_SHORT
                                        )
                                            .show()
                                        binding.sentCodeText.text = requireActivity().getString(R.string.sent_code_phone_new)

                                    }
                                    else {
                                        Toast.makeText(
                                            requireContext(),
                                            getString(R.string.sent_code_email_new),
                                            Toast.LENGTH_SHORT
                                        )
                                            .show()
                                        binding.sentCodeText.text = requireActivity().getString(R.string.sent_code_email_new)

                                    }
                                })
                        }
                        else {
                            popBackTo(R.id.settingFragment)
                        }
                    }
                    else {
                        observeResultFlow(
                            nftViewModel.getAllNFTCollection(),
                            successHandler = { nftList ->
                                if(nftList.isEmpty()){
                                    findNavController().navigate(R.id.otpToGift)
                                }else{
                                    findNavController().navigate(R.id.toMain)
                                }
                            })
                    }
                }, errorHandler = {
                    Toast.makeText(requireContext(), it?.message.toString(), Toast.LENGTH_SHORT)
                        .show()
                })
        }

        binding.closeSignup.setOnClickListener(View.OnClickListener {
            if (fromSettings)
            {
                findNavController().navigate(R.id.toSettings)
            }
            else {
                findNavController().navigate(R.id.toAuth)
            }
        })

        binding.resendCodeText.setOnClickListener {
            userViewModel.loginUser(userViewModel.walletName)
            Toast.makeText(requireContext(), getString(R.string.resend_code_confirmation), Toast.LENGTH_SHORT).show()
        }
    }
}