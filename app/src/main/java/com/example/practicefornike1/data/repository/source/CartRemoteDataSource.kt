package com.example.practicefornike1.data.repository.source

import com.example.practicefornike1.data.AddToCartResponse
import com.example.practicefornike1.data.CartItemCount
import com.example.practicefornike1.data.CartResponse
import com.example.practicefornike1.data.MessageResponse
import com.example.practicefornike1.service.ApiService
import com.google.gson.JsonObject
import io.reactivex.Single

class CartRemoteDataSource(val apiService: ApiService):CartDataSource {

    override fun addToCart(productId: Int): Single<AddToCartResponse> =apiService.addToCart(
        JsonObject().apply {
            addProperty("product_id",productId)
        }
    )

    override fun get(): Single<CartResponse> {
        TODO("Not yet implemented")
    }

    override fun remove(cartItemId: Int): Single<MessageResponse> {
        TODO("Not yet implemented")
    }

    override fun changeCount(cartItemId: Int, count: Int): Single<AddToCartResponse> {
        TODO("Not yet implemented")
    }

    override fun getCartItemCount(): Single<CartItemCount> {
        TODO("Not yet implemented")
    }
}