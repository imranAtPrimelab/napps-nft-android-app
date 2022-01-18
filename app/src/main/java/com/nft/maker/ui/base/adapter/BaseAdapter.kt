package com.nft.maker.ui.base.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView


abstract class BaseAdapter<DATA, VH : RecyclerView.ViewHolder>(
    private val items: MutableList<DATA?> = mutableListOf(),
) : RecyclerView.Adapter<VH>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return createViewHolderInternal(parent, viewType)
    }

    override fun getItemCount(): Int = items.size

    abstract fun createViewHolderInternal(parent: ViewGroup, viewType: Int): VH

    fun getItemAtPosition(position: Int): DATA? = items.getOrNull(position)

    fun removeItemAtPosition(position: Int): DATA? = items.removeAt(position)

    open fun setData(newItems: List<DATA>?) {
        this.items.clear()
        this.items.addAll(newItems ?: emptyList())
        notifyDataSetChanged()
    }

    open fun getData() : List<DATA>? {
        return this.items as List<DATA>?
    }
}
