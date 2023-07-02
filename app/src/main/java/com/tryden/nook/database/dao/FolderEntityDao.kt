package com.tryden.nook.database.dao

import androidx.room.*
import com.tryden.nook.database.entity.FolderEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FolderEntityDao {

    @Query("SELECT * FROM folder_entity")
    fun getAllFolderEntities(): Flow<List<FolderEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(folderEntity: FolderEntity)

    @Delete
    suspend fun delete(folderEntity: FolderEntity)

    @Update
    suspend fun update(folderEntity: FolderEntity)

}