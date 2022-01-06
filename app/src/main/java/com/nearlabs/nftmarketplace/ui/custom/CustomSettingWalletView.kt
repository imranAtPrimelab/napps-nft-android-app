package com.nearlabs.nftmarketplace.ui.custom

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.annotation.Nullable
import androidx.constraintlayout.widget.ConstraintLayout
import com.nearlabs.nftmarketplace.R
import com.nearlabs.nftmarketplace.databinding.CustomSettingWalletViewBinding

class CustomSettingWalletView : ConstraintLayout {

    private lateinit var binding: CustomSettingWalletViewBinding
    private var walletName: String? = null

    constructor(context: Context, @Nullable attrs: AttributeSet?) : super(context, attrs) {
        setupView()
        extractAttrs(attrs)
        initViews()
    }

    constructor(
        context: Context, @Nullable attrs: AttributeSet?, defStyleAttr: Int
    ) : super(context, attrs, defStyleAttr) {
        setupView()
        extractAttrs(attrs)
        initViews()
    }

    private fun setupView() {
        binding = CustomSettingWalletViewBinding.inflate(LayoutInflater.from(context), this)
    }

    private fun extractAttrs(attrs: AttributeSet?) {
        val attributes = context.obtainStyledAttributes(attrs, R.styleable.CustomSettingWalletView)
        walletName = attributes.getString(R.styleable.CustomSettingWalletView_cswv_name)
        attributes.recycle()
    }

    private fun initViews() {
        setWalletName(walletName)
    }

    fun setWalletName(name: String?) {
        this.walletName = name
        binding.tvName.text = name
    }
}