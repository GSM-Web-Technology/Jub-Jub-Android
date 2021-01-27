package com.example.jup_jup_android.entity.singleton

object TokenManager {

    lateinit var token : String


    @JvmName("getToken1")
    fun getToken(): String{
        return token
    }

    @JvmName("setToken1")
    fun setToken(tk: String){
        token = tk
    }

}