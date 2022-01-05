package com.nearlabs.nftmarketplace.common

import androidx.core.widget.NestedScrollView
import androidx.recyclerview.widget.RecyclerView


abstract class BaseLoadMoreInsideNestedScrollAdapter<DATA, VH : RecyclerView.ViewHolder>(
    items: MutableList<DATA?> = mutableListOf(),
    onLoadMoreListener: () -> Unit
) : BaseLoadMoreAdapter<DATA, VH>(items, onLoadMoreListener) {

    fun attachToNestedScrollView(nestedScrollView: NestedScrollView) {
        nestedScrollView.setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener { v, scrollX, scrollY, oldScrollX, oldScrollY ->
            if (isLoading || !isAllowLoadMore) return@OnScrollChangeListener
            if (scrollY == v.getChildAt(0).measuredHeight - v.measuredHeight) {
                invokeLoadMore()
            }
        })
    }

    override fun initLoadMore(recyclerView: RecyclerView) {
        // ignore recyclerview load
    }
}