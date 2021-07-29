package com.example.practicefornike1.ui.user

import com.example.practicefornike1.common.NikeViewModel
import com.example.practicefornike1.data.TokenContainer
import com.example.practicefornike1.data.repository.UserRepository

class UserViewModel(val userRepository: UserRepository) : NikeViewModel() {

    val userName: String
        get() = userRepository.getUsername()

    val isSignedIn: Boolean
        get() = TokenContainer.token != null

    fun signOut() {
        userRepository.signOut()
    }
}