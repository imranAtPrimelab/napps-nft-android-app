package com.nft.maker.ui.sendNFTDialog.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.nft.maker.R
import com.nft.maker.extensions.viewBinding
import com.nft.maker.databinding.ItemSentNftPeopleBinding
import com.nft.maker.model.Contact
import com.nft.maker.ui.base.adapter.BaseSelectionAdapter

class PeopleAdapter(private val onItemClicked: ((Contact, Int) -> Unit)? = null) :
    BaseSelectionAdapter<Contact, ItemPeopleAdapterViewHolder>() {

    override fun createViewHolderInternal(
        parent: ViewGroup,
        viewType: Int
    ): ItemPeopleAdapterViewHolder {
        return ItemPeopleAdapterViewHolder(
            parent.viewBinding(ItemSentNftPeopleBinding::inflate),
            onItemClicked
        )
    }

    override fun onBindViewHolder(holder: ItemPeopleAdapterViewHolder, position: Int) {
        val item = getItemAtPosition(position) ?: return
        holder.bind(item, isSelected(position))
    }
}

class ItemPeopleAdapterViewHolder(
    private val binding: ItemSentNftPeopleBinding,
    private val onItemClicked: ((Contact, Int) -> Unit)?
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(data: Contact, selected: Boolean) {
        binding.tvNameShort.text = ("${data.first_name?.get(0)?.toString()}${data.last_name?.get(0)?.toString()}")
        binding.tvName.text = "${data.first_name} ${data.last_name}"
        binding.tvDesc.text = data.email?.firstOrNull()?.address ?: ""

        binding.imageSelected.setImageResource(if (selected) R.drawable.ic_selected else R.drawable.ic_un_select)

        binding.root.setOnClickListener { onItemClicked?.invoke(data, adapterPosition) }
    }
}