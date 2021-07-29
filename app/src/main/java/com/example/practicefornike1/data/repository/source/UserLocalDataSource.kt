package com.example.practicefornike1.data.repository.source

import android.content.SharedPreferences
import com.example.practicefornike1.data.MessageResponse
import com.example.practicefornike1.data.TokenContainer
import com.example.practicefornike1.data.TokenResponce
import io.reactivex.Single

const val ACCESS_TOKEN = "access_token"
const val REFRESH_TOKEN = "refresh_token"

class UserLocalDataSource(val sharedPreferences: SharedPreferences) : UserDataSource {

    override fun login(userName: String, password: String): Single<TokenResponce> {
        TODO("Not yet implemented")
    }

    override fun signUp(userName: String, password: String): Single<MessageResponse> {
        TODO("Not yet implemented")
    }

    override fun loadToken() {
        TokenContainer.update(
            sharedPreferences.getString(ACCESS_TOKEN, null),
            sharedPreferences.getString(REFRESH_TOKEN, null)
        )
    }

    override fun saveToken(token: String, refreshToken: String) {
        sharedPreferences.edit().apply {
            putString(ACCESS_TOKEN, token)
            putString(REFRESH_TOKEN, refreshToken)
        }.apply()
    }

    override fun getUsername():String {
        return sharedPreferences.getString("username","")?:""
    }

    override fun saveUserName(userName: String) {
        sharedPreferences.edit().apply {
            putString("username",userName)
        }.apply()
    }

    override fun signOut() {
        sharedPreferences.edit().apply {
            clear()
        }.apply()
    }
}