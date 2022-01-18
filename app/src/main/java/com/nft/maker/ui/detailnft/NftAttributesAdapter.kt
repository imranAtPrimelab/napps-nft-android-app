package com.nft.maker.ui.detailnft

import android.content.Context
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.nft.maker.extensions.viewBinding
import com.nft.maker.databinding.ListItemAttributesBinding
import com.nft.maker.model.Contact

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
