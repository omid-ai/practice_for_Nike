package com.example.practicefornike1.ui.auth

import com.example.practicefornike1.common.NikeViewModel
import com.example.practicefornike1.data.repository.UserRepository
import io.reactivex.Completable

class AuthViewModel(val userRepository: UserRepository):NikeViewModel() {

    fun login(userName:String,password:String):Completable{
        return userRepository.login(userName,password)
    }

    fun signUp(userName:String,password:String):Completable{
        return userRepository.signUp(userName,password)
    }
}