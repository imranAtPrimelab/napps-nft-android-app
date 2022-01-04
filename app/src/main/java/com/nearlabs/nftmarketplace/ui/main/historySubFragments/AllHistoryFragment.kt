package com.nearlabs.nftmarketplace.ui.main.historySubFragments

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.nearlabs.nftmarketplace.R
import com.nearlabs.nftmarketplace.common.extensions.viewBinding
import com.nearlabs.nftmarketplace.databinding.FragmentAllHistoryBinding
import com.nearlabs.nftmarketplace.ui.base.BaseFragment
import com.nearlabs.nftmarketplace.ui.main.historySubFragments.adapter.HistoryTabsAdapter

class AllHistoryFragment : BaseFragment(R.layout.fragment_all_history) {

    private val binding by viewBinding(FragmentAllHistoryBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rvallhistory.layoutManager = LinearLayoutManager(
            activity,
            RecyclerView.VERTICAL, false
        )

        val mData = ArrayList<String>()
        mData.add("#123456")
        mData.add("#123456")
        mData.add("#123456")
        mData.add("#123456")
        mData.add("#123456")
        mData.add("#123456")
        mData.add("#123456")

        binding.rvallhistory.adapter = HistoryTabsAdapter(mData)
        binding?.rvallhistory?.adapter?.notifyDataSetChanged()

    }
}