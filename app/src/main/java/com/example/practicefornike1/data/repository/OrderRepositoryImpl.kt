package com.example.practicefornike1.data.repository

import com.example.practicefornike1.data.Checkout
import com.example.practicefornike1.data.OrderHistoryItem
import com.example.practicefornike1.data.SubmitOrderResult
import com.example.practicefornike1.data.repository.source.OrderDataSource
import io.reactivex.Single

class OrderRepositoryImpl(val remoteDataSource: OrderDataSource) : OrderRepository {

    override fun submitOrders(
        firstName: String,
        lastName: String,
        phoneNumber: String,
        address: String,
        postalCode: String,
        payingMethod: String
    ): Single<SubmitOrderResult> =
        remoteDataSource.submitOrders(
        firstName,
        lastName,
        phoneNumber,
        address,
        postalCode,
        payingMethod
    )

    override fun checkout(orderId: Int): Single<Checkout> = remoteDataSource.checkout(orderId)

    override fun orders(): Single<List<OrderHistoryItem>> =
        remoteDataSource.orders()
}