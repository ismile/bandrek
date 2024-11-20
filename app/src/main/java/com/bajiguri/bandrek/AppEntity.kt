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
    val activityName: String?,
    val appArgument: String?
)

@Entity(tableName = "roms")
data class Rom(
    @PrimaryKey(autoGenerate = false)
    val code: String = "",
    val platformCode: String = "",
    val name: String = "",
    val description: String = "",
    val category: String = "",
    val genres: String = "",

    val locationUri: String = "",
    val location: String = "",
    val filename: String = "",

    val coverUrl: String? = null,
    val artworkUrl: String? = null,
    val rating: Double = 0.0,

    val lastPlayed: Long = 0L,
    val favorite: Boolean = false,

    val packageName: String? = null,
    val activityName: String? = null,

)

@Entity(tableName = "app_favorites")
data class AppFavorite(
    @PrimaryKey(autoGenerate = false)
    val code: String,
    val name: String,
    val activityName: String?,
    val packageName: String?,
    val orderId: Int
)

@Entity(tableName = "settings")
data class Setting(
    @PrimaryKey(autoGenerate = false)
    val key: String,
    val value: String
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

    @Query("SELECT * from roms where platformCode = :platformCode ORDER BY name ASC")
    fun getAllRomByPlatformCode(platformCode: String): Flow<List<Rom>>

    @Query("SELECT * from roms where platformCode = :platformCode ORDER BY name ASC")
    suspend fun getAllRomByPlatformCodeDirect(platformCode: String): List<Rom>

    // keys
    @Upsert
    suspend fun upsertSetting(data: Setting)

    @Query("SELECT * from settings ORDER BY `key` ASC")
    fun getAllSetting(): Flow<List<Setting>>

    @Query("SELECT * from settings WHERE `key` = :key")
    fun getSettingByKey(key: String): Flow<Setting>

    @Query("SELECT value FROM settings WHERE `key` = :key")
    suspend fun getSettingValueByKey(key: String): String?

    // app fovorites
    @Upsert
    suspend fun upsertApp(data: AppFavorite)

    @Query("SELECT * from app_favorites ORDER BY `name` ASC")
    fun getAllAppFavorites(): Flow<List<AppFavorite>>

    @Delete
    suspend fun deleteAppFavorite(data: AppFavorite)
}