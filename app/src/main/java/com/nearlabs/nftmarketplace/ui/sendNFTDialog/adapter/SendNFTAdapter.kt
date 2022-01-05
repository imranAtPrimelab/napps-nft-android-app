package com.nearlabs.nftmarketplace.ui.sendNFTDialog.adapter

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.nearlabs.nftmarketplace.R
import com.nearlabs.nftmarketplace.common.extensions.viewBinding
import com.nearlabs.nftmarketplace.databinding.ItemLayoutSendNftCellBinding
import com.nearlabs.nftmarketplace.domain.model.nft.NFT
import com.nearlabs.nftmarketplace.ui.base.adapter.BaseSelectionAdapter

class SendNFTAdapter(private val onItemClicked: ((NFT, Int) -> Unit)? = null) :
    BaseSelectionAdapter<NFT, ItemSendNFTViewHolder>() {

    override fun createViewHolderInternal(parent: ViewGroup, viewType: Int): ItemSendNFTViewHolder {
        return ItemSendNFTViewHolder(
            parent.viewBinding(ItemLayoutSendNftCellBinding::inflate),
            onItemClicked
        )
    }

    override fun onBindViewHolder(holder: ItemSendNFTViewHolder, position: Int) {
        val item = getItemAtPosition(position) ?: return
        holder.bind(item, isSelected(position))
    }
}

class ItemSendNFTViewHolder(
    private val binding: ItemLayoutSendNftCellBinding,
    private val onItemClicked: ((NFT, Int) -> Unit)?
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(data: NFT, selected: Boolean) {
        binding.tvTitleSendNFT.text = data.type.toString()
        binding.tvTitleSendNFT.text = data.name
        binding.tvUIDSendNFT.text = data.id.toString()
//        binding.ivThumbnailSendNFT.visibility = if (data.selected) View.VISIBLE else View.GONE

        if (selected) {
            binding.imageSelected.visibility = View.VISIBLE
            binding.llContent.setBackgroundResource(R.drawable.bg_ntf_selected)
        } else {
            binding.imageSelected.visibility = View.GONE
            binding.llContent.setBackgroundResource(0)
        }

        binding.root.setOnClickListener { onItemClicked?.invoke(data, adapterPosition) }
    }
}