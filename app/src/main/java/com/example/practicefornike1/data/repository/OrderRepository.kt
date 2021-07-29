package com.example.practicefornike1.data.repository

import com.example.practicefornike1.data.Checkout
import com.example.practicefornike1.data.OrderHistoryItem
import com.example.practicefornike1.data.SubmitOrderResult
import io.reactivex.Single

interface OrderRepository {

    fun submitOrders(
        firstName: String,
        lastName: String,
        phoneNumber: String,
        address: String,
        postalCode: String,
        payingMethod: String
    ):Single<SubmitOrderResult>

    fun checkout(orderId:Int):Single<Checkout>

    fun orders():Single<List<OrderHistoryItem>>
}