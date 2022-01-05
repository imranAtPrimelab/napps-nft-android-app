package com.nearlabs.nftmarketplace.ui.sendNFTDialog.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.nearlabs.nftmarketplace.common.extensions.viewBinding
import com.nearlabs.nftmarketplace.databinding.ItemLayoutSendNftCellBinding
import com.nearlabs.nftmarketplace.domain.model.nft.NFT
import com.nearlabs.nftmarketplace.ui.base.adapter.BaseAdapter

class SendNFTAdapter(private val onItemClicked: ((NFT) -> Unit)? = null, ) : BaseAdapter<NFT, ItemSendNFTViewHolder>() {

    override fun createViewHolderInternal(parent: ViewGroup, viewType: Int): ItemSendNFTViewHolder {
        return ItemSendNFTViewHolder(
            parent.viewBinding(ItemLayoutSendNftCellBinding::inflate),
            onItemClicked
        )
    }

    override fun onBindViewHolder(holder: ItemSendNFTViewHolder, position: Int) {
        val item = getItemAtPosition(position) ?: return
        holder.bind(item)
    }
}

class ItemSendNFTViewHolder(
    private val binding: ItemLayoutSendNftCellBinding,
    private val onItemClicked: ((NFT) -> Unit)?
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(data: NFT) {
        binding.tvTitleSendNFT.text = data.type.toString()
        binding.tvTitleSendNFT.text = data.name
        binding.tvUIDSendNFT.text = data.id.toString()
//        binding.ivThumbnailSendNFT.visibility = if (data.selected) View.VISIBLE else View.GONE
        binding.root.setOnClickListener { onItemClicked?.invoke(data) }
    }
}