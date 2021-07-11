package com.example.practicefornike1.data.repository.source

import com.example.practicefornike1.data.Product
import com.example.practicefornike1.service.ApiService
import io.reactivex.Completable
import io.reactivex.Single

class ProductRemoteDataSource(private val apiService: ApiService):ProductDataSource {

    override fun getProducts(sort:Int): Single<List<Product>> =apiService.getProducts(sort.toString())

    override fun getFavorites(): Single<List<Product>> {
        TODO("Not yet implemented")
    }

    override fun addToFavorites(): Completable {
        TODO("Not yet implemented")
    }

    override fun deleteFromProducts(): Completable {
        TODO("Not yet implemented")
    }
}