package com.example.bagshop.model.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.bagshop.model.data.Product

@Database(entities = [Product::class], version =2, exportSchema = false)
abstract class AppDataBase : RoomDatabase() {
    abstract fun productDao(): ProductDao
}