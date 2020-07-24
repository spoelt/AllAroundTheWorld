package com.spoelt.allaroundtheworld.data.db

import androidx.room.*
import com.spoelt.allaroundtheworld.data.model.Location
@Dao
interface LocationDao {
    @Query("SELECT * FROM location")
    suspend fun getAll(): List<Location>

    @Insert
    suspend fun insert(location: Location)

    @Update
    suspend fun update(location: Location)

    @Delete
    suspend fun delete(location: Location)
}