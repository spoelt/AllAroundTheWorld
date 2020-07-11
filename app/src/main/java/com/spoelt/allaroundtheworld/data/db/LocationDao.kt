package com.spoelt.allaroundtheworld.data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.spoelt.allaroundtheworld.data.model.Location
@Dao
interface LocationDao {
    @Query("SELECT * FROM location")
    suspend fun getAll(): List<Location>

    @Insert
    suspend fun insertAll(users: List<Location>)

    @Insert
    suspend fun insert(location: Location)

    @Delete
    suspend fun delete(location: Location)
}