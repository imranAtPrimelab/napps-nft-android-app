package com.nearlabs.nftmarketplace.ui.custom

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.annotation.Nullable
import androidx.constraintlayout.widget.ConstraintLayout
import com.nearlabs.nftmarketplace.R
import com.nearlabs.nftmarketplace.databinding.ItemCustomNftCellBinding

class CustomNFTsItemView: ConstraintLayout {

    private lateinit var binding: ItemCustomNftCellBinding
    private var title: String? = null
    private var uid: String? = null
    private var thumbnail: String? = null
    private var nftType:String?= null

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
        binding = ItemCustomNftCellBinding.inflate(LayoutInflater.from(context),this,false)
    }

    private fun extractAttrs(attrs: AttributeSet?) {
        val attributes = context.obtainStyledAttributes(attrs, R.styleable.CustomNFTsItemView)
        title = attributes.getString(R.styleable.CustomNFTsItemView_cnftiv_title)
        nftType = attributes.getString(R.styleable.CustomNFTsItemView_cnftiv_nft_type)
        uid = attributes.getString(R.styleable.CustomNFTsItemView_cnftiv_uid)
        thumbnail = attributes.getString(R.styleable.CustomNFTsItemView_cnftiv_thumbnail)

        attributes.recycle()
    }

    private fun initViews() {
        setTitle(title)
        setNFTType(nftType)
        setUID(uid)
        setImage(thumbnail?:"")
    }

    private fun setNFTType(nftType: String?) {

    }

    fun setUID(value: String?) {
    }

    fun setImage(image:String){

    }

    fun setTitle(title: String?) {
        this.title = title
//        binding.tvTitle.text = title
//        binding.tvTitle.visibility = if (title.isNullOrEmpty()) View.GONE else View.VISIBLE
    }
}