package com.nearlabs.nftmarketplace.util.adapters

import android.view.ViewGroup

import androidx.recyclerview.widget.RecyclerView
import com.nearlabs.nftmarketplace.common.extensions.viewBinding
import com.nearlabs.nftmarketplace.databinding.ItemContactsBinding
import com.nearlabs.nftmarketplace.domain.model.Contact
import com.nearlabs.nftmarketplace.domain.model.nft.NFT
import com.nearlabs.nftmarketplace.ui.base.adapter.BaseSelectionAdapter

class ContactListAdapter(
    private val onItemClicked: ((Contact, Int) -> Unit)? = null) :
    BaseSelectionAdapter<Contact, ItemContactHolder>() {
    inner class MyViewHolder(b: ItemContactsBinding) :
        RecyclerView.ViewHolder(b.root) {
        var binding : ItemContactsBinding = b
    }

    override fun createViewHolderInternal(parent: ViewGroup, viewType: Int): ItemContactHolder {
        return ItemContactHolder(
            parent.viewBinding(ItemContactsBinding::inflate),
            onItemClicked
        )
    }

    override fun onBindViewHolder(holder: ItemContactHolder, position: Int) {
        val item = getItemAtPosition(position) ?: return
        holder.bind(item, isSelected(position))
    }


}

class ItemContactHolder(
    private val binding: ItemContactsBinding,
    private val onItemClicked: ((Contact, Int) -> Unit)?
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(data: Contact, selected: Boolean) {
        binding.nameLong.text = data.firstName
        binding.username.text = data.contactUserId
        /*binding.root.setOnClickListener {
            onItemClicked?.invoke(data, adapterPosition) }*/
    }
}


/*  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
      return MyViewHolder(ItemContactsBinding.inflate(LayoutInflater.from(context)))
  }

  override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
      holder.binding.nameShort.text = items[position].firstName?.take(2)
      holder.binding.nameLong.text = items[position].firstName
      holder.binding.username.text = items[position].firstName
      //holder.binding.checkStatus.setImageDrawable(text)
  }

  override fun getItemCount(): Int {
      return items.size
  }*/
