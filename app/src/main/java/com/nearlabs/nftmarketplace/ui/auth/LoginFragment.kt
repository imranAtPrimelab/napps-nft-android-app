package com.nearlabs.nftmarketplace.ui.auth

import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.text.*
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.AppCompatTextView
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

fun AppCompatTextView.makeLinks(vararg links: Pair<String, View.OnClickListener>) {
    val spannableString = SpannableString(this.text)
    var startIndexOfLink = -1
    for (link in links) {
        val clickableSpan = object : ClickableSpan() {
            override fun updateDrawState(textPaint: TextPaint) {
                textPaint.color = textPaint.linkColor
                textPaint.isUnderlineText = true
            }

            override fun onClick(view: View) {
                Selection.setSelection((view as TextView).text as Spannable, 0)
                view.invalidate()
                link.second.onClick(view)
            }
        }
        startIndexOfLink = this.text.toString().indexOf(link.first, startIndexOfLink + 1)
        spannableString.setSpan(
            clickableSpan, startIndexOfLink, startIndexOfLink + link.first.length,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )
    }
    this.movementMethod =
        LinkMovementMethod.getInstance()
    this.setText(spannableString, TextView.BufferType.SPANNABLE)
}

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
            if(userViewModel.usesPhone){
                userViewModel.currentPhone = binding.etEmailPhone.text.toString()
            }else{
                userViewModel.currentEmail = binding.etEmailPhone.text.toString()
            }
            findNavController().navigate(R.id.signupFragment)
        }

        binding.tvPhoneLogin.setOnClickListener {
            AppConstants.logAppsFlyerEvent(CLICK_LOGIN_WITH_PHONE_EVENT_NAME,it.context)
            binding.tvPhoneLogin.backgroundTintList = ColorStateList.valueOf(resources.getColor(R.color.light_grey))
            binding.tvEmailLogin.backgroundTintList = ColorStateList.valueOf(Color.WHITE)
            binding.etEmailPhone.hint = requireActivity().getString(R.string.phone_example)
            binding.etEmailPhone.inputType = InputType.TYPE_CLASS_PHONE
            userViewModel.usesPhone = true
        }

        binding.tvEmailLogin.setOnClickListener {
            AppConstants.logAppsFlyerEvent(CLICK_LOGIN_WITH_PHONE_EVENT_NAME,it.context)
            binding.tvPhoneLogin.backgroundTintList = ColorStateList.valueOf(Color.WHITE)
            binding.tvEmailLogin.backgroundTintList = ColorStateList.valueOf(resources.getColor(R.color.light_grey))
            binding.etEmailPhone.hint = requireActivity().getString(R.string.email_example)
            binding.etEmailPhone.inputType = InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS
            userViewModel.usesPhone = false

        }

        binding.etEmailPhone.doAfterTextChanged {
            binding.btnGetStarted.backgroundTintList = ColorStateList.valueOf(
                ContextCompat.getColor(
                    requireContext(),
                    if (it.isNullOrBlank()) R.color.btndisabled_color else R.color.blue
                )
            )
        }

        binding.termsText.makeLinks(Pair("Terms & Conditions",View.OnClickListener {
            val browserIntent = Intent(Intent.ACTION_VIEW)
            browserIntent.data = Uri.parse("https://terms.nftmakerapp.io/")
            this.requireActivity().startActivity(browserIntent)
        }), Pair("Privacy Policy",View.OnClickListener {
            val browserIntent = Intent(Intent.ACTION_VIEW)
            browserIntent.data = Uri.parse("https://privacy.nftmakerapp.io/")
            this.requireActivity().startActivity(browserIntent)
        }))
    }
}