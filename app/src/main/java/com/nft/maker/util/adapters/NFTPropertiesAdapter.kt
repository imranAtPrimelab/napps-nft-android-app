package com.nft.maker.util.adapters

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.nft.maker.extensions.viewBinding
import com.nft.maker.databinding.ListItemPropertiesBinding
import com.nft.maker.model.Contact
import kotlin.collections.HashMap

class NFTPropertiesAdapter(
    private val context: Context,
    private val container: MutableList<Int>
) :
    RecyclerView.Adapter<ItemPropertiesHolder>() {

    val propertiesNames = HashMap<Int,String>(container.size)
    val propertiesValues = HashMap<Int,String>(container.size)

    override fun onBindViewHolder(holder: ItemPropertiesHolder, position: Int) {
        if(holder.adapterPosition==0){
            holder.binding.delete.visibility = View.GONE
        }
        holder.binding.attributeValueEditText.text= null
        holder.binding.attributeNameEditText.text= null

        holder.binding.attributeNameEditText.addTextChangedListener(object : TextWatcher {
              override fun afterTextChanged(s: Editable?) {
                  propertiesNames[holder.adapterPosition] = s.toString()
              }

              override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
              }

              override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
              }
          })
          holder.binding.attributeValueEditText.addTextChangedListener(object : TextWatcher {
              override fun afterTextChanged(s: Editable?) {
                  propertiesValues[holder.adapterPosition] = s.toString()
              }

              override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
              }

              override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
              }
          })

          holder.binding.delete.setOnClickListener{
              propertiesNames.remove(holder.adapterPosition)
              propertiesValues.remove(holder.adapterPosition)
              container.removeAt(holder.adapterPosition)
              notifyItemRemoved(holder.adapterPosition)
          }
    }

    override fun getItemCount(): Int {
        return container.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemPropertiesHolder {
        return ItemPropertiesHolder(
            this.context,
            parent.viewBinding(ListItemPropertiesBinding::inflate)
        )
    }

    fun getProperties(): List<HashMap<Int,String>>{
        return listOf(propertiesNames,propertiesValues)
    }

}

class ItemPropertiesHolder(
    private val context: Context,
    val binding: ListItemPropertiesBinding
) : RecyclerView.ViewHolder(binding.root) {


    fun bind(data: Contact) {

    }

}
