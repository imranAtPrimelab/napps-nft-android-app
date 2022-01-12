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
import androidx.appcompat.widget.AppCompatEditText
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
import timber.log.Timber

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


fun AppCompatEditText.addSuffix(suffix: String) {
    val editText = this
    val formattedSuffix = "$suffix"
    var text = ""
    var isSuffixModified = false

    val setCursorPosition: () -> Unit =
        { Selection.setSelection(editableText, editableText.length - formattedSuffix.length) }

    val setEditText: () -> Unit = {
        editText.setText(text)
        setCursorPosition()
    }

    this.addTextChangedListener(object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        override fun afterTextChanged(editable: Editable?) {
            val newText = editable.toString()

            if (isSuffixModified) {
                // user tried to modify suffix
                isSuffixModified = false
                setEditText()
            } else if (text.isNotEmpty() && newText.length < text.length && !newText.contains(
                    formattedSuffix
                )
            ) {
                // user tried to delete suffix
                setEditText()
            } else if (!newText.contains(formattedSuffix)) {
                // new input, add suffix
                text = "$newText$formattedSuffix"
                setEditText()
            } else {
                text = newText
            }
        }
    })
}

class LoginFragment : BaseFragment(R.layout.fragment_login) {

    private val binding by viewBinding(FragmentLoginBinding::bind)
    private val userViewModel: UserViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.ccp.setCountryForPhoneCode(1)
        binding.etEmailPhoneLogin.addSuffix(".near")
        binding.etEmailPhoneLogin.setText(" ", TextView.BufferType.EDITABLE)
        binding.etEmailPhoneLogin.setText("", TextView.BufferType.EDITABLE)

        userViewModel.usesPhone = false
        initListeners()
    }

    private fun initListeners() {
        binding.ccp.registerCarrierNumberEditText(binding.etEmailPhone)
        binding.btnLogin.setOnClickListener { view ->
            if (binding.etEmailPhoneLogin.text.toString().isNotBlank()) {
                AppConstants.logAppsFlyerEvent(LOGIN_WITH_PHONE_EVENT_NAME, view.context)
                observeResultFlow(
                    userViewModel.loginUser(
                        binding.etEmailPhoneLogin.text.toString()
                    ), successHandler = {
                        userViewModel.walletName = binding.etEmailPhoneLogin.text.toString()
                        val bundle = Bundle()
                        bundle.putString(OTPFragment.LOGIN_TYPE, it.type)
                        findNavController().navigate(R.id.toOtp, bundle)
                    }, errorHandler = {
                        Toast.makeText(requireContext(), it?.message.toString(), Toast.LENGTH_SHORT).show()
                    }, httpErrorHandler = {
                        Timber.e(it.toString())
                        Toast.makeText(requireContext(), it?.message.toString(), Toast.LENGTH_SHORT).show()
                    })
            } else {
                Toast.makeText(requireContext(), getString(R.string.login_text_error), Toast.LENGTH_SHORT).show()
            }
        }
        binding.btnGetStarted.setOnClickListener {
            val usesEmail = !userViewModel.usesPhone
            if (checkEmailPhone(binding.etEmailPhone.text.toString(), usesEmail)) {
                AppConstants.logAppsFlyerEvent(GET_STARTED_EVENT_NAME, it.context)
                if (userViewModel.usesPhone) {
                    userViewModel.currentPhone = binding.ccp.getFullNumberWithPlus()
                } else {
                    userViewModel.currentEmail = binding.etEmailPhone.text.toString()
                }
                findNavController().navigate(R.id.signupFragment)
            } else {
                if (usesEmail) {
                    Toast.makeText(requireContext(), getString(R.string.email_error), Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(requireContext(), getString(R.string.phone_error), Toast.LENGTH_SHORT).show()
                }
            }
        }

        binding.tvPhoneLogin.setOnClickListener {
            binding.etEmailPhone.text?.clear()
            AppConstants.logAppsFlyerEvent(CLICK_LOGIN_WITH_PHONE_EVENT_NAME, it.context)
            binding.tvPhoneLogin.backgroundTintList = ColorStateList.valueOf(resources.getColor(R.color.light_grey))
            binding.tvEmailLogin.backgroundTintList = ColorStateList.valueOf(Color.WHITE)
            binding.etEmailPhone.hint = requireActivity().getString(R.string.phone_example)
            binding.etEmailPhone.inputType = InputType.TYPE_CLASS_PHONE
            userViewModel.usesPhone = true
            binding.ccp.visibility = View.VISIBLE
        }

        binding.tvEmailLogin.setOnClickListener {
            binding.etEmailPhone.text?.clear()
            AppConstants.logAppsFlyerEvent(CLICK_LOGIN_WITH_PHONE_EVENT_NAME, it.context)
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

        binding.termsText.makeLinks(Pair("Terms & Conditions", View.OnClickListener {
            val browserIntent = Intent(Intent.ACTION_VIEW)
            browserIntent.data = Uri.parse("https://terms.nftmakerapp.io/")
            this.requireActivity().startActivity(browserIntent)
        }), Pair("Privacy Policy", View.OnClickListener {
            val browserIntent = Intent(Intent.ACTION_VIEW)
            browserIntent.data = Uri.parse("https://privacy.nftmakerapp.io/")
            this.requireActivity().startActivity(browserIntent)
        }))

    }

    private fun checkEmailPhone(text: String, usingEmail: Boolean): Boolean {
        return if (usingEmail) {
            val isLegitEmail = android.util.Patterns.EMAIL_ADDRESS.matcher(text).matches()
            (text.isNotBlank() && isLegitEmail)
        } else {
            val isLegitNumber = android.util.Patterns.PHONE.matcher(text).matches()
            (text.isNotBlank() && isLegitNumber)
        }
    }
}