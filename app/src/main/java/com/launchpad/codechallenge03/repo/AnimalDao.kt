package com.launchpad.codechallenge03.repo

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.launchpad.codechallenge03.models.Animal

@Dao
interface AnimalDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(vararg items: Animal)

    @Query("SELECT * FROM Animal WHERE id=:id")
    suspend fun get(id: String): Animal?

    @Query("SELECT * FROM Animal")
    suspend fun getAll(): List<Animal>

    @Query("DELETE FROM Animal WHERE id = :id")
    fun delete(id: String)

}