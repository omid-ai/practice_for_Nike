package com.example.practicefornike1.data.repository

import com.example.practicefornike1.data.Product
import com.example.practicefornike1.data.repository.source.ProductDataSource
import com.example.practicefornike1.data.repository.source.ProductLocalDataSource
import io.reactivex.Completable
import io.reactivex.Single

class ProductRepositoryImpl(
    private val remoteDataSource: ProductDataSource,
    localDataSource: ProductLocalDataSource
) : ProductRepository {

    override fun getProducts(sort:Int): Single<List<Product>> =remoteDataSource.getProducts(sort)

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