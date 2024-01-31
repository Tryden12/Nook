package com.tryden.nook.data.source.local.dao

import androidx.room.*
import com.tryden.nook.data.source.local.entity.ChecklistItemEntity
import kotlinx.coroutines.flow.Flow

/**
 * Data Access Object for the Checklist table.
 */
@Dao
interface ChecklistItemDao {

    @Query("SELECT * FROM checklist_item_entity")
    fun getAllChecklistItemEntities(): Flow<List<ChecklistItemEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(checklistItemEntity: ChecklistItemEntity)

    @Delete
    suspend fun delete(checklistItemEntity: ChecklistItemEntity)

    @Update
    suspend fun update(checklistItemEntity: ChecklistItemEntity)
}