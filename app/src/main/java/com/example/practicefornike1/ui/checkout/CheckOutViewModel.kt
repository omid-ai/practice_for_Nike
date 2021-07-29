package com.example.practicefornike1.ui.checkout

import androidx.lifecycle.MutableLiveData
import com.example.practicefornike1.common.NikeSingleObserver
import com.example.practicefornike1.common.NikeViewModel
import com.example.practicefornike1.common.asyncNetworkRequest
import com.example.practicefornike1.data.Checkout
import com.example.practicefornike1.data.repository.OrderRepository

class CheckOutViewModel(val orderRepository: OrderRepository,orderId:Int):NikeViewModel() {

    val checkoutLiveData=MutableLiveData<Checkout>()

    init {
        orderRepository.checkout(orderId).asyncNetworkRequest()
            .subscribe(object :NikeSingleObserver<Checkout>(compositeDisposable){
                override fun onSuccess(t: Checkout) {
                    checkoutLiveData.value=t
                }
            })
    }
}