package com.spoelt.allaroundtheworld.data.db

import com.spoelt.allaroundtheworld.data.model.Location

interface DatabaseHelper {
    suspend fun getLocations(): List<Location>
    suspend fun insertAll(locations: List<Location>)
    suspend fun insert(location: Location)
    suspend fun update(location: Location)
    suspend fun delete(location: Location)
}