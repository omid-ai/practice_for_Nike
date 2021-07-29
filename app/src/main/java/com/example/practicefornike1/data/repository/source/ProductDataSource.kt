package com.example.practicefornike1.data.repository.source

import com.example.practicefornike1.data.Product
import io.reactivex.Completable
import io.reactivex.Single

interface ProductDataSource {

    fun getProducts(sort:Int): Single<List<Product>>

    fun getFavorites(): Single<List<Product>>

    fun addToFavorites(product: Product): Completable

    fun deleteFromProducts(product: Product): Completable
}