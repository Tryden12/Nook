package com.tryden.nook.arch

import com.tryden.nook.database.AppDatabase
import com.tryden.nook.database.entity.PriorityItemEntity

class NookRepository(
    private val appDatabase: AppDatabase
){

    suspend fun getAllPriorityItems(): List<PriorityItemEntity> {
        return appDatabase.priorityItemEntityDao().getAllPriorityItemEntities()
    }

    fun insertPriorityItem(priorityItemEntity: PriorityItemEntity) {
        appDatabase.priorityItemEntityDao().insert(priorityItemEntity)
    }

    fun deletePriorityItem(priorityItemEntity: PriorityItemEntity) {
        appDatabase.priorityItemEntityDao().delete(priorityItemEntity)
    }

    fun updatePriorityItem(priorityItemEntity: PriorityItemEntity) {
        appDatabase.priorityItemEntityDao().update(priorityItemEntity)
    }

}