package com.tryden.nook.data.source.local.dao

import androidx.room.*
import com.tryden.nook.data.source.local.entity.PriorityItemEntity
import kotlinx.coroutines.flow.Flow

/**
 * Data Access Object for the Priority table.
 */
@Dao
interface PriorityItemEntityDao {

    @Query("SELECT * FROM priority_item_entity")
    fun getAllPriorityItemEntities(): Flow<List<PriorityItemEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(priorityItemEntity: PriorityItemEntity)

    @Delete
    suspend fun delete(priorityItemEntity: PriorityItemEntity)

    @Update
    suspend fun update(priorityItemEntity: PriorityItemEntity)
}