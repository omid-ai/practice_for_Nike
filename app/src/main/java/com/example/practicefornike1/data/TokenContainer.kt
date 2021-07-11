package com.example.practicefornike1.data

import timber.log.Timber

object TokenContainer {

    var token: String? = null
        private set
    var refreshToken: String? = null
        private set

    fun update(token: String?, refreshToken: String?) {
        Timber.i("access token-> ${token?.substring(0,10)},refresh token->${refreshToken?.substring(0,10)}")
        this.token = token
        this.refreshToken = refreshToken
    }
}