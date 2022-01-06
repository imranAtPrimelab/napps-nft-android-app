package com.nearlabs.nftmarketplace.domain.model.contact


data class Address (

	val formatted : String,
	val street : String,
	val city : String,
	val region : String,
	val country : String,
	val postal_code : String,
	val type : String
)