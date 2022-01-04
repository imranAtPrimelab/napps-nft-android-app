package com.nearlabs.nftmarketplace.util.models

class Contact {
    private var nameShort : String = "";
    private var nameLong : String = "";
    private var username : String = "";
    //var nameShort : String = "";
    fun setNameShort(input : String){
        nameShort = input
    }
    fun getNameShort() : String{
        return nameShort
    }

    fun setNameLong(input : String){
        nameLong = input
    }
    fun getNameLong() : String{
        return nameLong
    }

    fun setUsername(input : String){
        username = input
    }
    fun getUsername() : String{
        return username
    }
}