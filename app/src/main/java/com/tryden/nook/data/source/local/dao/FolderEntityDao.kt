package com.tryden.nook.data.source.local.dao

import androidx.room.*
import com.tryden.nook.data.source.local.entity.FolderEntity
import kotlinx.coroutines.flow.Flow

/**
 * Data Access Object for the Folder table.
 */
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