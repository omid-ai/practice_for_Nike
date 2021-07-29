package com.example.practicefornike1.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.practicefornike1.data.Product
import com.example.practicefornike1.data.repository.source.ProductLocalDataSource

@Database(entities = [Product::class],version = 1,exportSchema = false)
abstract class AppDatabase: RoomDatabase() {
    abstract fun productDao():ProductLocalDataSource
}