package com.tryden.nook.database.dao

import androidx.room.*
import com.tryden.nook.database.entity.PriorityItemEntity

@Dao
interface PriorityItemEntityDao {

    @Query("SELECT * FROM priority_item_entity")
    suspend fun getAllPriorityItemEntities(): List<PriorityItemEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(priorityItemEntity: PriorityItemEntity)

    @Delete
    fun delete(priorityItemEntity: PriorityItemEntity)

    @Update
    fun update(priorityItemEntity: PriorityItemEntity)
}