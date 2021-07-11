package com.example.practicefornike1.data.repository

import com.example.practicefornike1.data.Product
import io.reactivex.Completable
import io.reactivex.Single

interface ProductRepository {

    fun getProducts(sort:Int):Single<List<Product>>

    fun getFavorites():Single<List<Product>>

    fun addToFavorites():Completable

    fun deleteFromProducts():Completable
}