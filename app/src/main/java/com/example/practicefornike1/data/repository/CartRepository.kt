package com.example.practicefornike1.data.repository

import com.example.practicefornike1.data.*
import io.reactivex.Single

interface CartRepository {

    fun addToCart(productId:Int):Single<AddToCartResponse>

    fun get():Single<CartResponse>

    fun remove(cartItemId:Int):Single<MessageResponse>

    fun changeCount(cartItemId: Int,count: Int):Single<AddToCartResponse>

    fun getCartItemCount():Single<CartItemCount>
}