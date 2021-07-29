package com.example.practicefornike1.data.repository.source

import androidx.room.*
import com.example.practicefornike1.data.Product
import io.reactivex.Completable
import io.reactivex.Single

@Dao
interface ProductLocalDataSource:ProductDataSource {

    override fun getProducts(sort:Int): Single<List<Product>>{
        TODO("Not yet implemented")
    }

    @Query("SELECT * FROM products")
    override fun getFavorites(): Single<List<Product>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    override fun addToFavorites(product: Product): Completable

    @Delete
    override fun deleteFromProducts(product: Product): Completable
}