package com.example.practicefornike1.data.repository

import com.example.practicefornike1.data.AddToCartResponse
import com.example.practicefornike1.data.CartItemCount
import com.example.practicefornike1.data.CartResponse
import com.example.practicefornike1.data.MessageResponse
import com.example.practicefornike1.data.repository.source.CartDataSource
import io.reactivex.Single

class CartRepositoryImpl(val remoteDataSource: CartDataSource) : CartRepository {

    override fun addToCart(productId: Int): Single<AddToCartResponse> =
        remoteDataSource.addToCart(productId)

    override fun get(): Single<CartResponse> = remoteDataSource.get()

    override fun remove(cartItemId: Int): Single<MessageResponse> =
        remoteDataSource.remove(cartItemId)

    override fun changeCount(cartItemId: Int, count: Int): Single<AddToCartResponse> =
        remoteDataSource.changeCount(cartItemId, count)

    override fun getCartItemCount(): Single<CartItemCount> = remoteDataSource.getCartItemCount()
}