package com.example.practicefornike1.data.repository

import com.example.practicefornike1.data.TokenContainer
import com.example.practicefornike1.data.TokenResponce
import com.example.practicefornike1.data.repository.source.UserDataSource

import io.reactivex.Completable

class UserRepositoryImpl(
    val remoteDataSource: UserDataSource,
    val localDataSource: UserDataSource
) :
    UserRepository {
    override fun login(userName: String, password: String): Completable =
        remoteDataSource.login(userName, password).doOnSuccess {
            onSuccessfulLogin(it,userName)
        }.ignoreElement()

    override fun signUp(userName: String, password: String): Completable =
        remoteDataSource.signUp(userName, password).flatMap { messageResponces ->
            remoteDataSource.login(userName, password)
        }.doOnSuccess {
            onSuccessfulLogin(it,userName)
        }.ignoreElement()

    override fun loadToken() {
        localDataSource.loadToken()
    }

    override fun getUsername():String {
        return localDataSource.getUsername()
    }

    override fun signOut() {
        localDataSource.signOut()
    }

    fun onSuccessfulLogin(tokenResponce: TokenResponce,userName: String) {
        TokenContainer.update(tokenResponce.access_token, tokenResponce.refresh_token)
        localDataSource.saveToken(tokenResponce.access_token, tokenResponce.refresh_token)
        localDataSource.saveUserName(userName)
    }
}