package com.nearlabs.nftmarketplace.data.preference

import android.content.SharedPreferences
import com.nearlabs.nftmarketplace.common.extensions.boolean

class SharePrefs(private val sharePrefs: SharedPreferences) {


    var isLoggedIn by sharePrefs.boolean()

    fun registerKeyChangeListener(listener: SharedPreferences.OnSharedPreferenceChangeListener) {
        sharePrefs.registerOnSharedPreferenceChangeListener(listener)
    }

    fun unregisterKeyChangeListener(listener: SharedPreferences.OnSharedPreferenceChangeListener) {
        sharePrefs.unregisterOnSharedPreferenceChangeListener(listener)
    }

    fun clear() {
        sharePrefs.edit().clear().apply()
    }

    fun getUserId() = "h315j-3kn1i5-315j3"

    fun accessToken() =
        "eyJraWQiOiIzQWlHTFhxYU10WkFUcTVXU0Z1ckRnR21nNTVydWlCbVZxSkR4YVZPS1VzPSIsImFsZyI6IlJTMjU2In0.eyJvcmlnaW5fanRpIjoiYjBhMGMxOGYtOTJjNy00Njc0LTg1ZmEtODY3OWM1OTFkZGFmIiwic3ViIjoiOTMzZjNiZmEtNjQ4NS00NmU5LTllNGItNzMyMDI5MTFjNDczIiwiZXZlbnRfaWQiOiI1ZTQyYTQ2YS05MGZmLTQ2Y2MtODY4Yi1lMDZmNzM5MDAxYTAiLCJ0b2tlbl91c2UiOiJhY2Nlc3MiLCJzY29wZSI6ImF3cy5jb2duaXRvLnNpZ25pbi51c2VyLmFkbWluIiwiYXV0aF90aW1lIjoxNjQxMzYwMjQ3LCJpc3MiOiJodHRwczpcL1wvY29nbml0by1pZHAudXMtZWFzdC0xLmFtYXpvbmF3cy5jb21cL3VzLWVhc3QtMV9QZWwzYmEwM1giLCJleHAiOjE2NDE0NDY2NDcsImlhdCI6MTY0MTM2MDI0NywianRpIjoiNjg0MThiNmYtZjZhNi00MmYyLWFjM2YtY2I1YWJmYmRiMTFhIiwiY2xpZW50X2lkIjoiMXBhaTQ0b3RjaHJhdml2cGNqbXZqdTN2MG0iLCJ1c2VybmFtZSI6ImFzZGZhc2RmMjM5MjM5MjMubmVhciJ9.Xay2gah_G3cwFK09qKz5e9OIZldgTDLtizSbsbtiFxYaZQQLjdYu4PD3dNGOawpW3lPFx60Q0EZly0YWcdxrkVjze9hGW6ptskGu1ne1FBTIo6nzLopvOMuKd-_LOyjhE0ODCH0oMjAtJWxk5hhpe8N0ZCD4j966_x4UMpXlJKorrAjEelnGpmArUs_DjYT-jTlHsKpWCuiURz5ARP8pOGNTwzUDzA01lZfnmBevOdvTYnbp4aHgyxIur3ihdqAUrXEsZyF029z_J5GAQ1z_qmNzIIRJsIE0AnXCYnfAo2Y7xodWYVCFmRIAZEL-kMkm-_HxRyu_wbunKsrM65ErHQ"
}