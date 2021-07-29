package com.example.practicefornike1.data.repository.source

import com.example.practicefornike1.data.Checkout
import com.example.practicefornike1.data.OrderHistoryItem
import com.example.practicefornike1.data.SubmitOrderResult
import com.example.practicefornike1.service.ApiService
import com.google.gson.JsonObject
import io.reactivex.Single

class OrderRemoteDataSource(val apiService: ApiService):OrderDataSource {
    override fun submitOrders(
        firstName: String,
        lastName: String,
        phoneNumber: String,
        address: String,
        postalCode: String,
        payingMethod: String
    ): Single<SubmitOrderResult> =
        apiService.submittingOrder(JsonObject().apply {
            addProperty("first_name",firstName)
            addProperty("last_name",lastName)
            addProperty("postal_code",postalCode)
            addProperty("mobile",phoneNumber)
            addProperty("address",address)
            addProperty("payment_method",payingMethod)
        })


    override fun checkout(orderId: Int): Single<Checkout> = apiService.checkout(orderId)

    override fun orders(): Single<List<OrderHistoryItem>> =
        apiService.orders()
}