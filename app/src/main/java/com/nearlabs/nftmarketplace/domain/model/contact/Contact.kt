package com.nearlabs.nftmarketplace.domain.model.contact

data class Contact (
	val first_name : String,
	val email : List<Email>,
	val phone : List<Phone>,
	val last_name : String,
	val address : List<Address>,
	val job_title : String,
	val companies : List<String>,
	val groups : List<String>,
	val dob : String,
	val birthday : String,
	val is_imported : Boolean,
	val import_source : String,
	val app_id : String,
	val contact_user_id : String,
	val status : String,
	val created : Int,
	val updated : Int,
	val archived_date : Int
)