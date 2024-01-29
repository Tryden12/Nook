package com.tryden.nook.data.database.dao

import androidx.room.*
import com.tryden.nook.data.database.entity.ChecklistItemEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ChecklistItemEntityDao {

    @Query("SELECT * FROM checklist_item_entity")
    fun getAllChecklistItemEntities(): Flow<List<ChecklistItemEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(checklistItemEntity: ChecklistItemEntity)

    @Delete
    suspend fun delete(checklistItemEntity: ChecklistItemEntity)

    @Update
    suspend fun update(checklistItemEntity: ChecklistItemEntity)
}