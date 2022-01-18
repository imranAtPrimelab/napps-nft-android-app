package com.nft.maker.util

class Helpers {
    companion object {
    fun checkEmailPhone(text: String, usingEmail: Boolean): Boolean {
        return if (usingEmail) {
            val isLegitEmail = android.util.Patterns.EMAIL_ADDRESS.matcher(text).matches()
            (text.isNotBlank() && isLegitEmail)
        } else {
            val isLegitNumber = android.util.Patterns.PHONE.matcher(text).matches()
            (text.isNotBlank() && isLegitNumber)
        }
    }}
}