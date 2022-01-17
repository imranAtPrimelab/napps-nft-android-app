package com.nearlabs.nftmarketplace.ui.detailnft

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.nearlabs.nftmarketplace.common.extensions.viewBinding
import com.nearlabs.nftmarketplace.databinding.ListItemAttributesBinding
import com.nearlabs.nftmarketplace.databinding.ListItemPropertiesBinding
import com.nearlabs.nftmarketplace.domain.model.Contact

class NftAttributesAdapter (
    private val context: Context,
    private val attributes : HashMap<String,String>
) :
    RecyclerView.Adapter<NftAttributesHolder>() {


    override fun onBindViewHolder(holder: NftAttributesHolder, position: Int) {
        holder.binding.attributeName.text = attributes.keys.elementAt(position)
        holder.binding.attributeValue.text = attributes.values.elementAt(position)

    }

    override fun getItemCount(): Int {
        return attributes.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NftAttributesHolder {
        return NftAttributesHolder(
            this.context,
            parent.viewBinding(ListItemAttributesBinding::inflate)
        )
    }


}

class NftAttributesHolder(
    private val context: Context,
    val binding: ListItemAttributesBinding
) : RecyclerView.ViewHolder(binding.root) {


    fun bind(data: Contact) {

    }

}
