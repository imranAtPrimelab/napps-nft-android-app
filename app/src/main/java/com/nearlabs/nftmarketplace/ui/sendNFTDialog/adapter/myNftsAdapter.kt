package com.nearlabs.nftmarketplace.ui.sendNFTDialog.adapter

import android.R.attr
import android.content.Context
import android.os.Bundle
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.nearlabs.nftmarketplace.R
import com.nearlabs.nftmarketplace.common.extensions.viewBinding
import com.nearlabs.nftmarketplace.databinding.ItemLayoutSendNftCellBinding
import com.nearlabs.nftmarketplace.domain.model.nft.NFT
import com.nearlabs.nftmarketplace.ui.base.adapter.BaseSelectionAdapter
import android.graphics.BitmapFactory
import android.os.AsyncTask
import android.os.Handler
import android.os.Looper

import androidx.appcompat.widget.AppCompatImageView
import okhttp3.*
import java.io.IOException
import java.net.HttpURLConnection
import java.net.URL
import com.bumptech.glide.Glide

import android.graphics.Bitmap
import java.lang.Exception


class myNftsAdapter(private val onItemClicked: ((NFT, Int) -> Unit)? = null) :
    BaseSelectionAdapter<NFT, ItemMyNFTViewHolder>() {

    var token = ""
    var context: Context? = null
    override fun createViewHolderInternal(parent: ViewGroup, viewType: Int): ItemMyNFTViewHolder {
        var viewHolder = ItemMyNFTViewHolder(
            parent.viewBinding(ItemLayoutSendNftCellBinding::inflate),
            onItemClicked
        )
        viewHolder.token = token
        viewHolder.context = context
        return viewHolder
    }

    override fun onBindViewHolder(holder: ItemMyNFTViewHolder, position: Int) {
        val item = getItemAtPosition(position) ?: return
        holder.bind(item, isSelected(position))

    }
}

class ItemMyNFTViewHolder(
    private val binding: ItemLayoutSendNftCellBinding,
    private val onItemClicked: ((NFT, Int) -> Unit)?
) : RecyclerView.ViewHolder(binding.root) {

    var context: Context? = null
    var token = ""

    fun bind(data: NFT, selected: Boolean) {
        binding.tvTitleSendNFT.text = data.type.toString()
        binding.tvTitleSendNFT.text = data.name
        binding.tvUIDSendNFT.text = data.id.toString()
        getBitmapFromURL(data.image, binding.ivThumbnailSendNFT)
        binding.NftContainer.setOnClickListener{
            val bundle = Bundle()
            bundle.putInt("NftPosition",this.layoutPosition)
            Navigation.findNavController(itemView).navigate(R.id.detailsFragment,bundle)
        }

        //binding.root.setOnClickListener { onItemClicked?.invoke(data, adapterPosition) }
    }


    private fun getBitmapFromURL(src: String, imageView: AppCompatImageView) {

    }

}