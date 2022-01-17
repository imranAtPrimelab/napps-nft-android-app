package com.nearlabs.nftmarketplace.ui

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import com.nearlabs.nftmarketplace.R
import com.nearlabs.nftmarketplace.data.preference.SharePrefs
import com.nearlabs.nftmarketplace.ui.base.activity.BaseActivity
import com.nearlabs.nftmarketplace.ui.detailnft.ClaimNFTFragment.Companion.CLIM_NFT_ID
import com.nearlabs.nftmarketplace.viewmodel.AuthViewModel
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
        sharePrefs.redirectedUrl = uri.toString()
        if (authViewModel.isLoggedIn()) {
            navGraph.startDestination = R.id.nav_main
            navGraph.startDestination
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
        } else {
            navGraph.startDestination = R.id.nav_auth
            navController.graph = navGraph
        }
    }

    override fun startActivityForResult(intent: Intent?, requestCode: Int) {
        super.startActivityForResult(intent, requestCode)
    }
}