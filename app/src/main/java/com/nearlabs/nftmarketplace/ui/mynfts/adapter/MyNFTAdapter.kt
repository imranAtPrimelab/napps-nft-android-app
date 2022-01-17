package com.nearlabs.nftmarketplace.ui.mynfts.adapter

import android.content.Context
import android.os.Bundle
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.nearlabs.nftmarketplace.R
import com.nearlabs.nftmarketplace.common.extensions.viewBinding
import com.nearlabs.nftmarketplace.databinding.ItemCustomNftCellBinding
import com.nearlabs.nftmarketplace.domain.model.nft.NFT
import com.nearlabs.nftmarketplace.ui.base.adapter.BaseSelectionAdapter

class MyNFTAdapter(private val onItemClicked: ((NFT, Int) -> Unit)? = null) :
    BaseSelectionAdapter<NFT, ItemSendNFTViewHolder>() {

    var context: Context? = null

    override fun createViewHolderInternal(parent: ViewGroup, viewType: Int): ItemSendNFTViewHolder {
        var holder = ItemSendNFTViewHolder(
            parent.viewBinding(ItemCustomNftCellBinding::inflate),
            onItemClicked
        )
        holder.context = context
        return holder
    }

    override fun onBindViewHolder(holder: ItemSendNFTViewHolder, position: Int) {
        val item = getItemAtPosition(position) ?: return
        holder.bind(item, isSelected(position))
    }
}

class ItemSendNFTViewHolder(
    private val binding: ItemCustomNftCellBinding,
    private val onItemClicked: ((NFT, Int) -> Unit)?
) : RecyclerView.ViewHolder(binding.root) {

    var context: Context? = null

    fun bind(data: NFT, selected: Boolean) {
        binding.tvTitle.text = data.name
        binding.tvUID.text = data.id
        context?.let { Glide.with(context!!).load(data.image).into(binding.ivThumbnail) }
        binding.root.setOnClickListener {
            val bundle = Bundle()
            bundle.putInt("NftPosition",this.layoutPosition)
            Navigation.findNavController(itemView).navigate(R.id.detailsFragment,bundle)
        }
    }
}