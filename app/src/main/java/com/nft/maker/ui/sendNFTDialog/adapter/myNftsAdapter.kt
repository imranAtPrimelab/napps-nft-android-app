package com.nft.maker.ui.sendNFTDialog.adapter

import android.content.Context
import android.os.Bundle
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.nft.maker.R
import com.nft.maker.extensions.viewBinding
import com.nft.maker.databinding.ItemLayoutSendNftCellBinding
import com.nft.maker.model.nft.NFT
import com.nft.maker.ui.base.adapter.BaseSelectionAdapter

import com.bumptech.glide.Glide


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
        context?.let { Glide.with(it).load(data.image).into(binding.ivThumbnailSendNFT) }
        binding.NftContainer.setOnClickListener{
            val bundle = Bundle()
            bundle.putInt("NftPosition",this.layoutPosition)
            Navigation.findNavController(itemView).navigate(R.id.detailsFragment,bundle)
        }

        //binding.root.setOnClickListener { onItemClicked?.invoke(data, adapterPosition) }
    }
}