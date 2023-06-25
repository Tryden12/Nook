package com.tryden.nook.arch

import com.tryden.nook.database.AppDatabase
import com.tryden.nook.database.entity.PriorityItemEntity

class NookRepository(
    private val appDatabase: AppDatabase
){

    suspend fun getAllPriorityItems(): List<PriorityItemEntity> {
        return appDatabase.priorityItemEntityDao().getAllPriorityItemEntities()
    }

    suspend fun insertPriorityItem(priorityItemEntity: PriorityItemEntity) {
        appDatabase.priorityItemEntityDao().insert(priorityItemEntity)
    }

    suspend fun deletePriorityItem(priorityItemEntity: PriorityItemEntity) {
        appDatabase.priorityItemEntityDao().delete(priorityItemEntity)
    }

    suspend fun updatePriorityItem(priorityItemEntity: PriorityItemEntity) {
        appDatabase.priorityItemEntityDao().update(priorityItemEntity)
    }

}