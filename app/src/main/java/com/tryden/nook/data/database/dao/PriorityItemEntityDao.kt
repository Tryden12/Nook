package com.tryden.nook.data.database.dao

import androidx.room.*
import com.tryden.nook.data.database.entity.PriorityItemEntity
import kotlinx.coroutines.flow.Flow

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