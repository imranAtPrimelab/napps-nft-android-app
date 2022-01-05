package com.nearlabs.nftmarketplace.domain.model.user

data class Data (

	val user_id : String,
	val wallet_status : String,
	val status : String,
	val created : Int,
	val full_name : String,
	val wallet_id : String,
	val email : String,
	val phone : Int,
	val verified : Boolean
)