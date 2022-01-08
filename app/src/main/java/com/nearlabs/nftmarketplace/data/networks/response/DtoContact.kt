package com.nearlabs.nftmarketplace.data.networks.response

import com.google.gson.annotations.SerializedName

data class DtoContact(
	@SerializedName("birthday")
	val birthday: String? = null,

	@SerializedName("address")
	val address: List<DtoContactAddress>? = null,

	@SerializedName("archived_date")
	val archivedDate: String? = null,

	@SerializedName("created")
	val created: String? = null,

	@SerializedName("last_name")
	val lastName: String? = null,

	@SerializedName("groups")
	val groups: List<String>? = null,

	@SerializedName("companies")
	val companies: List<String>? = null,

	@SerializedName("phone")
	val phone: List<DtoContactPhone>? = null,

	@SerializedName("dob")
	val dob: String? = null,

	@SerializedName("is_imported")
	val isImported: Boolean? = null,

	@SerializedName("contact_user_id")
	val contactUserId: String? = null,

	@SerializedName("import_source")
	val importSource: String? = null,

	@SerializedName("first_name")
	val firstName: String? = null,

	@SerializedName("job_title")
	val jobTitle: String? = null,

	@SerializedName("app_id")
	val appId: String? = null,

	@SerializedName("updated")
	val updated: String? = null,

	@SerializedName("email")
	val email: List<DtoContactEmail>? = null,

	@SerializedName("status")
	val status: String? = null,

	@SerializedName("owner_id")
	val ownerId: String? = null
)

data class DtoContactEmail(

	@SerializedName("address")
	val address: String? = null,

	@SerializedName("type")
	val type: String? = null
)

data class DtoContactAddress(

	@SerializedName("country")
	val country: String? = null,

	@SerializedName("city")
	val city: String? = null,

	@SerializedName("formatted")
	val formatted: String? = null,

	@SerializedName("street")
	val street: String? = null,

	@SerializedName("region")
	val region: String? = null,

	@SerializedName("postal_code")
	val postalCode: String? = null,

	@SerializedName("type")
	val type: String? = null
)

data class DtoContactPhone(

	@SerializedName("number")
	val number: String? = null,

	@SerializedName("type")
	val type: String? = null
)
