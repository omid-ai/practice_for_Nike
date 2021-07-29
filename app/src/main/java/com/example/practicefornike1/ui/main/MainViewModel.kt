package com.example.practicefornike1.ui.main

import com.example.practicefornike1.common.NikeSingleObserver
import com.example.practicefornike1.common.NikeViewModel
import com.example.practicefornike1.data.CartItemCount
import com.example.practicefornike1.data.TokenContainer
import com.example.practicefornike1.data.repository.CartRepository
import io.reactivex.schedulers.Schedulers
import org.greenrobot.eventbus.EventBus

class MainViewModel(val cartRepository: CartRepository):NikeViewModel(){

    fun getCartItemCount(){
        if (!TokenContainer.token.isNullOrEmpty()){
            cartRepository.getCartItemCount().subscribeOn(Schedulers.io())
                .subscribe(object :NikeSingleObserver<CartItemCount>(compositeDisposable){
                    override fun onSuccess(t: CartItemCount) {
                        EventBus.getDefault().postSticky(t)
                    }
            })
        }
    }
}