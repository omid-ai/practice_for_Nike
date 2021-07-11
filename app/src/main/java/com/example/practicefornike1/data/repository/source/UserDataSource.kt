package com.example.practicefornike1.data.repository.source

import com.example.practicefornike1.data.MessageResponse
import com.example.practicefornike1.data.TokenResponce
import io.reactivex.Completable
import io.reactivex.Single

interface UserDataSource {

    fun login(userName:String,password:String):Single<TokenResponce>

    fun signUp(userName:String,password: String):Single<MessageResponse>

    fun loadToken()

    fun saveToken(token:String,refreshToken:String)
}