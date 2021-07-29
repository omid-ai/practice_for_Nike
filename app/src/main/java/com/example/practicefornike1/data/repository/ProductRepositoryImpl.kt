package com.example.practicefornike1.data.repository

import com.example.practicefornike1.data.Product
import com.example.practicefornike1.data.repository.source.ProductDataSource
import com.example.practicefornike1.data.repository.source.ProductLocalDataSource
import io.reactivex.Completable
import io.reactivex.Single

class ProductRepositoryImpl(
    private val remoteDataSource: ProductDataSource,
    val localDataSource: ProductLocalDataSource
) : ProductRepository {

    override fun getProducts(sort: Int): Single<List<Product>> =
        localDataSource.getFavorites().flatMap { favoriteProducts ->
            remoteDataSource.getProducts(sort).doOnSuccess {
                val favoriteProductsId = favoriteProducts.map {
                    it.id
                }
                it.forEach { product ->
                    if (favoriteProductsId.contains(product.id))
                        product.isFavorite = true
                }
            }
        }

    override fun getFavorites(): Single<List<Product>> = localDataSource.getFavorites()

    override fun addToFavorites(product: Product): Completable = localDataSource.addToFavorites(product)

    override fun deleteFromProducts(product: Product): Completable =
        localDataSource.deleteFromProducts(product)
}