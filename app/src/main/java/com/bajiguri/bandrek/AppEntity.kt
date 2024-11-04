package com.bajiguri.bandrek

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.Update
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow


@Entity(tableName = "platforms")
data class Platform(
    @PrimaryKey(autoGenerate = false)
    val code: String,
    val name: String,
    val fileExtension: String,
    val activityName: String,
    val appArgument: String
)

@Entity(tableName = "roms")
data class Rom(
    @PrimaryKey(autoGenerate = false)
    val code: String,
    val platformCode: String,
    val name: String,
    val description: String,
    val category: String,
    val genres: String,

    val locationUri: String,
    val location: String,
    val filename: String
)

@Dao
interface AppDao {

    // platforms
    @Upsert
    suspend fun upsertPlatform(data: Platform)

    @Delete
    suspend fun deletePlatform(data: Platform)

    @Query("SELECT * from platforms WHERE code = :code")
    fun getPlatformByCode(code: String): Flow<Platform>

    @Query("SELECT * from platforms ORDER BY name ASC")
    fun getAllPlatform(): Flow<List<Platform>>

    // roms
    @Upsert
    suspend fun upsertRom(data: Rom)

    @Delete
    suspend fun deleteRom(data: Rom)

    @Query("SELECT * from roms WHERE code = :code")
    fun getRomByCode(code: String): Flow<Rom>

    @Query("SELECT * from roms ORDER BY name ASC")
    fun getAllRom(): Flow<List<Rom>>
}