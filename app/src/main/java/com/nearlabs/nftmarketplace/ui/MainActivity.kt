package com.nearlabs.nftmarketplace.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import com.nearlabs.nftmarketplace.R
import com.nearlabs.nftmarketplace.viewmodel.AuthViewModel

class MainActivity : AppCompatActivity(R.layout.activity_main) {

    private val navController by lazy { findNavController(R.id.nav_host) }
    private val authViewModel by viewModels<AuthViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initNavGraph()
    }

    private fun initNavGraph() {
        val navGraph = navController.navInflater.inflate(R.navigation.nav_launch)
        if (authViewModel.isLoggedIn()) {
            navGraph.startDestination = R.id.nav_main
            navController.graph = navGraph
        } else {
            navGraph.startDestination = R.id.nav_auth
            navController.graph = navGraph
        }
    }
}