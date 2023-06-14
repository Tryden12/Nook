package com.tryden.nook.arch

import com.tryden.nook.database.AppDatabase
import com.tryden.nook.database.entity.ItemEntity
import kotlinx.coroutines.flow.Flow

class NookRepository(
    private val appDatabase: AppDatabase
){

    fun insertItem(itemEntity: ItemEntity) {
        appDatabase.itemEntityDao().insert(itemEntity)
    }

    fun deleteItem(itemEntity: ItemEntity) {
        appDatabase.itemEntityDao().delete(itemEntity)
    }

    fun getAllItems(): List<ItemEntity> {
        return appDatabase.itemEntityDao().getAllItemEntities()
    }
}