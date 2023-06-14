package com.tryden.nook.database.dao

import androidx.room.*
import com.tryden.nook.database.entity.ItemEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ItemEntityDao {

    @Query("SELECT * FROM item_entity")
    fun getAllItemEntities(): List<ItemEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(itemEntity: ItemEntity)

    @Delete
    fun delete(itemEntity: ItemEntity)

    @Update
    fun update(itemEntity: ItemEntity)
}