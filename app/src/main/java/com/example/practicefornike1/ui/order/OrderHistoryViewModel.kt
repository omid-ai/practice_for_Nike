package com.example.practicefornike1.ui.order

import androidx.lifecycle.MutableLiveData
import com.example.practicefornike1.common.NikeSingleObserver
import com.example.practicefornike1.common.NikeViewModel
import com.example.practicefornike1.common.asyncNetworkRequest
import com.example.practicefornike1.data.OrderHistoryItem
import com.example.practicefornike1.data.repository.OrderRepository

class OrderHistoryViewModel(orderRepository: OrderRepository):NikeViewModel() {

    val orderHistoryLiveData=MutableLiveData<List<OrderHistoryItem>>()

    init {
        orderRepository.orders().asyncNetworkRequest()
            .subscribe(object :NikeSingleObserver<List<OrderHistoryItem>>(compositeDisposable){
                override fun onSuccess(t: List<OrderHistoryItem>) {
                    orderHistoryLiveData.value=t
                }
            })
    }
}