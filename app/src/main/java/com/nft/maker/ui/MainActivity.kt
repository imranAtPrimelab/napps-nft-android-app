package com.nft.maker.ui

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.navigation.findNavController
import com.nft.maker.R
import com.nft.maker.data.preference.SharePrefs
import com.nft.maker.ui.base.activity.BaseActivity
import com.nft.maker.ui.detailnft.ClaimNFTFragment.Companion.CLIM_NFT_ID
import com.nft.maker.viewmodel.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : BaseActivity(R.layout.activity_main) {

    private val navController by lazy { findNavController(R.id.nav_host) }
    private val authViewModel by viewModels<AuthViewModel>()

    @Inject
    lateinit var sharePrefs: SharePrefs
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initNavGraph()
    }

    private fun initNavGraph() {
        val navGraph = navController.navInflater.inflate(R.navigation.nav_launch)
        val uri = intent?.data
        if (authViewModel.isLoggedIn()) {
            navGraph.startDestination = R.id.nav_main
            navController.graph = navGraph
        } else {
            navGraph.startDestination = R.id.nav_auth
            when {
                uri == null -> {
                    navController.graph = navGraph
                }
                uri.toString().contains("dev.nftmakerapp.io/nft/detail/claim") -> {
                    val nftId = uri.lastPathSegment
                    val bundle = Bundle()
                    bundle.putString(CLIM_NFT_ID, nftId)
                    navController.setGraph(navGraph, bundle)
                }
                else -> {
                    navController.graph = navGraph
                }
            }
        }
    }

    override fun startActivityForResult(intent: Intent?, requestCode: Int) {
        super.startActivityForResult(intent, requestCode)
    }
}