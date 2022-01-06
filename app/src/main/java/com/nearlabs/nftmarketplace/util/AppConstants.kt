package com.nearlabs.nftmarketplace.util

import android.content.Context
import com.appsflyer.AppsFlyerLib

object AppConstants {
    fun logAppsFlyerEvent(eventName: String, context: Context){
        val eventValues = HashMap<String, Any>()
        AppsFlyerLib.getInstance().logEvent(context ,
            eventName, eventValues)
    }

    const val NFT_EVENT_NAME = "Send_NFT"
    const val NFT_EVENT_EMAIL_NAME = "email"

    const val CLICK_LOGIN_WITH_PHONE_EVENT_NAME ="click_continue_with_phone"
    const val LOGIN_WITH_PHONE_EMAIL_EVENT_NAME ="simple_continue_with_phone_email"
    const val LOGIN_WITH_NEAR_EVENT_NAME = "login_with_near"
    const val CREATE_NEW_ACCOUNT_EVENT_NAME = "create_new_account"
    const val CONTACTS_PERMISSION_GRANTED_EVENT_NAME = "contacts_permission_granted"
    const val CREATE_NFT_NEXT_BUTTON_EVENT_NAME = "create_nft_next_button"
    const val PREVIEW_NFT_MINT_EVENT_NAME="preview_nft_mint"
    const val CLAIM_NFT_EVENT_NAME = "claim_nft"
    const val CLAIM_NFT_LOGIN_WITH_NEAR_WALLET_EVENT_NAME = "claim_nft_login_with_near_wallet"
    const val CLAIM_NFT_CREATE_NEAR_WALLET_ACCOUNT = "claim_nft_create_near_wallet_account"
    const val DASHBOARD_CREATE_NFT_EVENT_NAME = "dashboard_create_nft"
    const val DASHBOARD_SEND_NFT_EVENT_NAME = "dashboard_send_nft"
    const val SEND_NFT_NEXT_BUTTON_EVENT_NAME = "send_nft_next_button"
    const val ENTER_VERIFICATION_CODE_EVENT_NAME = "verification_code_entered"
}