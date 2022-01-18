package com.nft.maker.ui.custom

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.annotation.Nullable
import androidx.constraintlayout.widget.ConstraintLayout
import com.nft.maker.R
import com.nft.maker.databinding.CustomSettingItemViewBinding

class CustomSettingItemView : ConstraintLayout {

    private lateinit var binding: CustomSettingItemViewBinding
    private var title: String? = null
    private var value: String? = null

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
        binding = CustomSettingItemViewBinding.inflate(LayoutInflater.from(context), this)
    }

    private fun extractAttrs(attrs: AttributeSet?) {
        val attributes = context.obtainStyledAttributes(attrs, R.styleable.CustomSettingItemView)
        title = attributes.getString(R.styleable.CustomSettingItemView_csiv_title)
        value = attributes.getString(R.styleable.CustomSettingItemView_csiv_value)
        attributes.recycle()
    }

    private fun initViews() {
        setTitle(title)
        setValue(value)
    }

    fun setValue(value: String?) {
        this.value = value
        binding.tvValue.text = value
    }

    fun setTitle(title: String?) {
        this.title = title
        binding.tvTitle.text = title
        binding.tvTitle.visibility = if (title.isNullOrEmpty()) View.GONE else View.VISIBLE
    }
}