package com.spoelt.allaroundtheworld.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.spoelt.allaroundtheworld.data.model.Location

@Database(entities = [Location::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun locationDao(): LocationDao
}