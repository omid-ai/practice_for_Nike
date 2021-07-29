package com.example.practicefornike1.data.repository.source

import com.example.practicefornike1.data.MessageResponse
import com.example.practicefornike1.data.TokenResponce
import com.example.practicefornike1.service.ApiService
import com.google.gson.JsonObject
import io.reactivex.Single

const val CLIENT_SECRET = "kyj1c9sVcksqGU4scMX7nLDalkjp2WoqQEf8PKAC"
const val CLIENT_ID = 2

class UserRemoteDataSource(val apiService: ApiService) : UserDataSource {
    override fun login(userName: String, password: String): Single<TokenResponce> =
        apiService.login(
            JsonObject().apply {
                addProperty("username", userName)
                addProperty("password", password)
                addProperty("grant_type", "password")
                addProperty("client_id", CLIENT_ID)
                addProperty("client_secret", CLIENT_SECRET)
            }
        )

    override fun signUp(userName: String, password: String): Single<MessageResponse> =
        apiService.signUp(JsonObject().apply {
            addProperty("email",userName)
            addProperty("password",password)
        })

    override fun loadToken() {
        TODO("Not yet implemented")
    }

    override fun saveToken(token: String, refreshToken: String) {
        TODO("Not yet implemented")
    }

    override fun getUsername():String {
        TODO("Not yet implemented")
    }

    override fun saveUserName(userName: String) {
        TODO("Not yet implemented")
    }

    override fun signOut() {
        TODO("Not yet implemented")
    }
}