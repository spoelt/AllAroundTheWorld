package com.spoelt.allaroundtheworld.data.db

import com.spoelt.allaroundtheworld.data.model.Location

class DatabaseHelperImpl(private val appDatabase: AppDatabase) :
    DatabaseHelper {
    override suspend fun getLocations(): List<Location> = appDatabase.locationDao().getAll()

    override suspend fun insert(location: Location) = appDatabase.locationDao().insert(location)

    override suspend fun update(location: Location) = appDatabase.locationDao().update(location)

    override suspend fun delete(location: Location) = appDatabase.locationDao().delete(location)
}